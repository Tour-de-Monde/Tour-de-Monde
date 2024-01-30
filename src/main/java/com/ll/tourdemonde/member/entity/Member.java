package com.ll.tourdemonde.member.entity;

import com.ll.tourdemonde.post.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseTime {

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String memberName;

    private LocalDate birthDate;

    @Column(unique = true)
    private String phoneNumber;
}