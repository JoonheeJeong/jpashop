package jpabook.jpashop.exception;

public class NotFoundItemException extends IllegalArgumentException {

    public NotFoundItemException() {
        this(ErrorMessage.NOT_FOUND_ITEM);
    }

    public NotFoundItemException(String s) {
        super(s);
    }
}
