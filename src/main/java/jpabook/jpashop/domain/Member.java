package jpabook.jpashop.domain;

import jpabook.jpashop.service.MemberRegisterDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public static Member from(MemberRegisterDto dto) {
        return Member.builder()
                .name(dto.getName())
                .address(Address.from(dto))
                .build();
    }
}
