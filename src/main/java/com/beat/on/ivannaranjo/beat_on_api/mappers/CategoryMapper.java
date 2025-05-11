package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setColor(category.getColor());
        dto.setIconUrl(category.getIconUrl());

        return dto;
    }

    public Category toEntity(CategoryDTO dto){
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setColor(dto.getColor());
        category.setIconUrl(dto.getIconUrl());

        return category;
    }

    public Category toEntity(CategoryCreateDTO dto){
        Category category = new Category();
        category.setName(dto.getName());
        category.setColor(dto.getColor());

        return category;
    }
}
