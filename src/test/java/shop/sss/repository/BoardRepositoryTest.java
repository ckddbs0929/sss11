package shop.sss.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.sss.entity.BoardEntity;
import shop.sss.service.BoardService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

}