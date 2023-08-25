package jpabook.jpashop.exception;

public class NotFoundMemberException extends IllegalArgumentException {

    public NotFoundMemberException(String s) {
        super(s);
    }
}
