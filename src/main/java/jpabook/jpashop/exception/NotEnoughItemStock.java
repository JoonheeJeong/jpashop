package jpabook.jpashop.exception;

public class NotEnoughItemStock extends IllegalStateException {

    public NotEnoughItemStock() {
        this(ErrorMessage.NOT_ENOUGH_ITEM_STOCK);
    }

    public NotEnoughItemStock(String s) {
        super();
    }
}
