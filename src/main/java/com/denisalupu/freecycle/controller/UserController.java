package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findByUserName(@RequestParam("userName") String userName) {
        return userService.findByUserName(userName);
    }

    //TODO -> getAllUsersByIdAsc
    //TODO -> getAllUsersByIdDesc

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return null;
    }


}
