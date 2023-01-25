package com.example.podrida.repository;

import com.example.podrida.entity.Mistake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMistakeRepository extends JpaRepository<Mistake,Long> {
}
