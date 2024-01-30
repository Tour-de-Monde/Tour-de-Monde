package com.ll.tourdemonde.home.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {

    @GetMapping("/")
    public String Home() {
        return "redirect:/post/list";

    }

    @GetMapping("/map")
//    @ResponseBody
    String showMap(){
        return "map";
    }
    @GetMapping("/search")
//    @ResponseBody
    String showSearch(){
        return "search";
    }

}
