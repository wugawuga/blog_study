package com.wugablog.meta.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wugablog.meta.config.auth.PrincipalDetailService;
import com.wugablog.meta.model.KakaoProfile;
import com.wugablog.meta.model.OAuthToken;
import com.wugablog.meta.model.User;
import com.wugablog.meta.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    private final UserService userService;
    private final PrincipalDetailService principalDetailService;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallBack(String code) throws JsonProcessingException {

        // post방식으로 key=value 로 요청 (서버 -> 카카오서버)
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf8");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", "9251bb4d95be0ecd03ededa206870062");
        map.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<>(map, headers);

        ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        // gson, json simple, objectmapper
        ObjectMapper mapper = new ObjectMapper();

        OAuthToken oAuthToken = mapper.readValue(
            response.getBody(), OAuthToken.class
        );

        // access 토큰으로 회원 정보 접근!!!
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded; charset=utf8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
            new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoProfileRequest,
            String.class
        );

        ObjectMapper mapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = mapper2.readValue(
            response2.getBody(), KakaoProfile.class
        );

        // User : username, password, email
        System.out.println("카카오 아이디 " + kakaoProfile.getId());
        System.out.println("카카오 이메일 " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("카카오 닉네임 " + kakaoProfile.getKakao_account().getProfile().getNickname());

        System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_"
            + kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        String name = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();
        String email = kakaoProfile.getKakao_account().getEmail();

        User kakaoUser = User.builder()
            .username(name)
            .email(email)
            .password(cosKey)
            .oauth("kakao")
            .build();

        User originUser = userService.findUserByKakao(name);
        if (originUser == null) {
            userService.join(kakaoUser);
        }

        // 로그인 처리
        UserDetails userDetail = principalDetailService.loadUserByUsername(kakaoUser.getUsername());
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return "redirect:/";
    }
}
