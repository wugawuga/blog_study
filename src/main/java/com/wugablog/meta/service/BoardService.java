package com.wugablog.meta.service;

import com.wugablog.meta.model.Board;
import com.wugablog.meta.model.ReplySaveDTO;
import com.wugablog.meta.model.User;
import com.wugablog.meta.repository.BoardRepository;
import com.wugablog.meta.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    @Transactional
    public void write(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board boardById(Long id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("없는 글이에요"));
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Board requestBoard) {
        Board boardById = boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("없는 글이에요"));
        boardById.setTitle(requestBoard.getTitle());
        boardById.setContent(requestBoard.getContent());
    }

    @Transactional
    public int writeReply(ReplySaveDTO replySaveDTO) {
//        Board boardById = boardRepository.findById(replySaveDTO.getBoardId())
//            .orElseThrow(() -> new IllegalArgumentException("댓글 작성 오류 : 게시글을 찾을 수 없어요"));
//        User userById = userRepository.findById(replySaveDTO.getUserId())
//            .orElseThrow(
//                () -> new IllegalArgumentException("댓글 작성 오류 : 등록된 회원이 없어요")
//            );
//
//        Reply reply = new Reply();
//        reply.creatReply(userById, boardById, replySaveDTO.getContent());
//        replyRepository.save(reply);
        int result = replyRepository.replyDtoSave(
            replySaveDTO.getUserId(),
            replySaveDTO.getBoardId(),
            replySaveDTO.getContent()
        );
        return result;
    }

    @Transactional
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
