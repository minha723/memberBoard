package com.its.memberboard.test;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.CommentEntity;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.CommentRepository;
import com.its.memberboard.repository.MemberRepository;
import com.its.memberboard.service.BoardService;
import com.its.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class TestClass {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommentRepository commentRepository;


    public MemberDTO newMember(int i){
        MemberDTO memberDTO = new MemberDTO("email"+1, "pw"+1, "name"+i, "mobile"+i);
        return memberDTO;
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 조회 테스트")
    public void findBYId() throws IOException {
        // 회원 번호 1을 조회함 - 이메일은 email
        String memberEmail = memberService.findById(1L).getMemberEmail();
        assertThat(memberEmail).isEqualTo("email");
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 목록 테스트")
    public void findAll(){
        //회원에는 총 3명이 저장되어 있음
        List<MemberDTO> memberList = memberService.findAll();
        assertThat(memberList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 로그인테스트")
    public void loginTest() {
        //이메일이 email 비밀번호가 1234asdf! 인 객체가 있음
        MemberDTO loginMember = new MemberDTO();
        loginMember.setMemberEmail("email");
        loginMember.setMemberPassword("1234asdf!");
        MemberDTO findMemberDTO = memberService.login(loginMember);
        //로그인 결과가 not null 이면 테스트 통과
        assertThat(findMemberDTO).isNotEqualTo(null);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원 삭제 테이트")
    public void deleteTest(){
        // 회원번호 3을 삭제함
        memberService.deleteById(3L);
        assertThat(memberService.findById(3L)).isNull();
    }
    @Test
    @Transactional
    @Rollback
    @DisplayName("회원 수정 테이트")
    public void updateTest(){
        //회원번호 3번 memberEmail을 3번이메일로 수정함
        MemberDTO memberDTO = memberService.findById(3L);
        MemberDTO updateMemberDTO = memberService.findById(3L);
        updateMemberDTO.setMemberEmail("3번 이메일");
        memberService.update(updateMemberDTO);
        assertThat(memberService.findById(3L).getMemberEmail()).isNotEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("댓글 목록 출력 테스트")
    public void commentListTest(){
        // 댓글이 들어있는 게시글 엔티티원 엔티티 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(5L);
        // 게시글 엔티티의 댓글 목록 조회
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
            for(CommentEntity commentEntity: commentEntityList){
                System.out.println("commentEntity.getId() = " + commentEntity.getId());
                System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
            }
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("검색 테스트")
    public void searchTest(){
        List<BoardDTO> boardDTOList = boardService.search("제목");
        System.out.println("boardDTOList = " + boardDTOList);
    }



}
