package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Member member = newMember("정준희");

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

    @DisplayName("회원 목록 조회")
    @Test
    void given2MembersExist_whenFindAll_thenSize2() {
        // given
        repository.save(newMember("테스트1"));
        repository.save(newMember("테스트2"));

        // when
        List<Member> all = repository.findAll();

        // then
        assertThat(all).hasSize(2);
        assertThat(all.get(0).getName()).isEqualTo("테스트1");
        assertThat(all.get(1).getName()).isEqualTo("테스트2");
    }

    private static Member newMember(String name) {
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