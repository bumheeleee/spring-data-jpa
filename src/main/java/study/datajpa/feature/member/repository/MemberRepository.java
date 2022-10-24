package study.datajpa.feature.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.feature.member.entity.Member;

import java.util.List;

//@Repository를 생략이 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.username = :username and m.age = :age")
    public List<Member> findUser(
            @Param("username") String username,
            @Param("age") int age
    );
}
