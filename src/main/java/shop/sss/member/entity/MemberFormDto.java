package shop.sss.member.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto {
    // 회원가입 화면으로부터 넘어오는 가입정보를 담을 객체

    private String name;
    private String email;
    private String password;
    private String address;
}
