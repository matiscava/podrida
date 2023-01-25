package com.example.podrida.dto.player;

import com.example.podrida.dto.mistakesMade.MistakesMadeDtoPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerDtoGetMistake {
    private Long id;
    private String name;
    private Set<MistakesMadeDtoPlayer> mistakesDtoList = new HashSet<>();
    private Long gameId;
}
