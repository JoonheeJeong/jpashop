package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughItemStock;
import jpabook.jpashop.service.ItemUpdateDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;

    @Builder.Default
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getItems().add(this);
    }

    public abstract void update(ItemUpdateDTO dto);

    protected void updateBase(ItemUpdateDTO dto) {
        name = dto.getName();
        price = dto.getPrice();
        stockQuantity = dto.getStackQuantity();
    }

    public void consumeStock(int quantity) {
        int newStockQuantity = stockQuantity - quantity;
        if (newStockQuantity < 0) {
            throw new NotEnoughItemStock();
        }
        stockQuantity = newStockQuantity;
    }

    public void restoreStock(int quantity) {
        stockQuantity += quantity;
    }
}
