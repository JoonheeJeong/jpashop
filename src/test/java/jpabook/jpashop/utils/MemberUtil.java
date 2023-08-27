package jpabook.jpashop.utils;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;

public class MemberUtil {

    public static Member newMember(String name) {
        Address address = Address.builder()
                .city("Daejeon")
                .street("Daehak-ro")
                .zipcode("34134")
                .build();
        return Member.builder()
                .name(name)
                .address(address)
                .build();
    }
}
