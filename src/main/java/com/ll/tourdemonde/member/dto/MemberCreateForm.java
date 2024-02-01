package com.ll.tourdemonde.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class  MemberCreateForm {
    @NotBlank(message = "아이디를 입력하세요")
    @Pattern(regexp = "^[a-zA-z0-9]{5,10}+$", message = "아이디는 특수문자를 제외한 5~10자여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "비밀번호는 영문자, 숫자를 포함한 8~16자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력하세요")
    private String passwordConfirm;

    @NotEmpty(message = "이메일을 입력하세요")
    @Email
    private String email;

    @NotBlank(message = "이름을 입력하세요")
    private String memberName;

    @NotNull(message = "생년월일을 입력하세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String phoneNumber;

    private String nickname;
}