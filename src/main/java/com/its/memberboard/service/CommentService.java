package com.its.memberboard.service;

import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.CommentEntity;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.CommentRepository;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getCommentBoardId());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (optionalBoardEntity.isPresent()) {
                BoardEntity boardEntity = optionalBoardEntity.get();
                commentRepository.save(CommentEntity.toSaveEntity(commentDTO, memberEntity,boardEntity));
            }
        }
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        List<CommentEntity> commentEntityList = commentRepository.findByCommentBoardId(boardId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity comment:commentEntityList) {
            commentDTOList.add(CommentDTO.toDTO(comment));
        }return commentDTOList;
    }

//    public void deleteById(Long id) {
//        boardRepository.deleteById(id);
//    }
}
