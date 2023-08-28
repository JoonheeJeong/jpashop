package jpabook.jpashop.exception;

public class NotFoundOrderException extends IllegalArgumentException {

    public NotFoundOrderException() {
        this(ErrorMessage.NOT_FOUND_ORDER);
    }

    public NotFoundOrderException(String s) {
        super(s);
    }
}
