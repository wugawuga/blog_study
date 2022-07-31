package com.wugablog.meta.controller;

import com.wugablog.meta.model.Board;
import com.wugablog.meta.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model,
        @PageableDefault(size = 2, sort = "id", direction = Direction.DESC)
        Pageable pageable) {
        model.addAttribute("boards", boardService.boardList(pageable));
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Board board = boardService.boardById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardById(id));
        return "board/updateForm";
    }
}
