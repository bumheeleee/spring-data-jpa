package study.datajpa.feature.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.feature.member.entity.Member;

import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberJpaRepositoryTest {

    @Autowired
    public MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        //한 트랜젝션 안에서는 모두 동일하다 : 생성한 멤버 == 저장(생성한 멤버) == 찾기(저장한 멤버)
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getUsername(), findMember.getUsername());
        assertEquals(savedMember, findMember);
        assertEquals(member, findMember);
    }

    @Test
    public void testCRUD() {
        Member m1 = new Member("a");
        Member m2 = new Member("b");
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        Member findm1 = memberJpaRepository.findById(m1.getId()).get();
        Member findm2 = memberJpaRepository.findById(m2.getId()).get();

        //단건 조회 검증
        assertEquals(m1, findm1);
        assertEquals(m2, findm2);

        //findAll 조회 검증
        assertEquals(memberJpaRepository.findAll().size(), 2);

        //카운트 검증
        assertEquals(memberJpaRepository.count(), 2);

        //삭제 검증
        memberJpaRepository.delete(m1);
        memberJpaRepository.delete(m2);
        assertEquals(memberJpaRepository.findAll().size(), 0);


    }
}