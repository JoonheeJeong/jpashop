package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.utils.ItemUtil;
import jpabook.jpashop.utils.MemberUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @DisplayName("상품 주문")
    @Test
    void whenOrderItems_thenFoundAndStockConsumed() {
        // given
        Member member = MemberUtil.newMember("정준희");
        memberRepository.save(member);

        List<Book> books = List.of(
                ItemUtil.newBook("루터 선집", "마르틴 루터", "1234"),
                ItemUtil.newBook("이방인의 염려", "쇠얀 케르케고르", "1235"),
                ItemUtil.newBook("순전한 기독교", "C. S. 루이스", "1236")
        );
        List<Integer> stockQuantitiesOriginal = books.stream()
                .map(Item::getStackQuantity)
                .collect(Collectors.toList());
        books.forEach(itemRepository::save);
        List<Long> itemIds = books.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        List<Integer> quantities = List.of(2, 3, 1);

        OrderRequestDTO dto = new OrderRequestDTO(member.getId(), itemIds, quantities);

        // when
        Long orderId = orderService.order(dto);

        // then
        Optional<Order> byId = orderRepository.findById(orderId);
        assertThat(byId).isPresent();
        Order orderFound = byId.get();

        assertThat(orderFound.getMember()).isSameAs(member);

        List<OrderItem> orderItemsFound = orderFound.getOrderItems();
        assertThat(orderItemsFound).hasSize(books.size());

        IntStream.range(0, books.size())
                .forEach(i -> {
                    OrderItem orderItem = orderItemsFound.get(i);
                    Item item = orderItem.getItem();
                    assertThat(item).isSameAs(books.get(i));
                    assertThat(orderItem.getQuantity()).isEqualTo(quantities.get(i));
                    assertThat(stockQuantitiesOriginal.get(i)).isEqualTo(item.getStackQuantity() + orderItem.getQuantity());
                });
    }
}