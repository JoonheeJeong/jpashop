package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    List<Member> findAllLikeName(String name);

    Optional<Member> findByName(String name);
}
