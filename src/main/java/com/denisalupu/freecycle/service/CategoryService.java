package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.model.CategoryDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public CategoryEntity geEntityById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not a valid category"));

    }

    public List<CategoryDTO> getAllCategories() {
        return mapper.mapCollectionToList(categoryRepository.findAll(), CategoryDTO.class);
    }


}
