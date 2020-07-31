package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.CategoryDTO;
import com.denisalupu.freecycle.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("categories")
@SecurityRequirement(name = "http_basic")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/allCategories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
