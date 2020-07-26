package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.RegistrationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.service.UserService;
import com.denisalupu.freecycle.util.AuthenticationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUserAccount(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return userService.registerNewUser(registrationDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody UserDTO userDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return userService.update(userDTO, userDetails);
    }

    //TODO check ownership
    // TODO: update/change password thing

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDTO showUserDetails(@RequestParam ("userName") String userName) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return userService.findUserByUserName(userName, userDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        userService.deleteById(id, userDetails);
    }

}
