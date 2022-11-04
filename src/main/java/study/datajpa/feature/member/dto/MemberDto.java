package study.datajpa.feature.member.dto;

import lombok.Data;
import study.datajpa.feature.member.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //dto에서는 객체를 봐도 된다.
    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
