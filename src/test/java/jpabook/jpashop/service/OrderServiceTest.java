package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.type.OrderStatus;
import jpabook.jpashop.exception.AlreadyDeliveredException;
import jpabook.jpashop.exception.NotEnoughItemStock;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
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

    @DisplayName("상품 주문")
    @Test
    void whenOrderItems_thenFoundAndStockConsumed() {
        // given
        Member member = newMember();
        List<Book> books = newBooks();
        List<Integer> stockQuantitiesOriginal = getStockQuantities(books);
        List<Long> itemIds = getItemIds(books);
        List<Integer> quantities = List.of(2, 3, 1);
        OrderRequestDTO dto = new OrderRequestDTO(member.getId(), itemIds, quantities);

        // when
        Long orderId = orderService.order(dto);

        // then
        Order orderFound = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderFound.getMember()).isSameAs(member);

        List<OrderItem> orderItemsFound = orderFound.getOrderItems();
        assertThat(orderItemsFound).hasSize(books.size());

        IntStream.range(0, books.size())
                .forEach(i -> {
                    OrderItem orderItem = orderItemsFound.get(i);
                    Item item = orderItem.getItem();
                    assertThat(item).isSameAs(books.get(i));
                    assertThat(orderItem.getQuantity()).isEqualTo(quantities.get(i));
                    assertThat(stockQuantitiesOriginal.get(i)).isEqualTo(item.getStockQuantity() + orderItem.getQuantity());
                });
    }

    @DisplayName("주문 취소")
    @Test
    void whenCancelOrder_thenStatusCancelledAndStockRestored() {
        // given
        Member member = newMember();
        List<Book> books = newBooks();
        List<Integer> stockQuantitiesOriginal = getStockQuantities(books);
        List<Long> itemIds = getItemIds(books);
        List<Integer> quantities = List.of(2, 3, 1);
        OrderRequestDTO dto = new OrderRequestDTO(member.getId(), itemIds, quantities);

        Long orderId = orderService.order(dto);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findById(orderId).orElseThrow();
        assertThat(order.getStatus()).isSameAs(OrderStatus.CANCEL);
        List<OrderItem> orderItemsFound = order.getOrderItems();
        assertThat(orderItemsFound).hasSize(books.size());
        IntStream.range(0, books.size())
                .forEach(i -> {
                    Integer stockQuantity = orderItemsFound.get(i).getItem().getStockQuantity();
                    Integer expected = stockQuantitiesOriginal.get(i);
                    assertThat(stockQuantity).isEqualTo(expected);
                });
    }

    @DisplayName("상품 주문 재고 수량 초과")
    @Test
    void whenOrderOverStock_thenThrows() {
        // given
        Member member = newMember();
        List<Book> books = newBooks();
        List<Long> itemIds = getItemIds(books);
        List<Integer> quantities = books.stream()
                .map(item -> item.getStockQuantity() + 1)
                .collect(Collectors.toList());
        OrderRequestDTO dto = new OrderRequestDTO(member.getId(), itemIds, quantities);

        // when, then
        assertThatThrownBy(() -> orderService.order(dto))
                .isInstanceOf(NotEnoughItemStock.class);
    }

    @DisplayName("배송 시작 후 주문 취소 불가")
    @Test
    void whenCancelDeliveredOrder_thenThrows() {
        // given
        Member member = newMember();
        List<Book> books = newBooks();
        List<Long> itemIds = getItemIds(books);
        List<Integer> quantities = Collections.nCopies(books.size(), 1);
        OrderRequestDTO dto = new OrderRequestDTO(member.getId(), itemIds, quantities);

        Long orderId = orderService.order(dto);

        Order order = orderRepository.findById(orderId).orElseThrow();
        order.getDelivery().delivered();

        // when, then
        assertThatThrownBy(() -> orderService.cancelOrder(orderId))
                .isInstanceOf(AlreadyDeliveredException.class);
    }

    private Member newMember() {
        Member member = MemberUtil.newMember("정준희");
        memberRepository.save(member);
        return member;
    }

    private List<Book> newBooks() {
        List<Book> books = List.of(
                ItemUtil.newBook("루터 선집", "마르틴 루터", "1234"),
                ItemUtil.newBook("이방인의 염려", "쇠얀 케르케고르", "1235"),
                ItemUtil.newBook("순전한 기독교", "C. S. 루이스", "1236")
        );
        books.forEach(itemRepository::save);
        return books;
    }

    private static List<Integer> getStockQuantities(List<Book> books) {
        return books.stream()
                .map(Item::getStockQuantity)
                .collect(Collectors.toList());
    }

    private static List<Long> getItemIds(List<Book> books) {
        return books.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
    }
}