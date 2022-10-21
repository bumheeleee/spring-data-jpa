package study.datajpa.feature.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.feature.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
