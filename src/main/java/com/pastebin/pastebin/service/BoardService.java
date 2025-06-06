package com.pastebin.pastebin.service;

import com.pastebin.pastebin.controller.request.dto.BoardRequestDTO;
import com.pastebin.pastebin.entity.dto.BoardDTO;

public interface BoardService {

    BoardDTO createBoard(Long userId, BoardRequestDTO boardRequestDTO);

    BoardDTO getBoard(Long id);

    BoardDTO updateBoard(Long userId, Long id, BoardRequestDTO boardRequestDTO);
}
