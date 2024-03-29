package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.DuplicateMemberException;
import jpabook.jpashop.exception.NotFoundMemberException;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
            throw new DuplicateMemberException();
        }
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getMembersContainName(String name) {
        return memberRepository.findAllLikeName(name);
    }
}
