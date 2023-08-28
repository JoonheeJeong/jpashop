package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.utils.ItemUtil;
import jpabook.jpashop.utils.MemberUtil;
import jpabook.jpashop.utils.OrderItemUtil;
import jpabook.jpashop.utils.OrderUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JpaOrderRepositoryTest {

    @Autowired
    private JpaOrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("주문 저장")
    @Test
    void whenSaveOrder_thenShouldBeFound() {
        // given
        Member member = MemberUtil.newMember("정준희");
        memberRepository.save(member);

        List<Book> books = List.of(
                ItemUtil.newBook("루터 선집", "마르틴 루터", "1234"),
                ItemUtil.newBook("이방인의 염려", "쇠얀 케르케고르", "1235"),
                ItemUtil.newBook("순전한 기독교", "C. S. 루이스", "1236")
        );
        books.forEach(itemRepository::save);

        OrderItem[] orderItems = List.of(
                OrderItemUtil.newOrderItem(books.get(0), 2),
                OrderItemUtil.newOrderItem(books.get(1), 3),
                OrderItemUtil.newOrderItem(books.get(2), 1)).toArray(OrderItem[]::new);
        Order order = OrderUtil.newOrder(member, orderItems);

        // when
        orderRepository.save(order);

        // then
        Long id = order.getId();
        assertThat(id).isNotNull();
        Optional<Order> byId = orderRepository.findById(id);
        assertThat(byId).isPresent();
        Order orderFound = byId.get();
        assertThat(orderItems).hasSize(orderItems.length);
        List<OrderItem> foundOrderItems = orderFound.getOrderItems();
        IntStream.range(0, orderItems.length)
                .forEach(i -> assertThat(orderItems[i]).isEqualTo(foundOrderItems.get(i)));
    }
}