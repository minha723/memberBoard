package com.its.memberboard.entity;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column
    private Long commentBoardId;
    @Column
    private String commentWriter;
    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, MemberEntity memberEntity, BoardEntity boardEntity){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentBoardId(commentDTO.getCommentBoardId());
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setMemberEntity(memberEntity);
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

}
