package com.pwang.projoect.domain.user.controller;

import com.pwang.projoect.domain.user.entity.User;
import com.pwang.projoect.domain.user.requestDTO.RequestFindUserDTO;
import com.pwang.projoect.domain.user.requestDTO.RequestUserDTO;
import com.pwang.projoect.domain.user.responseDTO.ResponseJwtTokenDTO;
import com.pwang.projoect.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pwang")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private HttpHeaders makeUTF8Header() {
        HttpHeaders returnResHeaders = new HttpHeaders();
        returnResHeaders.add("Content-Type", "application/json;charset=UTF-8");

        return returnResHeaders;
    }

    @PostMapping("/api/auth/new")
    public ResponseEntity<Boolean> createUser(@RequestBody RequestUserDTO requestUserDTO) {

        User user = User.builder()
            .userId(requestUserDTO.getUserId())
            .password(passwordEncoder.encode(requestUserDTO.getPassword()))
            .name(requestUserDTO.getName())
            .role(requestUserDTO.getRole())
            .email(requestUserDTO.getEmail())
            .build();

        return new ResponseEntity<>(userService.createUser(user), this.makeUTF8Header(), HttpStatus.OK);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<ResponseJwtTokenDTO> loginAuthUser(@RequestBody RequestFindUserDTO requestFindUserDTO) {

        return new ResponseEntity<>(userService.loginAuthUser(requestFindUserDTO), this.makeUTF8Header(), HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/{userId}")
    public ResponseEntity<Boolean> updateUser(@RequestBody RequestUserDTO requestUserDTO) {
        User user = userService.findByUserId(requestUserDTO.getUserId());

        user = User.builder()
                .userId(requestUserDTO.getUserId())
                .password(passwordEncoder.encode(requestUserDTO.getPassword()))
                .name(requestUserDTO.getName())
                .role(requestUserDTO.getRole())
                .email(requestUserDTO.getEmail())
                .build();

        return new ResponseEntity<>(Boolean.TRUE, this.makeUTF8Header(), HttpStatus.OK);
    }
}