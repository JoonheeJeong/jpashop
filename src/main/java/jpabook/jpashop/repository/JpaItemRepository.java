package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaItemRepository implements ItemRepository {

    private final EntityManager em;

    @Override
    public void save(Item item) {
        em.persist(item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }
}
