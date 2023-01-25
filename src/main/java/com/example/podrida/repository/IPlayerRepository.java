package com.example.podrida.repository;

import com.example.podrida.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerRepository extends JpaRepository<Player,Long> {
}
