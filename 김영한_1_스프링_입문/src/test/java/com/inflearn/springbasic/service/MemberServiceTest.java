package com.inflearn.springbasic.service;

import com.inflearn.springbasic.domain.Member;
import com.inflearn.springbasic.repository.MemberRepository;
import com.inflearn.springbasic.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void tearDown() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        // given
        Member member = new Member();
        member.setName("member1");

        // when
        Long saveId = memberService.join(member);

        // then
        Member foundMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @Test
    @DisplayName("중복 회원 회원가입")
    void join_DUPLICATED_MEMBER() {
        // given
        Member member1 = new Member();
        member1.setName("member1");

        Member member2 = new Member();
        member2.setName("member1");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}