package com.ll.tourdemonde.member.service;

import com.ll.tourdemonde.base.genFile.entity.GenFile;
import com.ll.tourdemonde.base.genFile.service.GenFileService;
import com.ll.tourdemonde.global.app.AppConfig;
import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.global.util.Ut;
import com.ll.tourdemonde.member.dto.ModifyNicknameDto;
import com.ll.tourdemonde.member.dto.ModifyPasswordDto;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenFileService genFileService;

    @Transactional
    public Member createMember(String username, String password,
                               String email, String memberName,
                               LocalDate birthDate, String phoneNumber, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .memberName(memberName)
                .birthDate(birthDate)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .build();

        this.memberRepository.save(member);
        return member;
    }

    //메일을 받아 일치하는 회원 찾기. 찾은 결과는 있을 수도, 없을 수도 있음
    public Optional<Member> findMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member;
    }

    //멤버 객체로부터 아이디 반환
    public String findUsername(Member member) {
        String username = member.getUsername();
        return username;
    }

    @Transactional
    public Member renewPassword(Member member, String newPassword) {
        member.setPassword(passwordEncoder.encode(newPassword)); // 새로 발급받은 비밀번호를 암호화 하여 DB에 저장

        this.memberRepository.save(member);
        return member;
    }

    @Transactional
    public RsData<Member> join(String username, String password, String email, String memberName, LocalDate birthDate, String phoneNumber, String nickname) {
        return join(username, password, email, memberName, birthDate, phoneNumber, nickname, null);
    }

    @Transactional
    public RsData<Member> join(String username, String password,
                               String email, String memberName,
                               LocalDate birthDate, String phoneNumber, String nickname, String profileImgFilePath) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 회원입니다.");
        }
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .memberName(memberName)
                .birthDate(birthDate)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .build();
        memberRepository.save(member);
        if (Ut.str.hasLength(profileImgFilePath)) {
            saveProfileImg(member, profileImgFilePath);
        }
        return RsData.of("200", "%s님 환영합니다. 회원가입이 완료되었습니다. 로그인 후 이용해주세요.".formatted(member.getUsername()), member);
    }

    private void saveProfileImg(Member member, String profileImgFilePath) {
        genFileService.save(member.getModelName(), member.getId(), "common", "profileImg", 1, profileImgFilePath);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImgUrl) {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) return RsData.of("200", "이미 존재합니다.", opMember.get());

        String filePath = Ut.str.hasLength(profileImgUrl) ? Ut.file.downloadFileByHttp(profileImgUrl, AppConfig.getTempDirPath()) : "";

        return join(username, "", "", "", null, "", nickname, filePath);
    }

    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new NullPointerException("member not found");
        }
    }

    public String getProfileImgUrl(Member member) {
        return Optional.ofNullable(member)
                .flatMap(this::findProfileImgUrl)
                .orElse("https://placehold.co/30x30?text=UU");
    }

    private Optional<String> findProfileImgUrl(Member member) {
        return genFileService.findBy(
                        member.getModelName(), member.getId(), "common", "profileImg", 1
                )
                .map(GenFile::getUrl);
    }

    public void checkNickname(ModifyNicknameDto modifyNicknameDto) {
        memberRepository.findByNickname(modifyNicknameDto.getNickname()).ifPresent(
                user -> {
                    throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
                }
        );
    }

    @Transactional
    public void modifyNickname(Member memberInfo, ModifyNicknameDto modifyNicknameDto) {
        checkNickname(modifyNicknameDto);

        Member modifyUser = Member.builder()
                .nickname(modifyNicknameDto.getNickname())
                .build();

        memberInfo.modifyNickname(modifyUser);
    }

    @Transactional
    public void modifyPassword(Member memberInfo, ModifyPasswordDto modifyPasswordDto) {
        if (!passwordEncoder.matches(modifyPasswordDto.getExistingPassword(), memberInfo.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!modifyPasswordDto.getNewPassword().equals(modifyPasswordDto.getCheckNewPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        Member changeUser = Member.builder()
                .password(passwordEncoder.encode(modifyPasswordDto.getNewPassword()))
                .build();

        memberInfo.modifyPassword(changeUser);
    }
}
