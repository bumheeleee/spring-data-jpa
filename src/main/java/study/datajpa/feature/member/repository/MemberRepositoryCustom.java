package study.datajpa.feature.member.repository;

import study.datajpa.feature.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
