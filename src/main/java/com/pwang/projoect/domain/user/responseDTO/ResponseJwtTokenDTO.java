package com.pwang.projoect.domain.user.responseDTO;

import lombok.Data;

@Data
public class ResponseJwtTokenDTO {
    private String accessToken;
    private String refreshToken;

    public ResponseJwtTokenDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
