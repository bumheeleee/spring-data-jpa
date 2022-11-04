package study.datajpa.feature.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.feature.member.entity.Member;
import study.datajpa.feature.member.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public String findMemberName(@PathVariable("id") Long id) throws Exception {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            return member.get().getUsername();
        }else{
            throw new Exception();
        }
    }
    // 도메인 클래스 컨버터 : 잘 사용하지 않는다.
    @GetMapping("/memberConvert/{id}")
    public String findMemberNameConvert(@PathVariable("id") Member member) throws Exception {
        return member.getUsername();
    }

    @PostConstruct
    public void init(){
        memberRepository.save(new Member("userA"));
    }

}
