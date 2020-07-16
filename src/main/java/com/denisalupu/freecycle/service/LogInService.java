package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.model.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogInService {
    private final UserService userService;

    //logIn -> verify if username and password match data base
//    public boolean logIn(String userName, String password) {
//       UserDTO userDTO = userService.findByUserName(userName);
//        return userDTO.getPassword().equals(password);
//    }

}
