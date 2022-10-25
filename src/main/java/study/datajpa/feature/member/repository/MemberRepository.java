package study.datajpa.feature.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.feature.member.dto.MemberDto;
import study.datajpa.feature.member.entity.Member;

import java.util.List;
import java.util.Optional;

//@Repository를 생략이 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(
            @Param("username") String username,
            @Param("age") int age
    );
    @Query("select new study.datajpa.feature.member.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByUsernames(@Param("names") List<String> names);

    List<Member> findListByUsername(String name); //컬렉션 Member findByUsername(String name); //단건

    Member findMemberByUsername(String name); //단건

    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    Page<Member> findByAge(int age, Pageable pageable);
}
