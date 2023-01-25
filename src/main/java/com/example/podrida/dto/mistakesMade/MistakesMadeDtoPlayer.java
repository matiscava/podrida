package com.example.podrida.dto.mistakesMade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MistakesMadeDtoPlayer {
    private Long id;
    private String mistake;
    private int points;
    private int handNumber;
}
