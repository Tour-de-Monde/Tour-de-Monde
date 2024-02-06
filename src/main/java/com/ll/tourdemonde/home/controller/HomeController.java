package com.ll.tourdemonde.home.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@Controller

public class HomeController {

    @GetMapping("/")
    public String Home() {
        return "redirect:/post/list";

    }


    @GetMapping("/session")
    @ResponseBody
    public Map<String, String> getSession(HttpSession session) {
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key).toString()
                        ));
    }
}
