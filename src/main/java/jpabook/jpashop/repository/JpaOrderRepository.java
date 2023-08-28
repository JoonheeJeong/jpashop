package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.type.OrderStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaOrderRepository(EntityManager em) {
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Order order) {
        em.persist(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = em.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAllByLikeNameAndStatus(OrderSearchCond cond) {
        return query.select(order)
                .from(order)
                .join(order.member, member)
                .where(likeName(cond.getName()), eqStatus(cond.getStatus()))
                .fetch();
    }

    private static BooleanExpression eqStatus(final OrderStatus status) {
        if (status != null) {
            return order.status.eq(status);
        }
        return null;
    }

    private static BooleanExpression likeName(final String name) {
        if (StringUtils.hasText(name)) {
            return member.name.like("%" + name + "%");
        }
        return null;
    }
}
