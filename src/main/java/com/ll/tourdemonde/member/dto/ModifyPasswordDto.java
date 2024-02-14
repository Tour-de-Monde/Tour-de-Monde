package com.ll.tourdemonde.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordDto {
    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "비밀번호는 영문자, 숫자를 포함한 8~16자여야 합니다."
    )
    private String existingPassword;

    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "비밀번호는 영문자, 숫자를 포함한 8~16자여야 합니다."
    )
    private String newPassword;

    @NotBlank(message = "회원님의 비밀번호를 적어주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "비밀번호는 영문자, 숫자를 포함한 8~16자여야 합니다."
    )
    private String checkNewPassword;
}
