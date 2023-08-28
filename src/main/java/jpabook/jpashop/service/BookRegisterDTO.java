package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookRegisterDTO extends ItemRegisterDTO {

    private final String author;
    private final String isbn;

    @Override
    public Item toItem() {
        return Book.builder()
                .name(getName())
                .price(getPrice())
                .stockQuantity(getStackQuantity())
                .author(author)
                .isbn(isbn)
                .build();
    }
}
