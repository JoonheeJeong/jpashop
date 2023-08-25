package jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;
}
