package jpabook.jpashop.domain.item;

import jpabook.jpashop.service.BookUpdateDTO;
import jpabook.jpashop.service.ItemUpdateDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@DiscriminatorValue("B")
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    @Override
    public void update(ItemUpdateDTO itemUpdateDTO) {
        BookUpdateDTO dto = (BookUpdateDTO) itemUpdateDTO;
        author = dto.getAuthor();
        isbn = dto.getIsbn();
        super.updateBase(itemUpdateDTO);
    }
}
