package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.service.AreaOfAvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("areas")
public class AreaOfAvailabilityController {

    private final AreaOfAvailabilityService areaService;

    @GetMapping("/allAreas")
    @ResponseStatus(HttpStatus.OK)
    public List<AreaOfAvailabilityDTO> getAllAreas() {
        return areaService.getAllAreas();
    }
}
