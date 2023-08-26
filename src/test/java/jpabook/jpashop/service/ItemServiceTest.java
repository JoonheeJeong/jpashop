package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.JpaItemRepository;
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
class ItemServiceTest {

    @Autowired
    private ItemService sut;
    @Autowired
    private JpaItemRepository repository;

    @DisplayName("상품 등록")
    @Test
    void when() {
        // given
        BookRegisterDTO dto = newBookRegisterDTO("루터 선집", "마르틴 루터", "1234");

        // when
        Long id = sut.register(dto);

        // then
        Optional<Item> byId = repository.findById(id);
        assertThat(byId).isPresent();
        assertThat(byId.get()).isInstanceOf(Book.class);
        Book book = (Book) byId.get();
        assertThat(book.getName()).isEqualTo(dto.getName());
    }

    @DisplayName("상품 목록 조회")
    @Test
    void when3BookExist_thenFoundThem() {
        // given
        List<BookRegisterDTO> dtos = List.of(
                newBookRegisterDTO("루터 선집", "마르틴 루터", "1234"),
                newBookRegisterDTO("이방인의 염려", "쇠얀 케르케고르", "1235"),
                newBookRegisterDTO("순전한 기독교", "C. S. 루이스", "1236")
        );
        dtos.forEach(sut::register);

        // when
        List<Item> items = sut.getList();

        // then
        assertThat(items).hasSize(dtos.size());
        IntStream.range(0, dtos.size())
                .forEach(i -> {
                    Item item = items.get(i);
                    assertThat(item).isInstanceOf(Book.class);
                    Book book = (Book) item;
                    BookRegisterDTO dto = dtos.get(i);
                    assertThat(book.getName()).isEqualTo(dto.getName());
                    assertThat(book.getAuthor()).isEqualTo(dto.getAuthor());
                    assertThat(book.getIsbn()).isEqualTo(dto.getIsbn());
                });
    }

    private BookRegisterDTO newBookRegisterDTO(String name, String author, String isbn) {
        return BookRegisterDTO.builder()
                .name(name)
                .price(10000)
                .stackQuantity(10)
                .author(author)
                .isbn(isbn)
                .build();
    }

}