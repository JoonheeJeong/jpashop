package jpabook.jpashop.exception;

public class NotFoundItemException extends IllegalArgumentException {

    public NotFoundItemException(String s) {
        super(s);
    }
}
