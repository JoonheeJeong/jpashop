package jpabook.jpashop.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class BookUpdateDTO extends ItemUpdateDTO {

    private String author;
    private String isbn;
}
