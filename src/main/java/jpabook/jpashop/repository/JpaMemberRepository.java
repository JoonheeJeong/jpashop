package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findAllLikeName(String name) {
        final String pattern = "'%" + name + "%'";
        return em.createQuery("select m from Member m where m.name like " + pattern, Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByName(String name) {
        try {
            Member member = em.createQuery("select m from Member m where m.name=:name", Member.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
