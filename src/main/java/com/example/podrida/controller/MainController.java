package com.example.podrida.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping({"","/"})
    public ModelAndView getHome(){
        return new ModelAndView("home");
    }
    @GetMapping("/rules")
    public ModelAndView getRules() {return  new ModelAndView("rules"); }
}
