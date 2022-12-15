package com.pwang.projoect.domain.user.requestDTO;

import com.pwang.projoect.domain.user.entity.UserRole;
import lombok.Data;

@Data
public class RequestUserDTO {

    private String userId;
    private String name;
    private UserRole role;
    private String email;
    private String password;
}
