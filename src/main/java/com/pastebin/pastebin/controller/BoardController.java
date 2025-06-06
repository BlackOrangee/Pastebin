package com.pastebin.pastebin.controller;

import com.pastebin.pastebin.controller.request.dto.BoardRequestDTO;
import com.pastebin.pastebin.entity.dto.BoardDTO;
import com.pastebin.pastebin.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BoardDTO createBoard(@RequestParam Long userId,
                                @RequestBody @Valid BoardRequestDTO boardRequestDTO) {
        log.info("Create board");
        return boardService.createBoard(userId, boardRequestDTO);
    }

    @GetMapping(value = "/{boardId}")
    public BoardDTO getBoard(@PathVariable Long boardId) {
        log.info("Get board");
        return boardService.getBoard(boardId);
    }

    @PutMapping(value = "/{boardId}", consumes = "text/plain")
    public BoardDTO updateBoard(@PathVariable Long boardId,
                                @RequestParam Long userId,
                                @RequestBody String body) {
        log.info("Update board");
        return boardService.updateBoard(userId, boardId, BoardRequestDTO.builder().content(body).build());
    }
}
