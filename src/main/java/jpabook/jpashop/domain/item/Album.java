package jpabook.jpashop.domain.item;

import jpabook.jpashop.service.ItemUpdateDTO;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;

    @Override
    public void update(ItemUpdateDTO dto) {

    }
}
