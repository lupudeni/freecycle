package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.service.UserService;
import com.denisalupu.freecycle.utils.SortOrder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

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

    //TODO: this is not needed
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

    //TODO:this is not needed
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllOrderedById(@RequestParam("orderedById") SortOrder orderedById) {
        return userService.getAllOrderedById(orderedById);
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserDTO> getAllOrderedByDonatedObjects(@RequestParam("orderedByDonations") SortOrder orderedByDonations) {
//        return userService.getAllOrderedByDonatedObjects(orderedByDonations);
//    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam("id") long id) {
        userService.deleteById(id);
    }


}
