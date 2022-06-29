package com.its.memberboard.dto;

import com.its.memberboard.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberMobile;
    private String memberProfileName;
    private MultipartFile memberProfile;


    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberProfileName(memberEntity.getMemberProfileName());
        return memberDTO;
    }

    public MemberDTO(String memberId, String memberPassword, String memberName, String memberEmail, String memberMobile) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberMobile = memberMobile;
    }
}