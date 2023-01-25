package com.example.podrida.repository;

import com.example.podrida.entity.Hand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHandRepository extends JpaRepository<Hand, Long> {
}
