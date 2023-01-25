package com.example.podrida.service;

import com.example.podrida.dto.game.GameDtoRes;
import com.example.podrida.dto.hand.HandDtoReq;
import com.example.podrida.dto.hand.HandDtoRes;
import com.example.podrida.dto.SuccessfullyMessageDto;
import com.example.podrida.dto.hand.HandDtoUpdate;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.entity.Game;
import com.example.podrida.entity.Hand;
import com.example.podrida.entity.Player;
import com.example.podrida.exception.IdNotFoundException;
import com.example.podrida.exception.InvalidValueException;
import com.example.podrida.mapper.GameMapper;
import com.example.podrida.mapper.HandMapper;
import com.example.podrida.repository.IGameRepository;
import com.example.podrida.repository.IHandRepository;
import com.example.podrida.repository.IPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HandService implements IHandService {
    private final IHandRepository handRepository;
    private final IPlayerRepository playerRepository;
    private final IGameRepository gameRepository;
    public HandService(
            IHandRepository handRepository,
            IPlayerRepository playerRepository,
            IGameRepository gameRepository
    ){
        this.handRepository = handRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }
    @Override
    public List<HandDtoRes> getAll() {
        List<Hand> playerHandList = handRepository.findAll();
        List<HandDtoRes> handDtoList = new ArrayList<>();
        playerHandList.forEach(
                h -> handDtoList.add(HandMapper.convertEntityToResDto(h))
        );
        return handDtoList;
    }

    @Override
    public HandDtoRes create(HandDtoReq handDto) {
        Hand h = HandMapper.convertReqDtoToEntity(handDto);
        Optional<Player> p = playerRepository.findById(handDto.getPlayerId());
        if (p.isEmpty()) throw new IdNotFoundException("No existe el Jugador con ID "+handDto.getPlayerId()+", modifique los datos.");
        if (p.get().getPlayerHands().size() == 21) throw new InvalidValueException("No se pueden agregar más manos a este usuario.");
        Optional<Game> g = gameRepository.findById(handDto.getGameId());
        if (g.isEmpty()) throw new IdNotFoundException("No existe el Juego con ID "+handDto.getGameId()+", modifique los datos.");
        h.setPlayer(p.get());
        h.setHandNumber(g.get().getHandNumber());
        Hand hSaved = handRepository.save(h);
        int nextPlayer = g.get().getNextPlayer()+1;
        if (nextPlayer >=7) nextPlayer =0;
        g.get().setNextPlayer(nextPlayer);
        gameRepository.save(g.get());
        return HandMapper.convertEntityToResDto(hSaved);
    }

    @Override
    public HandDtoRes getById(Long id) {
        Optional<Hand> h = handRepository.findById(id);
        if (h.isEmpty()) throw new IdNotFoundException("La Mano con el ID: "+id+" no existe, verifique sus datos");
        return HandMapper.convertEntityToResDto(h.get());
    }

    @Override
    public HandDtoRes update(HandDtoReq hDto) {
        return null;
    }
    @Override
    public Long updateHand(HandDtoUpdate hDto){
        System.out.println("hDto = " + hDto);
        Optional<Hand> h = handRepository.findById(hDto.getId());
        if (h.isEmpty()) throw new IdNotFoundException("La Mano con el ID: "+hDto.getId()+" no existe, verifique sus datos");
        h.get().setPredict(hDto.getPredict());
        h.get().setTake(hDto.getTake());
        if (hDto.isSetPoints()){
            int points;
            if(hDto.getTake() == hDto.getPredict()) {
                points = 10 + (hDto.getPredict()*3);
            }else {
                int number = hDto.getPredict()<hDto.getTake() ? hDto.getTake()-hDto.getPredict() : hDto.getPredict()-hDto.getTake();
                points = number * -3;
            }
            h.get().setPoints(points);
        }
        Hand hSaved = handRepository.save(h.get());
        return hSaved.getPlayer().getGame().getId();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Hand> h = handRepository.findById(id);
        if(h.isEmpty()) throw new IdNotFoundException("No existe la mano con el id "+id+", verigique sus datos");
        handRepository.deleteById(id);
    }

    @Override
    public GameDtoRes setTakeCard(HandSetTakeCardsDto handDto) {
        Optional<Hand> h= handRepository.findById(handDto.getId());
        if (h.isEmpty()) throw new IdNotFoundException("No existe la mano con ID "+handDto.getId()+", modifique los datos.");
        Optional<Game> g = gameRepository.findById(handDto.getGameId());
        if (g.isEmpty()) throw new IdNotFoundException("No existe el Juego con ID "+handDto.getGameId()+", modifique los datos.");
        h.get().setTake(handDto.getTake());
        handRepository.save(h.get());
        int nextPlayer = g.get().getNextPlayer()+1;
        if (nextPlayer >=7) {
            nextPlayer = 0;
            g.get().setHandNumber(g.get().getHandNumber()+1);
            List<Player> playerList = g.get().getPlayerList().stream().toList();
            playerList.forEach(
                    p ->{
                        int order = p.getPlayerOrder();
                        if (order==0) {
                            p.setPlayerOrder(6);
                        } else{
                            p.setPlayerOrder(order-1);
                        }
                        playerRepository.save(p);
                    }
            );
        }
        g.get().setNextPlayer(nextPlayer);
        Game game = gameRepository.save(g.get());
        return GameMapper.convertEntityToDtoRes(game);
    }

    @Override
    public void getPoints(Long gameId) {
        Optional<Game> g = gameRepository.findById(gameId);
        if (g.isEmpty()) throw new IdNotFoundException("No existe el Juego con ID "+gameId+", modifique los datos.");
        List<Player> playerList = g.get().getPlayerList().stream().toList();
        playerList.forEach(
                p -> p.getPlayerHands().forEach(
                            h->{
                                int points;
                                if(h.getTake() == h.getPredict()) {
                                    points = 10 + (h.getPredict()*3);
                                }else {
                                    int number = h.getPredict()<h.getTake() ? h.getTake()-h.getPredict() : h.getPredict()-h.getTake();
                                    points = number * -3;
                                }
                                h.setPoints(points);
                                handRepository.save(h);
                            }
                    )
        );
    }

    @Override
    public Long getHandID(int handNumber, Set<HandDtoRes> handDtoList) {
        AtomicReference<Long> id = new AtomicReference<>(0L);
        handDtoList.forEach( h->{
            if (h.getHandNumber() == handNumber){
                id.set(h.getId());
            }
        });
       return id.get();
    }

    @Override
    public int getPredicted(GameDtoRes gameDtoRes) {
        Optional<Game> g = gameRepository.findById(gameDtoRes.getId());
        if (g.isEmpty()) throw new IdNotFoundException("No existe el Juego con ID "+gameDtoRes.getId()+", modifique los datos.");
        Set<Hand> handSet = new HashSet<>();
        int handNumber = gameDtoRes.getHandNumber();
        if (gameDtoRes.getViewName().equals("endTaken")) --handNumber;
        int finalHandNumber1 = handNumber;
        g.get().getPlayerList().forEach(
                p-> p.getPlayerHands().forEach(hand -> {
                    if (hand.getHandNumber() == finalHandNumber1) handSet.add(hand);
                } )
        );
        AtomicInteger predicted = new AtomicInteger();
        if (handSet.size() == 7){
            handSet.forEach(h -> predicted.addAndGet(h.getPredict()) );
        }
        return predicted.intValue();
    }

    @Override
    public int getTaken(GameDtoRes gameDtoRes) {
        Optional<Game> g = gameRepository.findById(gameDtoRes.getId());
        if (g.isEmpty()) throw new IdNotFoundException("No existe el Juego con ID "+gameDtoRes.getId()+", modifique los datos.");
        Set<Hand> handSet = new HashSet<>();
        int handNumber = gameDtoRes.getHandNumber();
        if (gameDtoRes.getViewName().equals("endTaken")) --handNumber;
        int finalHandNumber1 = handNumber;
        g.get().getPlayerList().forEach(
                p-> p.getPlayerHands().forEach(hand -> {
                    if (hand.getHandNumber() == finalHandNumber1) handSet.add(hand);
                } )
        );
        AtomicInteger taken = new AtomicInteger();
        if (handSet.size() == 7){
            handSet.forEach(h -> taken.addAndGet(h.getTake()) );
        }
        return taken.intValue();
    }
}
