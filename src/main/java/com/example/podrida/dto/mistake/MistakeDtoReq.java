package com.example.podrida.dto.mistake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MistakeDtoReq {
    private Long id = 0L;
    private String mistake;
    private int points;
}
