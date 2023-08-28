package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.type.OrderStatus;
import jpabook.jpashop.utils.ItemUtil;
import jpabook.jpashop.utils.MemberUtil;
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
        Member member = newMember("정준희");
        List<Book> books = newBooks();
        OrderItem[] orderItems = books.stream()
                .map(OrderItem::new)
                .toArray(OrderItem[]::new);
        Order order = new Order(member, orderItems);

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

    @DisplayName("주문 전체 조회")
    @Test
    void whenFindAll_thenFoundThem() {
        // given
        Member member1 = newMember("정준희");
        Member member2 = newMember("희준정");
        List<Book> books = newBooks();
        OrderItem[] orderItems1 = List.of(
                new OrderItem(books.get(0))).toArray(OrderItem[]::new);
        OrderItem[] orderItems2 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(2))).toArray(OrderItem[]::new);
        OrderItem[] orderItems3 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(3))).toArray(OrderItem[]::new);
        List<Order> orders = List.of(
                new Order(member1, orderItems1),
                new Order(member1, orderItems2),
                new Order(member2, orderItems3));
        orders.forEach(orderRepository::save);

        // when
        List<Order> ordersFound = orderRepository.findAllByLikeNameAndStatus(new OrderSearchCond());

        // then
        assertThat(ordersFound).hasSize(orders.size());
        assertThat(ordersFound).isEqualTo(orders);
    }

    @DisplayName("주문 회원명으로 조회")
    @Test
    void whenFindOrderByLikeName_thenFoundThem() {
        // given
        Member member1 = newMember("정준희");
        Member member2 = newMember("희준정");
        List<Book> books = newBooks();
        OrderItem[] orderItems1 = List.of(
                new OrderItem(books.get(0))).toArray(OrderItem[]::new);
        OrderItem[] orderItems2 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(2))).toArray(OrderItem[]::new);
        OrderItem[] orderItems3 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(3))).toArray(OrderItem[]::new);
        List<Order> orders = List.of(
                new Order(member1, orderItems1),
                new Order(member1, orderItems2),
                new Order(member2, orderItems3));
        orders.forEach(orderRepository::save);

        OrderSearchCond cond = new OrderSearchCond("준희");

        // when
        List<Order> ordersFound = orderRepository.findAllByLikeNameAndStatus(cond);

        // then
        assertThat(ordersFound).hasSize(2);
        assertThat(ordersFound).isEqualTo(List.of(orders.get(0), orders.get(1)));
    }

    @DisplayName("주문 상태로 조회")
    @Test
    void whenFindOrderByStatus_thenFoundThem() {
        // given
        Member member1 = newMember("정준희");
        Member member2 = newMember("희준정");
        List<Book> books = newBooks();
        OrderItem[] orderItems1 = List.of(
                new OrderItem(books.get(0))).toArray(OrderItem[]::new);
        OrderItem[] orderItems2 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(2))).toArray(OrderItem[]::new);
        OrderItem[] orderItems3 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(3))).toArray(OrderItem[]::new);
        List<Order> orders = List.of(
                new Order(member1, orderItems1),
                new Order(member1, orderItems2),
                new Order(member2, orderItems3));
        orders.forEach(orderRepository::save);

        orders.get(1).cancel();

        OrderSearchCond cond = new OrderSearchCond(OrderStatus.CANCEL);

        // when
        List<Order> ordersFound = orderRepository.findAllByLikeNameAndStatus(cond);

        // then
        assertThat(ordersFound).hasSize(1);
        assertThat(ordersFound).isEqualTo(List.of(orders.get(1)));
    }

    @DisplayName("주문 회원명과 상태로 조회")
    @Test
    void whenFindOrderByLikeNameAndStatus_thenFoundThem() {
        // given
        Member member1 = newMember("정준희");
        Member member2 = newMember("희준정");
        List<Book> books = newBooks();
        OrderItem[] orderItems1 = List.of(
                new OrderItem(books.get(0))).toArray(OrderItem[]::new);
        OrderItem[] orderItems2 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(2))).toArray(OrderItem[]::new);
        OrderItem[] orderItems3 = List.of(
                new OrderItem(books.get(1)),
                new OrderItem(books.get(3))).toArray(OrderItem[]::new);
        List<Order> orders = List.of(
                new Order(member1, orderItems1),
                new Order(member1, orderItems2),
                new Order(member2, orderItems3));
        orders.forEach(orderRepository::save);

        orders.get(1).cancel();

        OrderSearchCond cond = new OrderSearchCond("준희", OrderStatus.ORDER);

        // when
        List<Order> ordersFound = orderRepository.findAllByLikeNameAndStatus(cond);

        // then
        assertThat(ordersFound).hasSize(1);
        assertThat(ordersFound).isEqualTo(List.of(orders.get(0)));
    }

    private List<Book> newBooks() {
        List<Book> books = List.of(
                ItemUtil.newBook("루터 선집", "마르틴 루터", "1234"),
                ItemUtil.newBook("이방인의 염려", "쇠얀 케르케고르", "1235"),
                ItemUtil.newBook("순전한 기독교", "C. S. 루이스", "1236"),
                ItemUtil.newBook("내면소통", "김주환", "1237"));
        books.forEach(itemRepository::save);
        return books;
    }

    private Member newMember(String name) {
        Member member = MemberUtil.newMember(name);
        memberRepository.save(member);
        return member;
    }
}