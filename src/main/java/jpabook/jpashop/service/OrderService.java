package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotFoundItemException;
import jpabook.jpashop.exception.NotFoundMemberException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(OrderRequestDTO dto) {
        Member member = getMember(dto);

        List<Item> items = getItems(dto.getItemIds());
        List<Integer> quantities = dto.getQuantities();

        int size = items.size();
        OrderItem[] orderItems = new OrderItem[size];
        for (int i = 0; i < size; ++i) {
            Item item = items.get(i);
            int quantity = quantities.get(i);

            item.consumeStock(quantity);

            orderItems[i] = new OrderItem(item, quantity);
        }

        Order order = new Order(member, orderItems);

        orderRepository.save(order);

        return order.getId();
    }

    private List<Item> getItems(List<Long> itemIds) {
        return itemIds.stream()
                .map(itemRepository::findById)
                .map(byId -> byId.orElseThrow(NotFoundItemException::new))
                .collect(Collectors.toList());
    }

    private Member getMember(OrderRequestDTO dto) {
        return memberRepository.findById(dto.getMemberId())
                .orElseThrow(NotFoundMemberException::new);
    }
}
