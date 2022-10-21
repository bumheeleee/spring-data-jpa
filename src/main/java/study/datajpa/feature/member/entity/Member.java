package study.datajpa.feature.member.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
