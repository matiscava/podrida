package com.example.podrida.mapper;

import com.example.podrida.dto.mistake.MistakeDtoReq;
import com.example.podrida.dto.mistake.MistakeDtoRes;
import com.example.podrida.entity.Mistake;

public class MistakeMapper {
    public static Mistake convertReqDtoToEntity(MistakeDtoReq mistakeDto){
        Mistake m = new Mistake();
        m.setMistake(mistakeDto.getMistake());
        m.setPoints(mistakeDto.getPoints());
        m.setId(mistakeDto.getId());
        return m;
    }

    public static MistakeDtoRes convertEntityToResDto(Mistake m){
        MistakeDtoRes mistakeDto = new MistakeDtoRes();
        mistakeDto.setPoints(m.getPoints());
        mistakeDto.setMistake(m.getMistake());
        mistakeDto.setId(m.getId());
        return mistakeDto;
    }
}
