package jpabook.jpashop.exception;

public class NotFoundMemberException extends IllegalArgumentException {

    public NotFoundMemberException() {
        this(ErrorMessage.NOT_FOUND_MEMBER);
    }

    public NotFoundMemberException(String s) {
        super(s);
    }
}
