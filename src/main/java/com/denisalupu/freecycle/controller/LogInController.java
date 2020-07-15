package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.service.LogInService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
@AllArgsConstructor
public class LogInController {
   private final LogInService logInService;

    @PostMapping("/logIn")
    public boolean logIn(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return logInService.logIn(userName, password);
    }

}
