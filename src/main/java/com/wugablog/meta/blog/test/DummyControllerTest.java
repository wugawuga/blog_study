package com.wugablog.meta.blog.test;

import com.wugablog.meta.model.RoleType;
import com.wugablog.meta.model.User;
import com.wugablog.meta.repository.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DummyControllerTest {

    private final UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("email = " + user.getEmail());
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었어요";
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 유저는 없습니다. id : " + id)
        );

        // user 객체 = java object
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json 으로
        // 스프링부트 = MessageConverter 가 응답시에 자동 작동
        // 자바 오브젝트 리턴 시에 Jackson 라이브러리 호출
        // user -> json 으로 변환해서 응답
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한페이지당 2건 데이터 리턴
    @GetMapping("/dummy/user")
    public Page<User> pageList(
        @PageableDefault(size = 2, sort = "createDate", direction = Direction.DESC)
        Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        return pagingUser;
    }

    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        System.out.println("id = " + id);
        System.out.println("requestUser.getEmail() = " + requestUser.getEmail());
        System.out.println("requestUser.getPassword() = " + requestUser.getPassword());

        User user = userRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("업데이트 실패")
        );
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);
        // 더티체킹!
        return user;
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패했어요. 해당 id는 없습니다.";
        }
        return "삭제되었어요. id = " + id;
    }
}
