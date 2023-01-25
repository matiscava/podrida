package com.example.podrida.mapper;

import com.example.podrida.dto.mistakesMade.MistakesMadeDtoPlayer;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoReq;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoRes;
import com.example.podrida.entity.MistakesMade;

public class MistakesMadeMapper {
    public static MistakesMadeDtoRes convertEntityToDtoRes(MistakesMade m){
        MistakesMadeDtoRes mDto = new MistakesMadeDtoRes();
        mDto.setId(m.getId());
        mDto.setMistakeId(m.getMistake().getId());
        mDto.setMistakeName(m.getMistake().getMistake());
        mDto.setPlayerName(m.getPlayer().getName());
        mDto.setPlayerId(m.getPlayer().getId());
        return mDto;
    }

    public static MistakesMade convertDtoReqToEntity(MistakesMadeDtoReq mDto){
        MistakesMade mistakesMade = new MistakesMade();
        mistakesMade.setId(mDto.getMistakeId());
        mistakesMade.setHandNumber(mDto.getHandNumber());
        return mistakesMade;
    }

    public static MistakesMadeDtoPlayer convertEntityToMistakesMadeDtoPlayer (MistakesMade made){
        MistakesMadeDtoPlayer mDto = new MistakesMadeDtoPlayer();
        mDto.setMistake(made.getMistake().getMistake());
        mDto.setId(made.getId());
        mDto.setPoints(made.getMistake().getPoints());
        mDto.setHandNumber(made.getHandNumber());
        return mDto;
    }
}
