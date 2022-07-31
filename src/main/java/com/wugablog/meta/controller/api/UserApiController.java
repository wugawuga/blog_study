package com.wugablog.meta.controller.api;

import com.wugablog.meta.config.auth.PrincipalDetail;
import com.wugablog.meta.config.auth.PrincipalDetailService;
import com.wugablog.meta.dto.ResponseDto;
import com.wugablog.meta.model.User;
import com.wugablog.meta.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PrincipalDetailService principalDetailService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        int result = userService.join(user);
        return new ResponseDto<>(HttpStatus.OK.value(), result);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal
    PrincipalDetail principal,
        HttpSession session) {
        userService.update(user);

        // Authentication 세션 변경 해줘야 커밋된 db 확인 가능
        // 강제 Authentication 객체 만들어서 세션에 집어넣음
        UserDetails userDetail = principalDetailService.loadUserByUsername(user.getUsername());
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
