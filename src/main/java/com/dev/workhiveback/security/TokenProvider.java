package com.dev.workhiveback.security;

import com.dev.workhiveback.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
//jwt 토큰을 생성하고 검증하는 역할을 수행하는 클래스
public class TokenProvider {

    //jwt 서명에 사용할 비밀 키(512 비트 이상 추천)
    private static final String SECRET_KEY="5IaiTVcWpg09flHNydX3O%RnfrmOlwvB@GAsgho#yIU8cAa6%#Ke&eqVphHXiQ+q";

    //Keys.hmacShaKeyFor = jwt 서명을 만들 때 사용할 적절한 비밀키를 생성해주는 함수
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    //사용자 정보를 통해 jwt 토큰 생성
    public String create(UserEntity userEntity) {
        //만료시간 지금으로부터 1일 뒤에 만료
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        //chronoUnit.Days= 일 단위를 얘기한다/ instant.now()는 현재 시각을 instant 객체로 반환

        //jwt 생성 및 반환
        return Jwts.builder()
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)//서명 알고리즘과 키 설정
                .setSubject(String.valueOf(userEntity.getEmp_id()))//사원 번호를 subject로 설정 -> 이 토큰이 누구를 위한 것인지
                .setIssuer("Work Hive")//토큰 발급자 정보 설정
                .setIssuedAt(new Date())//토큰 발급 시간 설정
                .setExpiration(expireDate)//만료 기간 설정
                .compact();//토큰 생성 완료
    }

    //token을 검증하고 포함된 사용자 id를 반환
    public String validateAndGetUserEmpId(String token) {
        //토큰 파싱 및 검증(서명이 유효한지 확인)
        Claims claims = Jwts.parser()
                .setSigningKey(SIGNING_KEY)//서명 키 설정
                .build()
                .parseClaimsJws(token)//토큰을 파싱
                .getBody();//payload(Claims)추출
        System.out.println(claims.getSubject());

        return claims.getSubject();//사용자 id 반환
    }

    //로그인 이후 사용자 정보를 간단하게 전달할때 사용하기 좋다.
//    public String createByEmpId(Long emp_id){
//
//        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
//
//        //jwt 생성 및 반환
//        return Jwts.builder()
//                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)//서명 알고리즘과 키 설정
//                .setSubject(String.valueOf(emp_id))//사원 번호를 subject로 설정 -> 이 토큰이 누구를 위한 것인지
//                .setIssuedAt(new Date())//토큰 발급 시간 설정
//                .setExpiration(expireDate)//만료 기간 설정
//                .compact();//토큰 생성 완료
//    }
    public String createByEmpId(String emp_id){

        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        //jwt 생성 및 반환
        return Jwts.builder()
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)//서명 알고리즘과 키 설정
                .setSubject(String.valueOf(emp_id))//사원 번호를 subject로 설정 -> 이 토큰이 누구를 위한 것인지
                .setIssuedAt(new Date())//토큰 발급 시간 설정
                .setExpiration(expireDate)//만료 기간 설정
                .compact();//토큰 생성 완료
    }

}
