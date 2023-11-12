package com.example.service;

import com.example.entity.UsersEntity;

import com.example.exception.InvalidPasswordException;
import com.example.exception.UserIdNotFoundException;
import com.example.jwt.TokenUtils;
import com.example.repository.PostItRepository;
import com.example.repository.UsersRepository;
import com.example.dto.TokenResponse;
import com.example.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final TokenUtils tokenUtils;

    private final PasswordEncoder passwordEncoder;
    private final PostItRepository postItRepository;

    public Optional<UsersEntity> findByUserId(String userId) {

        return usersRepository.findByUserId(userId);
    }
    @Transactional
    public TokenResponse signUp(UserRequest userRequest) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequest.getUserPw());
        // 사용자 저장
        UsersEntity usersEntity = usersRepository.save(
                UsersEntity.builder()
                        .userId(userRequest.getUserId())
                        .userPw(encodedPassword) // 암호화된 비밀번호를 저장
                        .nickName(userRequest.getNickName())
                        .build());
        // 토큰 생성
        String accessToken = tokenUtils.generateJwtToken(usersEntity);
        // 인증 정보 저장
//        authRepository.save(
//                AuthEntity.builder()
//                        .usersEntity(usersEntity)
//                        .build());
        return TokenResponse.builder().token(accessToken).build();
    }

//    @Transactional
//    public TokenResponse signUp(UserRequest userRequest) {
//        UsersEntity usersEntity =
//                usersRepository.save(
//                        UsersEntity.builder()
//                                .userPw(userRequest.getUserPw())
//                                .userId(userRequest.getUserId())
//                                .build());
//
//        String accessToken = tokenUtils.generateJwtToken(usersEntity);
//        String refreshToken = tokenUtils.saveRefreshToken(usersEntity);
//
//        authRepository.save(
//                AuthEntity.builder().usersEntity(usersEntity).refreshToken(refreshToken).build());
//
//        return TokenResponse.builder().ACCESS_TOKEN(accessToken).build();
//    }

//    @Transactional
//    public TokenResponse signIn(UserRequest userRequest) {
//        UsersEntity usersEntity =
//                usersRepository
//                        .findByUserIdAndPw(userRequest.getUserId(), userRequest.getUserPw())
//                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//        AuthEntity authEntity =
//                authRepository
//                        .findByUsersEntityId(usersEntity.getId())
//                        .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
//        String accessToken = "";
//        String refreshToken= authEntity.getRefreshToken();
//
//        if (tokenUtils.isValidRefreshToken(refreshToken)) {
//            accessToken = tokenUtils.generateJwtToken(authEntity.getUsersEntity());
//            return TokenResponse.builder()
//                    .ACCESS_TOKEN(accessToken)
//                    .REFRESH_TOKEN(authEntity.getRefreshToken())
//                    .build();
//        } else {
//            refreshToken = tokenUtils.saveRefreshToken(usersEntity);
//            authEntity.refreshUpdate(refreshToken);
//        }
//
//        return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
//    }
//@Transactional(readOnly = true)
//public TokenResponse signIn(UserRequest userRequest) {
//    UsersEntity usersEntity =
//            usersRepository
//                    .findByUserIdAndPw(userRequest.getUserId(), userRequest.getUserPw())
//                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//    AuthEntity authEntity =
//            authRepository
//                    .findByUsersEntityId(usersEntity.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
//
//    // 리프레시 토큰을 확인합니다.
//    String refreshToken = authEntity.getRefreshToken();
//    String accessToken = "";
//
//    if (!tokenUtils.isValidRefreshToken(refreshToken)) {
//        // 리프레시 토큰이 유효하지 않다면 새로운 리프레시 토큰을 생성합니다.
//        refreshToken = tokenUtils.saveRefreshToken(usersEntity);
//        authEntity.refreshUpdate(refreshToken); // 데이터베이스 엔티티를 업데이트합니다.
//    }
//
//    // 유효한 리프레시 토큰이 있는 경우, 새 액세스 토큰을 생성합니다.
//    accessToken = tokenUtils.generateJwtToken(usersEntity);
//
//    return TokenResponse.builder()
//            .ACCESS_TOKEN(accessToken)
//            .REFRESH_TOKEN(refreshToken)
//            .build();
//}
    @Transactional
    public TokenResponse signIn(UserRequest userRequest) {
        UsersEntity usersEntity = usersRepository
                .findByUserId(userRequest.getUserId())
                .orElseThrow(() -> new UserIdNotFoundException("존재하지 않는 회원입니다."));

        // 로그 출력
        System.out.println("DB Encoded Password: " + usersEntity.getUserPw()); // 데이터베이스에 저장된 암호화된 비밀번호
        System.out.println("Raw Password: " + userRequest.getUserPw()); // 사용자가 입력한 원본 비밀번호

        if (!passwordEncoder.matches(userRequest.getUserPw(), usersEntity.getUserPw())) {
            throw new InvalidPasswordException("잘못된 비밀번호입니다.");
        }
        String accessToken = tokenUtils.generateJwtToken(usersEntity);
        boolean hasPostitData = postItRepository.existsByBoard_User_IdAndPictureIsNotNull(usersEntity.getId());

        return TokenResponse.builder()
                .token(accessToken)
                .memberInfo(TokenResponse.MemberInfo.builder()
                        .userId(usersEntity.getId())
                        .nickName(usersEntity.getNickName())
                        .hasHistory(hasPostitData) // 또는 다른 로직에 따른 값
                        .build())
                .build();
        }
}
