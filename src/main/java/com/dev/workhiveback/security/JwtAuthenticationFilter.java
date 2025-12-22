package com.dev.workhiveback.security;

import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.mappers.UserMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
//JwtAuthenticationFilter 는 스프링 부트의 앱에서 모든 요청에 대해서 한번씩 실행, jwt토큰을 검사해서 유효하다면 인증 정보를 security context에 등록하는 역할을 한다
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter 를 상속하면 http 요청당 한번만 실행되는 필터를 만들수 있다.
    private final TokenProvider tokenProvider;
    private final UserMapper userMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getMethod().equals("OPTIONS")) {
            return true;//OPTIONS 메서드는 필터 대상이 아니다.
        }
        return false;
    }

    //필터 내부 로직: 요청에서 jwt파싱 -> 검증 -> 인증 객체 생성 -> security context 설정
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parserBearer(request);//Authorization 헤더에서 jwt 토큰 파싱
//            log.info("doFilterInternal");

            //토큰이 존재하고 null이 아닌 경우
            if (token != null && !token.equalsIgnoreCase("null")) {
                String empId = tokenProvider.validateAndGetUserEmpId(token);

                UserEntity user = userMapper.selectByEmpId(empId)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

                List<GrantedAuthority> authorities;
                if (user.getTeamCode() == 101 || user.getRoleCode() == 1) {
                    authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
                } else {
                    authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
                }

                AbstractAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);
// 🔺 여기 principal = user (UserEntity)

                //요청 정보 추가(IP,세션 등)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //새로운 Security context 생성 및 인증 객체 설정 -> @AuthenticationPrincipal을 통해 현재 로그인한 사용자 정보에 접근 가능
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);

                //현재 쓰레드에 Security Context 등록
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            log.error("could not set user authenticaion in security context", e);
        }

        filterChain.doFilter(request, response);
    }


    public String parserBearer(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);//"Bearer "이후 문자 추출
        }
        return null;//유효하지 않은 경우 null 반환
    }
}
