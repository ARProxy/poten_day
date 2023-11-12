package com.example.dto;

import lombok.*;

//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class TokenResponse {
//    private String token;
//    private String nickName;
//    private Long userId;
//    private boolean hasHistory;
//}
@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private MemberInfo memberInfo;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberInfo {
        private Long userId;
        private String nickName;
        private boolean hasHistory;
    }
}
