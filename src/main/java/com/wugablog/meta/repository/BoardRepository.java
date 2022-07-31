package com.wugablog.meta.repository;

import com.wugablog.meta.model.Board;
import com.wugablog.meta.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
