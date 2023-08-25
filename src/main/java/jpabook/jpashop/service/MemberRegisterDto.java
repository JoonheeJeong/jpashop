package jpabook.jpashop.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {

    private final String name;
    private final String city;
    private final String street;
    private final String zipcode;

    public MemberRegisterDto(String name) {
        this(name, null, null, null);
    }
}
