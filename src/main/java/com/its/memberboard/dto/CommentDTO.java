package com.its.memberboard.dto;

import com.its.memberboard.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long commentBoardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static CommentDTO toDTO(CommentEntity commentEntity){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentBoardId(commentEntity.getCommentBoardId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCreatedTime(commentEntity.getCreatedTime());
        return commentDTO;
    }
}
