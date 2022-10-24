package study.datajpa.feature.member.entity;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }else{
            throw new NullPointerException("잘못된 입력값을 입력하였습니다.");
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    //-- 연관관계 편의 매소드 --//
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
