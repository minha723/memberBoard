package com.its.memberboard.controller;

import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

        private final CommentService commentService;

    @PostMapping("/save")
    public @ResponseBody List<CommentDTO> save(@ModelAttribute CommentDTO commentDTO){
        commentService.save(commentDTO);
        List<CommentDTO> commentDTOList = commentService.findByBoardId(commentDTO.getCommentBoardId());
        return commentDTOList;
    }

//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable("id") Long id){
//        commentService.deleteById(id);
//        return "redirect:/board/";
//    }
}
