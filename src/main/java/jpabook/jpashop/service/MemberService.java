package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    //읽기가 아닌 쓰기에 readOnly = true를 넣지 않는것이 좋다. 데이터가 변할수도 있기 때문
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }


    /**
     * 회원 전체조회
     */

    @Transactional(readOnly = true)
    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne9(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
