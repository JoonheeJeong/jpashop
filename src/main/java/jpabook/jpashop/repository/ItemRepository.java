package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    void save(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();
}
