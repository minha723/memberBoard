package com.its.memberboard.service;

import com.its.memberboard.common.PagingConst;
import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;


    public Long save(BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis()+"-"+ boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        if(!boardFile.isEmpty()){
            boardFile.transferTo(new File(savePath));
        }
        boardDTO.setBoardFileName(boardFileName);
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail((boardDTO.getBoardWriter()));

        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            Long savedId = boardRepository.save(BoardEntity.toSaveEntity(boardDTO, memberEntity)).getId();
            return savedId;
        } else {
            return null;
        }
    }


    public Page<BoardDTO> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page==1) ? 0 : (page-1);
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardList = boardEntities.map(board-> new BoardDTO(board.getId(),
                board.getBoardTitle(),
                board.getBoardWriter(),
                board.getBoardHits(),
                board.getCreatedTime()
        ));
        return boardList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        boardRepository.boardHits(id);
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.toDTO(boardEntity);
        }else {
            return null;
        }
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public Long update(BoardDTO boardDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            Long updateId = boardRepository.save(BoardEntity.toUpdateEntity(boardDTO, memberEntity)).getId();
            return updateId;
        }else{
            return null;
        }
    }

    public List<BoardDTO> search(String q) {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContainingOrBoardWriterContaining(q, q);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity board: boardEntityList) {
            boardDTOList.add(BoardDTO.toDTO(board));
        }
            return boardDTOList;
    }


}
