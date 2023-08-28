package jpabook.jpashop.exception;

public class AlreadyDeliveredException extends IllegalStateException {

    public AlreadyDeliveredException() {
        this(ErrorMessage.ALREADY_DELIVERED);
    }

    public AlreadyDeliveredException(String s) {
        super(s);
    }
}
