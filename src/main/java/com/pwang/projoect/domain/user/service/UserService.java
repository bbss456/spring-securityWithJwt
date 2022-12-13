package com.pwang.projoect.domain.user.service;

import com.pwang.projoect.domain.user.entity.User;
import com.pwang.projoect.domain.user.repository.UserRepository;
import com.pwang.projoect.domain.user.requestDTO.RequestFindUserDTO;
import com.pwang.projoect.domain.user.responseDTO.ResponseJwtTokenDTO;
import com.pwang.projoect.global.exception.BusinessException;
import com.pwang.projoect.global.exception.ErrorCode;
import com.pwang.projoect.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public Boolean createUser(User user) {
        userRepository.save(user);

        return Boolean.TRUE;
    }

    public ResponseJwtTokenDTO findUser(RequestFindUserDTO requestFindUserDTO) {
        User user = userRepository.findByuserIdAndPassword(requestFindUserDTO.getUserId(), requestFindUserDTO.getPassword())
            .orElseThrow(() -> new BusinessException("계정이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));

        return new ResponseJwtTokenDTO(jwtTokenProvider.createAccessToken(user.getUserId(), user.getEmail()),
            jwtTokenProvider.createJwtRefreshToken(user.getEmail()));
    }

}
