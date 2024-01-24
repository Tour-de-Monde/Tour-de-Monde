package com.ll.tourdemonde;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
public class AddressController {
    @GetMapping("/address")
    @ResponseBody
    public String address(){
        System.out.println("카카오 테스트");
        return  "address";
    }

}
