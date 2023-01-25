package com.example.podrida.dto.mistake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MistakeDtoRes {
    private Long id;
    private String mistake;
    private int points;
}
