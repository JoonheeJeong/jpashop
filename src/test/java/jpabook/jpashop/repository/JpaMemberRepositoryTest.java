package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.utils.MemberUtil;
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
        Member member = MemberUtil.newMember("정준희");

        // when
        repository.save(member);

        // then
        assertThat(member.getId()).isNotNull();
        Optional<Member> byId = repository.findById(member.getId());
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
        repository.save(MemberUtil.newMember("테스트1"));
        repository.save(MemberUtil.newMember("테스트2"));

        // when
        List<Member> all = repository.findAll();

        // then
        assertThat(all).hasSize(2);
        assertThat(all.get(0).getName()).isEqualTo("테스트1");
        assertThat(all.get(1).getName()).isEqualTo("테스트2");
    }

    @DisplayName("이름으로 목록 조회")
    @Test
    void whenFindAllByName_thenFound() {
        // given
        repository.save(MemberUtil.newMember("테스트1"));
        repository.save(MemberUtil.newMember("테스트2"));

        // when
        final String keyword = "테스트";
        List<Member> all = repository.findAllLikeName(keyword);

        // then
        assertThat(all).hasSize(2);
        all.forEach(member ->
                assertThat(member.getName())
                        .contains(keyword));
    }

    @DisplayName("존재하는 이름으로 조회")
    @Test
    void whenFindByExistingName_thenFound() {
        // given
        final String name = "안녕";
        repository.save(MemberUtil.newMember(name));

        // when
        Optional<Member> byName = repository.findByName(name);

        // then
        assertThat(byName).isPresent();
        assertThat(byName.get().getName()).isEqualTo(name);
    }

    @DisplayName("존재하지 않는 이름으로 조회")
    @Test
    void whenFindByNotExistingName_thenNotFound() {
        // given
        final String notExistingName = "없는 이름";

        // when
        Optional<Member> byName = repository.findByName(notExistingName);

        // then
        assertThat(byName).isEmpty();
    }
}