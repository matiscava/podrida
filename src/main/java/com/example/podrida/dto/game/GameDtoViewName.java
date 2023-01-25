package com.example.podrida.dto.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GameDtoViewName {
    private Long id;
    private String viewName;

    @Override
    public String toString() {
        return "GameDtoViewName{" +
                "id=" + id +
                ", viewName='" + viewName + '\'' +
                '}';
    }
}
