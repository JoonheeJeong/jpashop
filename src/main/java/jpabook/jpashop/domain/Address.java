package jpabook.jpashop.domain;

import jpabook.jpashop.service.MemberRegisterDto;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public static Address from(MemberRegisterDto dto) {
        return new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());
    }
}
