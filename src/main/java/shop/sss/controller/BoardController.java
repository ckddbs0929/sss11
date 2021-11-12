package shop.sss.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shop.sss.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
}
