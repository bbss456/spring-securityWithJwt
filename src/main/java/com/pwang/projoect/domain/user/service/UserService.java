package com.pwang.projoect.domain.user.service;

import com.pwang.projoect.domain.user.entity.User;
import com.pwang.projoect.domain.user.repository.UserRepository;
import com.pwang.projoect.domain.user.requestDTO.RequestFindUserDTO;
import com.pwang.projoect.domain.user.responseDTO.ResponseJwtTokenDTO;
import com.pwang.projoect.global.exception.BusinessExceptionHandler;
import com.pwang.projoect.global.exception.ErrorCode;
import com.pwang.projoect.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public Boolean createUser(User user) {
        userRepository.save(user);

        return Boolean.TRUE;
    }

    public User findByUserId(String UserId) {
        User user = userRepository.findById(UserId).orElseThrow(() ->new BusinessExceptionHandler("일치하는 유저가 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        return user;
    }

    public ResponseJwtTokenDTO loginAuthUser(RequestFindUserDTO requestFindUserDTO) {
        User user = userRepository.findById(requestFindUserDTO.getUserId())
            .orElseThrow(() -> new BusinessExceptionHandler("계정이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        try {
            passwordEncoder.matches(requestFindUserDTO.getPassword(),user.getPassword());
        } catch (RuntimeException error) {
            throw new BusinessExceptionHandler("로그인 인증 실패",ErrorCode.UNAUTHORIZEDException);
        }
        return new ResponseJwtTokenDTO(jwtTokenProvider.createAccessToken(user.getUserId(), user.getEmail()),
            jwtTokenProvider.createJwtRefreshToken(user.getEmail()));
    }

}
