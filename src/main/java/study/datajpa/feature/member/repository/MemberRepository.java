package study.datajpa.feature.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.feature.member.entity.Member;

//@Repository를 생략이 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
}
