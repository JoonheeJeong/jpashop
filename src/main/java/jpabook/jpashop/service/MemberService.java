package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.DuplicateMemberException;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long register(MemberRegisterDto dto) {
        validateDuplicateName(dto.getName());
        Member member = Member.from(dto);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateName(String name) {
        Optional<Member> byName = memberRepository.findByName(name);
        if (byName.isPresent()) {
            throw new DuplicateMemberException("이미 존재하는 회원 이름입니다.");
        }
    }
}
