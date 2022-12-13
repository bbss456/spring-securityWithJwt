package com.pwang.projoect.domain.user.requestDTO;

import lombok.Data;

@Data
public class RequestFindUserDTO {

    private String userId;
    private String password;
}
