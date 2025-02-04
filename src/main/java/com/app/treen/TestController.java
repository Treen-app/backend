package com.app.treen;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/check")
    public String checkServerStatus() {
        return "check";
    }

    @GetMapping("/login")
    public String loginLoad() {return "login";}
}
