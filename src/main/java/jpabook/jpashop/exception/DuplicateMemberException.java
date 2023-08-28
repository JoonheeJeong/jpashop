package jpabook.jpashop.exception;

public class DuplicateMemberException extends IllegalStateException {

    public DuplicateMemberException() {
        this(ErrorMessage.DUPLICATE_MEMBER);
    }

    public DuplicateMemberException(String s) {
        super(s);
    }
}
