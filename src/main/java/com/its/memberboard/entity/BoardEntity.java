package com.its.memberboard.entity;


import com.its.memberboard.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(name = "board_title", nullable = false)
    private String boardTitle;
    @Column(name = "board_writer", nullable = false)
    private String boardWriter;
    @Column(name = "board_contents")
    private String boardContents;
    @Column(name = "board_hits")
    private int boardHits;
    @Column(name = "board_file_name")
    private String boardFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toSaveEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
        boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardFileName(boardDTO.getBoardFileName());
        boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }
}
