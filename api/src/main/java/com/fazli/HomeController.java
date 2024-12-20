package com.fazli;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }




    @GetMapping("/personKontakt")
    public String weiterleitenZuPersonKontak(){
        return "redirect:/personKontakt";
    }



}