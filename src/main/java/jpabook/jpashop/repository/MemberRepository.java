package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);
}
