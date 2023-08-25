package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JpaMemberRepositoryTest {

    @Autowired
    private JpaMemberRepository repository;

    @DisplayName("회원 등록 후 조회 성공")
    @Test
    void whenMemberSaved_thenItShouldBeFound() {
        // given
        Member member = newMember();

        // when
        Long id = repository.save(member);

        // then
        Optional<Member> byId = repository.findById(id);
        assertThat(byId).isPresent();
        Member foundMember = byId.get();
        assertThat(foundMember).isSameAs(member);
    }

    @DisplayName("회원이 없으면 조회 실패")
    @Test
    void whenMemberNotExists_thenNotFound() {
        // given, when
        Optional<Member> byId = repository.findById(1L);

        // then
        assertThat(byId).isEmpty();
    }

    private static Member newMember() {
        Address address = Address.builder()
                .city("Daejeon")
                .street("Daehak-ro")
                .zipcode("34134")
                .build();
        return Member.builder()
                .name("정준희")
                .address(address)
                .build();
    }
}