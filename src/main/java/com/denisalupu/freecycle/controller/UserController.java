package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.RegistrationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public UserDTO registerUserAccount(@RequestBody @Valid RegistrationDTO registrationDTO) {
        return userService.registerNewUser(registrationDTO);
    }

    //ar trebui de fapt sa fie sign up -> sa aiba o metoda in service
    //care verifica validitatea datelor din userDTO si dupa aia incearca sa faca o authentificare
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    //TODO: update/change password thing


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable("id") long id) {
        return userService.findById(id);
    }

//    @GetMapping("/")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO findByUserName(@RequestParam("userName") String userName) {
//        return userService.findByUserName(userName);
//    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam("id") long id) {
        userService.deleteById(id);
    }

}
