package com.pwang.projoect.domain.user.service;

import com.pwang.projoect.domain.user.entity.CustumUserDetail;
import com.pwang.projoect.domain.user.entity.User;
import com.pwang.projoect.domain.user.repository.UserRepository;
import com.pwang.projoect.global.exception.BusinessExceptionHandler;
import com.pwang.projoect.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustumUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessExceptionHandler("계정이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));

        CustumUserDetail userDetail = CustumUserDetail
                .builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return userDetail;
    }
}


