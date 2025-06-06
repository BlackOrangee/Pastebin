package com.pastebin.pastebin.repository;

import com.pastebin.pastebin.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository
        extends JpaRepository<Board, Long> {
}
