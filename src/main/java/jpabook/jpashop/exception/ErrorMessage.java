package jpabook.jpashop.exception;

public interface ErrorMessage {

    String DUPLICATE_MEMBER = "이미 존재하는 회원 이름입니다.";
    String NOT_FOUND_MEMBER = "해당 ID의 회원이 존재하지 않습니다.";
    String NOT_FOUND_ITEM = "해당 id의 삼품을 찾을 수 없습니다.";
    String NOT_ENOUGH_ITEM_STOCK = "상품의 재고가 부족합니다.";
    String NOT_FOUND_ORDER = "해당 ID의 주문이 존재하지 않습니다.";
    String ALREADY_DELIVERED = "이미 배송이 시작되어 주문을 취소할 수 없습니다.";
}
