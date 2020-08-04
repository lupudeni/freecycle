package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.service.AreaOfAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retrieves the list of available areas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved the area list")
    })
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<AreaOfAvailabilityDTO> getAllAreas() {
        return areaService.getAllAreas();
    }
}
