package com.example.podrida.dto.player;

import com.example.podrida.dto.hand.HandDtoRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoGamePoints {
    private Long id;
    private String name;
    private List<HandDtoRes> handDtoList = new ArrayList<>();
    private int mistakePoints=0;
    private int totalPoints;

    @Override
    public String toString() {
        return "PlayerDtoGamePoints{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", handDtoList=" + handDtoList +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
