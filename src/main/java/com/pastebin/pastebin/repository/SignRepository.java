package com.pastebin.pastebin.repository;

import com.pastebin.pastebin.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignRepository
        extends JpaRepository<Sign, Long> {
}
