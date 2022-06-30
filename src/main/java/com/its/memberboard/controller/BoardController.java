package com.its.memberboard.controller;

import com.its.memberboard.common.PagingConst;
import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.service.BoardService;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    private String saveForm(){
        return "boardPages/save";
    }


    @PostMapping("/save")
    private String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        Long savedId = boardService.save(boardDTO);
        return "redirect:/board/"+ savedId;
    }

    @GetMapping("/")
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<BoardDTO> boardList = boardService.findAll(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardDTO boardDTO= boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findByBoardId(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("commentList", commentDTOList);
        return "boardPages/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/board/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO updateBoardDTO = boardService.findById(id);
        model.addAttribute("updateBoard", updateBoardDTO);
        return "boardPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO){
        Long updateId = boardService.update(boardDTO);
        return "redirect:/board/" + updateId;
    }

    @PostMapping("/search")
    public String search(@RequestParam("q") String q, Model model){
        List<BoardDTO> searchList = boardService.search(q);
        model.addAttribute("boardList", searchList);
        return "boardPages/list";
    }
}
