package com.example.podrida.mapper;

import com.example.podrida.dto.game.GameDtoPoints;
import com.example.podrida.dto.game.GameDtoReq;
import com.example.podrida.dto.game.GameDtoRes;
import com.example.podrida.dto.game.GameDtoViewName;
import com.example.podrida.dto.player.PlayerDtoGamePoints;
import com.example.podrida.dto.player.PlayerDtoRes;
import com.example.podrida.entity.Game;
import com.example.podrida.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameMapper {
    public static Game convertReqDtoToEntity (GameDtoReq gameDtoReq){
        Game g = new Game();
        g.setId(gameDtoReq.getId());
        return g;
    }
    public static GameDtoRes convertEntityToDtoRes(Game g){
        GameDtoRes gameDto = new GameDtoRes();
        List<PlayerDtoRes> playerDtoList = new ArrayList<>();
        gameDto.setId(g.getId());
        g.getPlayerList().forEach(
                p->playerDtoList.add(PlayerMapper.convertEntityToResDto(p))
        );
        gameDto.setPlayerList(playerDtoList);
        gameDto.setTimestamp(Utils.convertDateToLocalDate(g.getTimestamp()));
        gameDto.setHandNumber(g.getHandNumber());
        gameDto.setNextPlayer(g.getNextPlayer());
        gameDto.setViewName(g.getViewName());
        return gameDto;
    }

    public static GameDtoPoints convertEntityToDtoPoints(Game g){
        GameDtoPoints gameDto = new GameDtoPoints();
        gameDto.setId(g.getId());
        gameDto.setHandNumber(g.getHandNumber());
        List<PlayerDtoGamePoints> playerDtoList = new ArrayList<>();
        g.getPlayerList().forEach(
                p -> playerDtoList.add(PlayerMapper.convertEntityToPlayerGamePoints(p))
        );
        playerDtoList.sort(Comparator.comparing(PlayerDtoGamePoints::getId));
        gameDto.setPlayerList(playerDtoList);
        return gameDto;
    }

    public static GameDtoViewName convertEntityToGameDtoViewName (Game g){
        GameDtoViewName gameDto = new GameDtoViewName();
        gameDto.setId(g.getId());
        gameDto.setViewName(g.getViewName());
        System.out.println("gameDto = " + gameDto);
        return gameDto;
    }
}
