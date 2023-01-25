package com.example.podrida.service;

import com.example.podrida.dto.SuccessfullyMessageDto;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoReq;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoRes;
import com.example.podrida.entity.*;
import com.example.podrida.exception.IdNotFoundException;
import com.example.podrida.mapper.MistakesMadeMapper;
import com.example.podrida.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MistakesMadeService implements IMistakesMadeService{
    private final IMistakeMadeRepository mistakeMadeRepository;
    private final IMistakeRepository mistakeRepository;
    private final IPlayerRepository playerRepository;
    private final IGameRepository gameRepository;
    private final IHandRepository handRepository;

    public MistakesMadeService(
            IMistakeMadeRepository mistakeMadeRepository,
            IMistakeRepository mistakeRepository,
            IPlayerRepository playerRepository,
            IGameRepository gameRepository,
            IHandRepository handRepository
    ){
        this.mistakeMadeRepository = mistakeMadeRepository;
        this.mistakeRepository = mistakeRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.handRepository = handRepository;
    }

    @Override
    public List<MistakesMadeDtoRes> getAll() {
        List<MistakesMade> mistakeMadeList = mistakeMadeRepository.findAll();
        List<MistakesMadeDtoRes> mistakesMadeDtoList = new ArrayList<>();
        mistakeMadeList.forEach(
                m -> mistakesMadeDtoList.add(MistakesMadeMapper.convertEntityToDtoRes(m))
        );
        return mistakesMadeDtoList;
    }

    @Override
    public MistakesMadeDtoRes create(MistakesMadeDtoReq mDto)
    {
        MistakesMade mistakesMade = MistakesMadeMapper.convertDtoReqToEntity(mDto);
        Optional<Player> p = playerRepository.findById(mDto.getPlayerId());
        if(p.isEmpty()) throw new IdNotFoundException("No se encontró el Jugador ID "+mDto.getPlayerId());
        Optional<Mistake> m = mistakeRepository.findById(mDto.getMistakeId());
        if(m.isEmpty()) throw new IdNotFoundException("No se encontró el Mistake ID "+mDto.getMistakeId());
        mistakesMade.setMistake(m.get());
        mistakesMade.setPlayer(p.get());
        MistakesMade mSaved = mistakeMadeRepository.save(mistakesMade);
        int handNumber = mDto.getHandNumber();
        if (mDto.isReplayHand()){
            Optional<Game> g = gameRepository.findById(mDto.getGameId());
            if(g.isEmpty()) throw new IdNotFoundException("No se encontró el Juego ID "+mDto.getMistakeId());
            g.get().setNextPlayer(0);
            if (g.get().getViewName().equals("endTaken")) {
                handNumber -= 1;
                g.get().setHandNumber(handNumber);
                g.get().getPlayerList().forEach(
                        player -> {
                            int order = player.getPlayerOrder();
                            if (order == 6) {
                                player.setPlayerOrder(0);
                            } else {
                                player.setPlayerOrder(order+1);
                            }
                            playerRepository.save(player);
                        }
                );
            }
            int finalHandNumber = handNumber;
            g.get().getPlayerList().forEach(
                    player -> {
                        List<Hand> handList = player.getPlayerHands().stream().toList();
                        handList.forEach(
                                hand -> {
                                    Hand handReal = hand;
                                    if (hand.getHandNumber() >= finalHandNumber){
                                        hand.setPredict(0);
                                        hand.setTake(0);
                                        hand.setPoints(0);
                                        handRepository.save(handReal);
                                    }
                                }
                        );
                    }
            );

            g.get().setViewName("predict");
            gameRepository.save(g.get());
        }
        return MistakesMadeMapper.convertEntityToDtoRes(mSaved);
    }

    @Override
    public MistakesMadeDtoRes getById(Long id) {
        Optional<MistakesMade> mistakesMade = mistakeMadeRepository.findById(id);
        if(mistakesMade.isEmpty()) throw new IdNotFoundException("No se encontró el Mistake ID "+id);
        return MistakesMadeMapper.convertEntityToDtoRes(mistakesMade.get());
    }

    @Override
    public MistakesMadeDtoRes update(MistakesMadeDtoReq object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<MistakesMade> mistakesMade = mistakeMadeRepository.findById(id);
        if(mistakesMade.isEmpty()) throw new IdNotFoundException("No se encontró el Mistake ID "+id);
        mistakeMadeRepository.deleteById(id);
    }

    @Override
    public Long getGameId(long id) {
        Optional<MistakesMade> m = mistakeMadeRepository.findById(id);
        if(m.isEmpty()) throw new IdNotFoundException("No se encontró el Mistake ID "+id);
        return m.get().getPlayer().getGame().getId();
    }
}
