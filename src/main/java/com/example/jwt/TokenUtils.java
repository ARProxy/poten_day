//package com.example.jwt;
//
//import com.example.entity.UsersEntity;
//import io.jsonwebtoken.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Service
//public class TokenUtils {
//
//    private final String SECRET_KEY = "BgPKXM1oynKeClXDo09d1k4hpzpFwAReQhg0Stv4TEM=";
//
//
//    public String generateJwtToken(UsersEntity usersEntity) {
//        return Jwts.builder()
//                .setSubject(usersEntity.getUserId())
//                .setHeader(createHeader())
//                .setClaims(createClaims(usersEntity))
////                .setExpiration(createExpireDate(1000 * 60 * 5))
//                .signWith(SignatureAlgorithm.HS256, createSigningKey(SECRET_KEY))
//                .compact();
//    }
//
////    public String saveRefreshToken(UsersEntity usersEntity) {
////        return Jwts.builder()
////                .setSubject(usersEntity.getUserId())
////                .setHeader(createHeader())
////                .setClaims(createClaims(usersEntity))
////                .setExpiration(createExpireDate(1000 * 60 * 10))
////                .signWith(SignatureAlgorithm.HS256, createSigningKey(REFRESH_KEY))
////                .compact();
////    }
//
////    public boolean isValidToken(String token) {
////        try {
////            Claims claims = getClaimsFromToken(token, SECRET_KEY);
////            System.out.println("Access expireTime: " + claims.getExpiration());
////            System.out.println("Access userId: " + claims.get(DATA_KEY, String.class));
////            return true;
////        } catch (ExpiredJwtException e) {
////            System.out.println("Token Expired UserID : " + e.getClaims().getSubject());
////            return false;
////        } catch (JwtException e) {
////            System.out.println("Token Tampered");
////            return false;
////        } catch (NullPointerException e) {
////            System.out.println("Token is null");
////            return false;
////        }
////    }
//
//    public boolean isValidToken(String token) {
//        System.out.println("isValidToken is : " +token);
//        try {
//            Claims accessClaims = getClaimsFormToken(token);
//            System.out.println("Access expireTime: " + accessClaims.getExpiration());
//            System.out.println("Access userId: " + accessClaims.get("userId"));
//            return true;
//        } catch (ExpiredJwtException exception) {
//            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
//            return false;
//        } catch (JwtException exception) {
//            System.out.println("Token Tampered");
//            return false;
//        } catch (NullPointerException exception) {
//            System.out.println("Token is null");
//            return false;
//        }
//    }
////    public boolean isValidRefreshToken(String token) {
////        try {
////            Claims accessClaims = getClaimsToken(token);
////            System.out.println("Access expireTime: " + accessClaims.getExpiration());
////            System.out.println("Access userId: " + accessClaims.get("userId"));
////            return true;
////        } catch (ExpiredJwtException exception) {
////            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
////            return false;
////        } catch (JwtException exception) {
////            System.out.println("Token Tampered");
////            return false;
////        } catch (NullPointerException exception) {
////            System.out.println("Token is null");
////            return false;
////        }
////    }
//
//
////    private Date createExpireDate(long expireDate) {
////        long curTime = System.currentTimeMillis();
////        return new Date(curTime + expireDate);
////    }
//
//    private Map<String, Object> createHeader() {
//        Map<String, Object> header = new HashMap<>();
//
//        header.put("typ", "ACCESS_TOKEN");
//        header.put("alg", "HS256");
//        header.put("regDate", System.currentTimeMillis());
//
//        return header;
//    }
//
//    private Map<String, Object> createClaims(UsersEntity usersEntity) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(DATA_KEY, usersEntity.getUserId());
//        return claims;
//    }
//
//    private Key createSigningKey(String key) {
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
//        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    private Claims getClaimsFormToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Claims getClaimsToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public Long getUserIdFromToken(String token) {
//        Claims claims = getClaimsFormToken(token);
//        String userIdString = claims.get(DATA_KEY, String.class);
//        return Long.parseLong(userIdString);
//    }
//}
package com.example.jwt;

import com.example.entity.UsersEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenUtils {

    private final String SECRET_KEY = "zZOYXKNZJ3JI2xNWLsUiHKupMIYgLfdbVsc/m8THFVg=";

    public String generateJwtToken(UsersEntity usersEntity) {
        long expirationTimeInMs = 100000 * 60 * 60;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usersEntity.getId());

        return Jwts.builder()

                .setSubject(usersEntity.getUserId()) // 사용자 ID를 Subject로 설정
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, createSigningKey(SECRET_KEY))
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(createSigningKey(SECRET_KEY)).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(createSigningKey(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Subject에서 사용자 ID 추출
    }

    private Key createSigningKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
