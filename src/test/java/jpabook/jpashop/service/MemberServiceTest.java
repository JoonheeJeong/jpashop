package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.DuplicateMemberException;
import jpabook.jpashop.exception.NotFoundMemberException;
import jpabook.jpashop.repository.JpaMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService sut;
    @Autowired
    private JpaMemberRepository repository;

    @DisplayName("회원 등록 성공")
    @Test
    void whenRegisterMember_thenFound() {
        // given
        MemberRegisterDto dto = new MemberRegisterDto("테스트유저");

        // when
        Long id = sut.register(dto);

        // then
        Optional<Member> byName = repository.findByName(dto.getName());
        assertThat(byName).isPresent();
        assertThat(id).isEqualTo(byName.get().getId());
    }

    @DisplayName("중복 회원 등록 시 예외 발생")
    @Test
    void whenRegisterDuplicateMember_thenThrowsException() {
        // given
        MemberRegisterDto dto1 = new MemberRegisterDto("테스트유저");
        sut.register(dto1);

        // when, then
        MemberRegisterDto dto2 = new MemberRegisterDto("테스트유저");
        assertThatThrownBy(() -> sut.register(dto2))
                .isInstanceOf(DuplicateMemberException.class);
    }



    @DisplayName("존재하는 회원 ID 조회")
    @Test
    void whenMemberExist_thenGotMember() {
        // given
        MemberRegisterDto dto = new MemberRegisterDto("테스트유저");
        final Long id = sut.register(dto);

        // when
        Member member = sut.getMember(id);

        // then
        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getName()).isEqualTo(dto.getName());
    }

    @DisplayName("존재하지 않는 회원 ID 조회")
    @Test
    void whenMemberNotExists_thenThrowsException() {
        // given
        final Long id = 1L;

        // when, then
        assertThatThrownBy(() -> sut.getMember(id))
                .isInstanceOf(NotFoundMemberException.class);
    }
}