package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional //데이터 변경하는 것은 Transactional이 꼭 있어야함
@Transactional(readOnly = true) // 기본으로 true로 해주고 쓰기에만 @Transactional 달아주는 것을 권장(readOnly가 기본 false이기 때문에 쓰기에 옵션은 안 적어도 됨)
@RequiredArgsConstructor // final 필드를 받는 생성자를 만들어줌
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository; // final로 해두면 컴파일 시점에 의존성이 주입이 됐는지 확인 가능

    // 생성자가 하나만 있는 경우 @Autowired 어노테이션이 없어도 스프링이 자동으로 주입해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional //쓰기에 readOnly = true를 할 경우 DB에 반영안됨, 기본적으로 readOnly가 false임
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
//    @Transactional(readOnly = true) //읽기 전용 트랜잭션, 단순히 조회만 할때는 성능적 이점이 있음
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
