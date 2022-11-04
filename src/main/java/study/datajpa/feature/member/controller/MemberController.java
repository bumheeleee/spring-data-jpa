package study.datajpa.feature.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.feature.member.dto.MemberDto;
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

    @GetMapping("members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable  pageable){
        Page<Member> page = memberRepository.findAll(pageable);

        //엔티티로 반환하는 것은 좋지 않다! -> dto객체로 반환하기
        Page<MemberDto> members = page.map(
                member -> new MemberDto(member)
        );
        return members;
    }

    //test 데이터를 만들기 위해서
    @PostConstruct
    public void init(){
        for(int i = 0; i < 100; i ++){
            memberRepository.save(new Member("user"+ i, i+1));
        }

    }

}
