package jpabook.jpashop.exception;

public class DuplicateMemberException extends IllegalStateException {

    public DuplicateMemberException(String s) {
        super(s);
    }
}
