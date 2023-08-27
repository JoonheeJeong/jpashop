package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.utils.ItemUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        Book book = ItemUtil.newBook(name, author, isbn);
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

    @DisplayName("상품 전체 조회")
    @Test
    void when3ItemsExists_thenFoundAll() {
        // given
        List<Book> books = List.of(
                ItemUtil.newBook("루터 선집", "마르틴 루터", "1234"),
                ItemUtil.newBook("이방인의 염려", "쇠얀 케르케고르", "1235"),
                ItemUtil.newBook("순전한 기독교", "C. S. 루이스", "1236")
        );
        books.forEach(repository::save);

        // when
        List<Item> all = repository.findAll();

        // then
        assertThat(all).hasSize(3);
        assertThat(all).isEqualTo(books);
    }
}
