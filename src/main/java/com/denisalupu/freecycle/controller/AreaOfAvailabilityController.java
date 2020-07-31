package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.service.AreaOfAvailabilityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("areas")
@SecurityRequirement(name = "http_basic")
@AllArgsConstructor
public class AreaOfAvailabilityController {

    private final AreaOfAvailabilityService areaService;

    @GetMapping("/allAreas")
    @ResponseStatus(HttpStatus.OK)
    public List<AreaOfAvailabilityDTO> getAllAreas() {
        return areaService.getAllAreas();
    }
}
