package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.JpaItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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