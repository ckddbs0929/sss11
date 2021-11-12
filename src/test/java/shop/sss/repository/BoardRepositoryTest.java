package shop.sss.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.sss.entity.BoardEntity;
import shop.sss.service.BoardService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Test
    public void test(){
        BoardEntity board = new BoardEntity();
        board.setWriter("dd");
        board.setContent("wer");
        board.setTitle("rweqreq");
        board.setInsertTime(LocalDateTime.now());
        boardService.insert(board);
    }

}