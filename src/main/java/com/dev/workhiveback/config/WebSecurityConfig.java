package com.dev.workhiveback.config;

import com.dev.workhiveback.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration//이 클래스가 스프링 설정 클래스임을 의미
@EnableWebSecurity//Spring Security 활성화
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //스프링 시큐리티의 보안 동작을 설정
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())//token 관리일때는 필수로 꺼주는것이 일반적이다.
                .httpBasic(httpBasic -> httpBasic.disable())//기본 인증 방식 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션 사용 안함 (jwt 기반 인증)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/user/login", "/user/register", "/","/test/requestTest").permitAll()
                                .anyRequest().authenticated()
                )
                //jwt 필터를 usernamePasswordAuthenticationFilter 이후에 실행되도록 추가
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        //인증 실패시 403 forbidden 반환
                        .authenticationEntryPoint(new Http403ForbiddenEntryPoint(
                )));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); //쿠키나 인증정보를 포함 할건지 true여야 클라이언트가 인증정보를 함께 보낼수 있다.
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "OPTIONS", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("*"));

        //위의 cors 설정을 모든 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);//모든 경로에 대해 설정 적용
        return source;
    }
}
