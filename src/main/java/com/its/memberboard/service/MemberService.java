package com.its.memberboard.service;

import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberProfile = memberDTO.getMemberProfile();
        String memberProfileName = memberProfile.getOriginalFilename();
        memberProfileName = System.currentTimeMillis() + "-" + memberProfileName;
        String savePath ="D:\\springboot_img\\" + memberProfileName;
        if(!memberProfile.isEmpty()){
            memberProfile.transferTo(new File(savePath));
        } memberDTO.setMemberProfileName(memberProfileName);

        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        Long savedId = memberRepository.save(memberEntity).getId();
        return savedId;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
            return MemberDTO.toDTO(memberEntity);
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    public MemberDTO duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toDTO(memberEntity);
        }else {
            return null;
        }
    }

    public MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toDTO(memberEntity);
        }else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity member: memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(member));
        } return  memberDTOList;
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public Long update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        Long updatedId = memberRepository.save(memberEntity).getId();
        return updatedId;
    }
}
