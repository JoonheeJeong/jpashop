package jpabook.jpashop.utils;

import jpabook.jpashop.domain.item.Book;

public class ItemUtil {

    public static Book newBook(String name, String author, String isbn) {
        return Book.builder()
                .name(name)
                .author(author)
                .isbn(isbn)
                .price(10000)
                .stockQuantity(10)
                .build();
    }
}
