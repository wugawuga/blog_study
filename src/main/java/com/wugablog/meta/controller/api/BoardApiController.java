package com.wugablog.meta.controller.api;

import com.wugablog.meta.config.auth.PrincipalDetail;
import com.wugablog.meta.dto.ResponseDto;
import com.wugablog.meta.model.Board;
import com.wugablog.meta.model.Reply;
import com.wugablog.meta.model.ReplySaveDTO;
import com.wugablog.meta.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board,
        @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.write(board, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        boardService.delete(id);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> updateById(@PathVariable Long id, @RequestBody Board board) {
        boardService.update(id, board);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> saveReply(@RequestBody ReplySaveDTO replySaveDTO) {
        int result = boardService.writeReply(replySaveDTO);
        return new ResponseDto<>(HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> deleteReply(@PathVariable Long replyId) {
        boardService.deleteReply(replyId);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
