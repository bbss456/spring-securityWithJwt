package com.pwang.projoect.domain.user.service;

import com.pwang.projoect.ProjoectApplication;
import com.pwang.projoect.domain.user.entity.User;
import com.pwang.projoect.domain.user.entity.UserRole;
import com.pwang.projoect.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ProjoectApplication.class)
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void createUser() {

        passwordEncoder.encode("asdasd");

        String pwd = passwordEncoder.encode("asdasd");

        passwordEncoder.matches("asdasd", pwd);

        User user = User.builder()
            .userId("test")
            .password("asdmad")
            .role(UserRole.ROLE_USER)
            .email("wqe@qwe")
            .name("test유저")
            .build();

        userService.createUser(user);
    }
 }