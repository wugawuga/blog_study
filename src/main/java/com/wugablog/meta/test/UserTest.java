package com.wugablog.meta.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTest {

    private Long id;
    private String username;
    private String password;
    private String phone;
}
