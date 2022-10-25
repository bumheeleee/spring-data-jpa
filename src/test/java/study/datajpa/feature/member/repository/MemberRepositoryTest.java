package study.datajpa.feature.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import study.datajpa.feature.member.dto.MemberDto;
import study.datajpa.feature.member.entity.Member;
import study.datajpa.feature.member.entity.Team;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void testFindByUsernames() {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        teamRepository.save(t1);
        teamRepository.save(t2);

        Member m1 = new Member("a", 15, t1);
        Member m2 = new Member("b", 20, t2);
        Member m3 = new Member("c", 25, t1);
        Member m4 = new Member("d", 30, t2);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);

        List<Member> entity = memberRepository.findByUsernames(Arrays.asList("a", "b", "c", "d"));
        for (Member member : entity) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void testReturnType() {
        Member m1 = new Member("a", 15);
        Member m2 = new Member("b", 20);
        Member m3 = new Member("c", 25);
        Member m4 = new Member("d", 30);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);

        List<Member> result = memberRepository.findListByUsername("asdfa");
        // result.size = 0, null이 아니다!!(중요)
        System.out.println("result = " + result.size());


        Member b = memberRepository.findMemberByUsername("bsafas");
        // b = null
        System.out.println("b = " + b);

        Optional<Member> c = memberRepository.findOptionalByUsername("csfas");
        // c = Optional.empty
        System.out.println("c = " + c);

    }

    @Test
    public void testPage(){
        //given
        memberRepository.save(new Member("m1", 10));
        memberRepository.save(new Member("m2", 10));
        memberRepository.save(new Member("m3", 10));
        memberRepository.save(new Member("m4", 10));
        memberRepository.save(new Member("m5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // entity -> dto로 변환
        Page<MemberDto> memberDtos = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        //then
        long totalElements = page.getTotalElements();
        List<Member> members = page.getContent();

        assertEquals(totalElements, 5);
        assertEquals(members.size(), 3);

        System.out.println("members = " + members);

    }
}