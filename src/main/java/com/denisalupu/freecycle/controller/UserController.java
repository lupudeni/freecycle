package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.RegistrationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.service.UserService;
import com.denisalupu.freecycle.util.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@SecurityRequirement(name = "http_basic")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered a new user"),
            @ApiResponse(responseCode = "400", description = "Invalid body passed for registering new user"),
    })
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUserAccount(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return userService.registerNewUser(registrationDTO);
    }

    @Operation(summary = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a user"),
            @ApiResponse(responseCode = "400", description = "Invalid body passed for registering new user"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody UserDTO userDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return userService.update(userDTO, userDetails);
    }

    //TODO check ownership
    // TODO: update/change password thing


    @Operation(summary = "Shows user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retried user details"),
            @ApiResponse(responseCode = "400", description = "Invalid param passed for showing user details"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDTO showUserDetails(@RequestParam ("userName") String userName) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return userService.findUserByUserName(userName, userDetails);
    }

    @Operation(summary = "Deletes a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "203", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "400", description = "Invalid path passed for deleting user"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        userService.deleteById(id, userDetails);
    }

}
