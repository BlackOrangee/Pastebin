package com.pastebin.pastebin.service.impl;

import com.pastebin.pastebin.DateFormatter;
import com.pastebin.pastebin.LineComparer;
import com.pastebin.pastebin.controller.request.dto.BoardRequestDTO;
import com.pastebin.pastebin.entity.Board;
import com.pastebin.pastebin.entity.Sign;
import com.pastebin.pastebin.entity.User;
import com.pastebin.pastebin.entity.dto.BoardDTO;
import com.pastebin.pastebin.entity.dto.mapper.BoardDTOMapper;
import com.pastebin.pastebin.exeption.ErrorList;
import com.pastebin.pastebin.exeption.ServerException;
import com.pastebin.pastebin.repository.BoardRepository;
import com.pastebin.pastebin.repository.SignRepository;
import com.pastebin.pastebin.repository.UserRepository;
import com.pastebin.pastebin.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final SignRepository signRepository;

    private final BoardDTOMapper boardDTOMapper;



    @Override
    @Transactional
    public BoardDTO createBoard(Long userId, BoardRequestDTO boardRequestDTO) {
        log.debug("Create board {}", boardRequestDTO);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ServerException("User not found", ErrorList.USER_NOT_FOUND));

        Board board = Board.builder()
                .name(boardRequestDTO.getName())
                .creator(user)
                .content(boardRequestDTO.getContent())
                .expires(DateFormatter.format(boardRequestDTO.getExpires()))
                .created(new Date(System.currentTimeMillis()))
                .lastChange(new Date(System.currentTimeMillis()))
                .build();

        return boardDTOMapper.apply(boardRepository.save(board));
    }

    @Override
    @Transactional
    public BoardDTO getBoard(Long id) {
        log.debug("Get board {}", id);
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new ServerException("Board not found", ErrorList.BOARD_NOT_FOUND));

        if(board.getExpires().before(new Date(System.currentTimeMillis()))) {
            throw new ServerException("Board expired", ErrorList.BOARD_EXPIRED);
        }
        return boardDTOMapper.apply(board);
    }

    @Override
    @Transactional
    public BoardDTO updateBoard(Long userId, Long id, BoardRequestDTO boardRequestDTO) {
        log.debug("Update board {}", id);
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new ServerException("Board not found", ErrorList.BOARD_NOT_FOUND));

        if(board.getExpires().before(new Date(System.currentTimeMillis()))) {
            throw new ServerException("Board expired", ErrorList.BOARD_EXPIRED);
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ServerException("User not found", ErrorList.USER_NOT_FOUND));

        List<Integer> signedLines = LineComparer.findChangedLineRange(board.getContent(), boardRequestDTO.getContent());

        if (signedLines.size() == 2 && signedLines.getFirst() != -1) {
            Sign sign = Sign.builder()
                    .board(board)
                    .user(user)
                    .rowStart(signedLines.getFirst())
                    .rowEnd(signedLines.getLast())
                    .lastChange(new Date(System.currentTimeMillis()))
                    .build();
            signRepository.save(sign);
            board.getSigns().add(sign);
        }
        board.setContent(boardRequestDTO.getContent());
        board.setLastChange(new Date(System.currentTimeMillis()));
        return boardDTOMapper.apply(boardRepository.save(board));
    }
}
