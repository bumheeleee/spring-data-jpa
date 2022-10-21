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
class MemberRepositoryTest {


    @Autowired
    public MemberRepository memberRepository;


    @Test
    public void testMember(){
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getUsername(), findMember.getUsername());
        assertEquals(savedMember, findMember);
        assertEquals(member, findMember);
    }

    @Test
    public void testCRUD() {
        Member m1 = new Member("a");
        Member m2 = new Member("b");
        memberRepository.save(m1);
        memberRepository.save(m2);
        Member findm1 = memberRepository.findById(m1.getId()).get();
        Member findm2 = memberRepository.findById(m2.getId()).get();

        //단건 조회 검증
        assertEquals(m1, findm1);
        assertEquals(m2, findm2);

        //findAll 조회 검증
        assertEquals(memberRepository.findAll().size(), 2);

        //카운트 검증
        assertEquals(memberRepository.count(), 2);

        //삭제 검증
        memberRepository.delete(m1);
        memberRepository.delete(m2);
        assertEquals(memberRepository.findAll().size(), 0);
    }

}