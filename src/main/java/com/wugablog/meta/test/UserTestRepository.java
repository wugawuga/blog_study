package com.wugablog.meta.test;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserTestRepository {

    private List<UserTest> users = new ArrayList<>();

    public List<UserTest> findAll() {
        users.add(new UserTest(2L, "wuga1", "123", "010-3655-1086"));
        users.add(new UserTest(3L, "wuga2", "123", "010-3655-1086"));
        return users;
    }

    public UserTest findById(Long id) {
        for (UserTest userTest : users) {
            if (userTest.getId() == id) {
                return userTest;
            }
        }
        return new UserTest(404L, "404", "404", "404-040-404");
    }

    public void save(UserTest userTest) {
        userTest.setId(888L);
        users.add(userTest);
    }
}
