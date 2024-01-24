package com.ll.tourdemonde;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller

public class HomeController {
    @GetMapping("/")
    @ResponseBody
    String showMain(){
        System.out.println("메인페이지");
        return "메인페이지입니다.";


    }
    @GetMapping("/map")
//    @ResponseBody
    String showMap(){
        return "map";
    }

}
