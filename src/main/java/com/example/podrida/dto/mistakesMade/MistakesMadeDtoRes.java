package com.example.podrida.dto.mistakesMade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MistakesMadeDtoRes {
    private Long id;
    private Long playerId;
    private String playerName;
    private Long mistakeId;
    private String mistakeName;
    private int points;
    private int handNumber;
}
