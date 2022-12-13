package com.pwang.projoect.global.config;



import com.pwang.projoect.jwt.AuthenticationEntryPointHandler;
import com.pwang.projoect.jwt.JwtAccessDeniedHandler;
import com.pwang.projoect.jwt.JwtAuthenticationFilter;
import com.pwang.projoect.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
        JwtTokenProvider jwtTokenProvider) throws Exception {
        return http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPointHandler)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .requestMatchers("/pwang/api/v1/auth/**").permitAll()
            .requestMatchers("/pwang/api/v1/**").authenticated()
            .requestMatchers("/test").hasRole("USER")
            .requestMatchers("/api/user/**").hasRole("USER")
            .and()
            .addFilterBefore(jwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
