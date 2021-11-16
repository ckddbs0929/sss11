package shop.sss.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.sss.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
