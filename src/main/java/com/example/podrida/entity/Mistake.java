package com.example.podrida.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Mistake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mistake;
    private int points;
    @OneToMany(mappedBy = "mistake",fetch = FetchType.EAGER)
    private List<MistakesMade> mistakesMade = new ArrayList<>();
}
