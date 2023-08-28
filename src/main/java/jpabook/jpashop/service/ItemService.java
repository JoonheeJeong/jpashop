package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotFoundItemException;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Long register(ItemRegisterDTO dto) {
        Item item = dto.toItem();
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> getList() {
        return itemRepository.findAll();
    }

    public Long update(ItemUpdateDTO dto) {
        Item item = itemRepository.findById(dto.getId())
                .orElseThrow(NotFoundItemException::new);
        item.update(dto);
        return item.getId();
    }
}
