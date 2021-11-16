package shop.sss.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.member.entity.Member;
import shop.sss.member.entity.MemberFormDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // controller 에서 POST 입력 값을 기반으로 memberFormDto 객체를 생성하는 것
    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@mail.com");
        memberFormDto.setName("길냥이");
        memberFormDto.setAddress("서울");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @Rollback(value = false)
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getName(), savedMember.getName());
        Assertions.assertEquals(member.getAddress(), savedMember.getAddress());
        Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
        Assertions.assertEquals(member.getRole(), savedMember.getRole());

    }

    @Test
    public void valid(){
        // given
        Member member1 = createMember();
        Member member2 = createMember();

        // when
        Throwable e = assertThrows(IllegalStateException.class, ()->{
            memberService.validateDuplicateMember(member2);
        });
        // then
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}