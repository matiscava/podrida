package com.example.podrida.repository;

import com.example.podrida.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameRepository extends JpaRepository<Game,Long> {
}
