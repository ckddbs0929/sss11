package shop.sss.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.entity.BoardEntity;
import shop.sss.repository.BoardRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardEntity updateBoard(Long id, String title, String content, String writer){
        BoardEntity board = boardRepository.selectBoardDetail(id);
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);
        return board;
    }

    @Transactional
    public void insert(BoardEntity board){
        boardRepository.insertBoard(board);
    }
}
