package com.its.memberboard.test;

import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Member;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class TestClass {

    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO memberDTO = new MemberDTO("password"+1, "Name"+1, "email"+i, "mobile"+i);
        return memberDTO;
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void saveTest() throws IOException {
        Long saveId = memberService.save(newMember(1));
        MemberDTO findDTO = memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(findDTO.getMemberEmail());
    }

}
