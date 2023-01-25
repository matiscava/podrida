package com.example.podrida.controller;

import com.example.podrida.dto.mistake.MistakeDtoReq;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoReq;
import com.example.podrida.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mistake")
public class MistakeController {
    private final IMistakeService mistakeService;
    private final IMistakesMadeService mistakesMadeService;

    private MistakeController(MistakeService mistakeService, MistakesMadeService mistakesMadeService){
        this.mistakeService = mistakeService;
        this.mistakesMadeService = mistakesMadeService;
    }

    @GetMapping("/create")
    public ModelAndView mistakeForm(
            @RequestParam(defaultValue = "", required = false) Long id
    ) {
        ModelAndView modelAndView = new ModelAndView("mistakeForm");
        if (id != null){
            modelAndView.addObject("mistakeDto", mistakeService.getById(id));
        } else {
            modelAndView.addObject("mistakeDto", new MistakeDtoReq());
        }
        return modelAndView;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("mistakeList");
        modelAndView.addObject("mistakeList", mistakeService.getAll());
        return modelAndView;
    }

    @PostMapping("/player")
    public ModelAndView setPlayer(@ModelAttribute MistakesMadeDtoReq mDto){
        mistakesMadeService.create(mDto);
        return new ModelAndView( "redirect:/game/"+mDto.getGameId());

    }

    @PostMapping("/new")
    public ModelAndView create(@ModelAttribute MistakeDtoReq mDto){
        mistakeService.create(mDto);
        return new ModelAndView("redirect:/mistake/getAll");
    }


}
