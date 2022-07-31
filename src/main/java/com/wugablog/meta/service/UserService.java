package com.wugablog.meta.service;

import com.wugablog.meta.model.RoleType;
import com.wugablog.meta.model.User;
import com.wugablog.meta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public int join(User user) {
        try {
            String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setRole(RoleType.USER);
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService : join() : " + e.getMessage());
        }
        return -1;
    }

    @Transactional
    public void update(User user) {
        User userById = userRepository.findById(user.getId())
            .orElseThrow(
                () -> new IllegalArgumentException("등록된 회원이 없어요")
            );
        if (userById.getOauth() == null || userById.getOauth().equals("")) {
            String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
            userById.setEmail(user.getEmail());
            userById.setPassword(encodePassword);
        }

    }

    @Transactional(readOnly = true)
    public User findUserByKakao(String name) {

        User user = userRepository.findByUsername(name)
            .orElse(
                null
            );

        return user;
    }
}
