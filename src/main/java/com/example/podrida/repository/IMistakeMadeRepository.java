package com.example.podrida.repository;

import com.example.podrida.entity.MistakesMade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMistakeMadeRepository extends JpaRepository<MistakesMade, Long> {}
