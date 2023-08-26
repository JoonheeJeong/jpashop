package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JpaItemRepositoryTest {
    
    @Autowired
    private ItemRepository repository;
    
    @DisplayName("상품 저장")
    @Test
    void whenSaveItem_thenShouldBeFound() {
        // given
        final String name = "루터 선집";
        final String author = "마르틴 루터";
        final String isbn = "1234";
        Book book = newBook(name, author, isbn);
        repository.save(book);

        // when
        Long id = book.getId();
        assertThat(id).isNotNull();
        Optional<Item> byId = repository.findById(id);

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get()).isInstanceOf(Book.class);
        Book foundBook = (Book) byId.get();
        assertThat(foundBook.getName()).isEqualTo(name);
        assertThat(foundBook.getAuthor()).isEqualTo(author);
        assertThat(foundBook.getIsbn()).isEqualTo(isbn);
    }
    
    private Book newBook(String name, String author, String isbn) {
        return Book.builder()
                .name(name)
                .author(author)
                .isbn(isbn)
                .price(10000)
                .stackQuantity(10)
                .build();
    }

}