package com.ll.tourdemonde.member.security.authorization;

import lombok.Getter;

// 회원 권한 관리
@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;

    MemberRole(String value) {
        this.value = value;
    }
}