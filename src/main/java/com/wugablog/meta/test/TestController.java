package com.wugablog.meta.test;

import com.wugablog.meta.model.Board;
import com.wugablog.meta.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserTestRepository userTestRepository;

//    @Autowired
//    private BoardRepository boardRepository;
//
//    @GetMapping("/test/board/{id}")
//    public Board getBoard(@PathVariable Long id) {
//        return boardRepository.findById(id).get();
//    }

    @GetMapping("/test/user")
    public List<UserTest> findAll() {
        System.out.println("TestController.findAll");
        return userTestRepository.findAll();
    }

    @GetMapping("/test/user/{id}")
    public UserTest findById(@PathVariable Long id) {
        System.out.println("TestController.findById");
        UserTest user = userTestRepository.findById(id);
        return user;
    }

    @PostMapping("/test/user")
    // x-www-form-urlencoded -> String username, String password, String phone
    public ResponseEntity<String> saveUser(String username, String password, String phone, @RequestBody UserTest userTest) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("phone = " + phone);

        System.out.println("userTest.getUsername() = " + userTest.getUsername());
        System.out.println("userTest.getPassword() = " + userTest.getPassword());
        System.out.println("userTest.getPhone() = " + userTest.getPhone());
        System.out.println("TestController.saveUser");

        userTestRepository.save(userTest);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/test/user/{id}")
    public void delete(@PathVariable Long id) {
        System.out.println("TestController.delete");
    }

    @PutMapping("/test/user/{id}")
    public void update(@PathVariable Long id, String password, String phone) {
        System.out.println("TestController.update");
    }
}
