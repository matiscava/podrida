package com.example.podrida.controller;

import com.example.podrida.dto.player.PlayerDtoChangeName;
import com.example.podrida.dto.player.PlayerDtoReq;
import com.example.podrida.service.IPlayerService;
import com.example.podrida.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/player")
public class PlayerController {
    private final IPlayerService playerService;

    public PlayerController( PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping("/edit-name/{id}")
    public ModelAndView getEditName (@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("playerEditName");
        modelAndView.addObject("playerDto", playerService.getById(id));
        modelAndView.addObject("player", new PlayerDtoChangeName());
        return modelAndView;
    }

    @PostMapping("/edit-name")
    public ModelAndView editName (@ModelAttribute PlayerDtoChangeName pDto){
        System.out.println(pDto );
        Long gameId = playerService.changeName(pDto);
        return new ModelAndView("redirect:/game/"+gameId+"/points");
    }

    @PostMapping("/new")
    public ModelAndView create (@ModelAttribute PlayerDtoReq u){
        playerService.create(u);
        int number = u.getNumber()+1;
        return new ModelAndView("redirect:/game/"+u.getGameId()+"/addPlayer?number="+number);
    }


}
