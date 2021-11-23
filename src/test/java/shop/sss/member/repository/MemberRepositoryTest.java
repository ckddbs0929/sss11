package shop.sss.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.member.entity.Member;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @WithMockUser(username = "codd", roles = "USER")
    public void au(){
        Member mem = new Member();
        memberRepository.save(mem);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(mem.getId())
                .orElseThrow(EntityExistsException::new);
        System.out.println("regTime : " + member.getRegTime());
        System.out.println("upTime : " + member.getUpdateTime());
        System.out.println("cremem : " + member.getCreateBy());
        System.out.println("modimem : " + member.getModifiedBy());
    }
}