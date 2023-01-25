package com.example.podrida.mapper;

import com.example.podrida.dto.hand.HandDtoRes;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoPlayer;
import com.example.podrida.dto.player.*;
import com.example.podrida.entity.Game;
import com.example.podrida.entity.Hand;
import com.example.podrida.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerMapper {
    public static Player convertReqDtoToEntity(PlayerDtoReq userDto, Game g){
        Player p = new Player();
        p.setId(userDto.getId());
        p.setName(userDto.getName());
        p.setGame(g);
        return p;
    }

    public static PlayerDtoRes convertEntityToResDto(Player p){
        PlayerDtoRes userDto = new PlayerDtoRes();
        userDto.setGameId(p.getGame().getId());
        userDto.setName(p.getName());
        userDto.setId(p.getId());
        Set<HandDtoRes> handSet = new HashSet<>();
        p.getPlayerHands().forEach(
                h -> handSet.add(HandMapper.convertEntityToResDto(h))
        );
        userDto.setHands(handSet);
        userDto.setOrder(p.getPlayerOrder());
        return userDto;
    }

    public static PlayerGetPredictDto convertEntityToGetHandDto(Player p){
        PlayerGetPredictDto playerDto = new PlayerGetPredictDto();
        playerDto.setUserId(p.getId());
        playerDto.setName(p.getName());
        playerDto.setPlayerOrder(p.getPlayerOrder());
        List<Hand> handList = p.getPlayerHands().stream().toList();
        handList.forEach(
                h -> {
                    if (h.getHandNumber() == p.getGame().getHandNumber()) {
                        playerDto.setPredict(h.getPredict());
                        playerDto.setHandId(h.getId());
                    }
                }
        );
        return playerDto;
    }

    public static HandSetTakeCardsDto convertEntityToHandGetCardDto(Player p){
        HandSetTakeCardsDto handDto = new HandSetTakeCardsDto();
        List<Hand> handList = p.getPlayerHands().stream().toList();
        handList.forEach(
                h -> {
                    if(h.getHandNumber() == p.getGame().getHandNumber()){
                        handDto.setId(h.getId());
                        handDto.setTake(h.getTake());
                        handDto.setPredict(h.getPredict());
                    }
                }
        );
        handDto.setPlayerId(p.getId());
        handDto.setPlayerName(p.getName());
        handDto.setGameId(p.getGame().getId());
        return handDto;
    }

    public static PlayerDtoGamePoints convertEntityToPlayerGamePoints(Player p){
        PlayerDtoGamePoints playerDto = new PlayerDtoGamePoints();
        playerDto.setId(p.getId());
        playerDto.setName(p.getName());
        AtomicInteger totalPoints = new AtomicInteger();
        List<HandDtoRes> handDtoList = new ArrayList<>();
        List<Hand> handList = p.getPlayerHands().stream().toList();
        int points = 0;
        for (Hand hand : handList) {
            HandDtoRes hDto = new HandDtoRes();
            totalPoints.addAndGet(hand.getPoints());
            hDto.setPredict(hand.getPredict());
            hDto.setTake(hand.getTake());
            hDto.setHandNumber(hand.getHandNumber());
            hDto.setId(hand.getId());
            hDto.setPlayerId(p.getId());
            points += hand.getPoints();
            hDto.setPoints(points);
            handDtoList.add(hDto);
        }
        AtomicInteger mistakePoints= new AtomicInteger();
        p.getMistakesMadeList().forEach(
                m -> {
                    totalPoints.addAndGet(-m.getMistake().getPoints());
                    mistakePoints.addAndGet(m.getMistake().getPoints());
                }
        );
        playerDto.setMistakePoints(mistakePoints.intValue());
        playerDto.setTotalPoints(totalPoints.intValue());
        playerDto.setHandDtoList(handDtoList);
        return playerDto;
    }

    public static PlayerGetPointsDto convertEntityToPlayerDtoPointDto (Player p, int handNumber) {
        PlayerGetPointsDto playerDto = new PlayerGetPointsDto();
        playerDto.setPlayerId(p.getId());
        playerDto.setName(p.getName());
        playerDto.setPlayerOrder(p.getPlayerOrder() == 6 ? 0 : p.getPlayerOrder()+1);
        List<Hand> handList = p.getPlayerHands().stream().toList();
        if(handList.size()==handNumber){
            handList.forEach(
                    h -> {
                        if (h.getHandNumber() == handNumber){
                            playerDto.setHandId(h.getId());
                            playerDto.setTake(h.getTake());
                            playerDto.setPoints(h.getPoints());
                            playerDto.setPredict(h.getPredict());
                        }
                    }
            );
        }
        return playerDto;
    }

    public static PlayerDtoGetMistake convertEntityToPlayerDtoGetMistake (Player p){
        PlayerDtoGetMistake playerDto = new PlayerDtoGetMistake();
        playerDto.setId(p.getId());
        playerDto.setName(p.getName());
        Set<MistakesMadeDtoPlayer> mistakesDtoList = new HashSet<>();
        p.getMistakesMadeList().forEach(
                m-> mistakesDtoList.add(MistakesMadeMapper.convertEntityToMistakesMadeDtoPlayer(m))
        );
        mistakesDtoList.stream().sorted(Comparator.comparing(MistakesMadeDtoPlayer::getHandNumber));
        playerDto.setMistakesDtoList(mistakesDtoList);
        playerDto.setGameId(p.getGame().getId());
        return playerDto;
    }
}
