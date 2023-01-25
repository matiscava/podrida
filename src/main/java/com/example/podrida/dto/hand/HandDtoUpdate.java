package com.example.podrida.dto.hand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class HandDtoUpdate {
    private Long id;
    private int predict;
    private int take    ;
    private boolean setPoints = false;

    @Override
    public String toString() {
        return "HandDtoUpdate{" +
                "id=" + id +
                ", predict=" + predict +
                ", taken=" + take +
                ", setPoints=" + setPoints +
                '}';
    }
}
