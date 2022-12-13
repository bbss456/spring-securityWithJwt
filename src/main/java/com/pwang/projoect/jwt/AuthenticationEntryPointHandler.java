package com.pwang.projoect.jwt;

import com.pwang.projoect.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;
        log.warn(exception);

        String result = (String) request.getAttribute("refresh");

        if(exception == null) {
            errorCode = ErrorCode.NULLTokenException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("MalformedJwtException")) {
            errorCode = ErrorCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("IllegalArgumentException")) {
            errorCode = ErrorCode.NULLTokenException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("ExpiredJwtException")) {
            errorCode = ErrorCode.ExpiredJwtException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("SignatureException")) {
            errorCode = ErrorCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        json.put("status", errorCode.getStatus());
        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
        log.warn(errorCode.getMessage());
    }
}
