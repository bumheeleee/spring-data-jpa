package study.datajpa.feature.member.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.feature.member.repository.MemberJpaRepository;
import study.datajpa.feature.team.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @PersistenceContext
    public EntityManager em;
    @Autowired
    public MemberJpaRepository memberJpaRepository;
    @Test
    public void testMember(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        //Member member5 = new Member("member5", 50, null);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();


        for (Member member : members) {
            System.out.println("-------------------------------");
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
            System.out.println("-------------------------------");
        }
    }

    @Test
    public void testCollection(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("memberName1", 10, teamA);
        Member member2 = new Member("memberName2", 20, teamA);
        Member member3 = new Member("memberName3", 10, teamB);
        Member member4 = new Member("memberName4", 20, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear();

        List<String> strings = memberJpaRepository.collectionJoin();
        for (String string : strings) {
            System.out.println("string = " + string);
        }
    }

    @Test
    public void JpaEventBaseEntityTest() throws Exception{
        //given
        Member m1 = new Member("m1");
        memberJpaRepository.save(m1); // @PrePersist가 일어난다.

        Thread.sleep(10000);
        m1.setUsername("m2");

        em.flush(); //@PreUpdate
        em.clear();

        //when
        Member findMember = memberJpaRepository.find(m1.getId());

        //then
        System.out.println("findMember.getCreateDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getUpdateDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreateBy() = " + findMember.getCreateBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }


}