package study.datajpa.feature.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.feature.member.dto.MemberDto;
import study.datajpa.feature.member.entity.Member;
import study.datajpa.feature.member.entity.Team;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = true)
class MemberRepositoryTest {
    @Autowired
    public MemberRepository memberRepository;
    @Autowired
    public TeamRepository teamRepository;

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

    @Test
    public void testQuery() {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        teamRepository.save(t1);
        teamRepository.save(t2);

        Member m1 = new Member("a", 15, t1);
        Member m2 = new Member("b", 20, t2);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("a", 15);

        assertEquals(m1, result.get(0));
        assertEquals(m1.getAge(), result.get(0).getAge());
    }


    @Test
    public void testFindMemberDto() {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");

        teamRepository.save(t1);
        teamRepository.save(t2);

        Member m1 = new Member("a", 15, t1);
        Member m2 = new Member("b", 20, t2);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto.getId() = " + dto.getId());
            System.out.println("dto.getUsername() = " + dto.getUsername());
            System.out.println("dto.getTeamName() = " + dto.getTeamName());
        }
    }
}