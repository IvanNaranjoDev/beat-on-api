package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Category;
import com.beat.on.ivannaranjo.beat_on_api.mappers.CategoryMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private  FileStorageService fileStorageService;

    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return  categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::toDTO);
    }

    public CategoryDTO createCategory(CategoryCreateDTO createDTO) {
        String filename = null;
        if(createDTO.getIconUrl() != null && !createDTO.getIconUrl().isEmpty()) {
            filename = fileStorageService.saveFile(createDTO.getIconUrl());
            if(filename == null) {
                throw new RuntimeException("Error al guardar la imagen");
            }
        }

        Category category = categoryMapper.toEntity(createDTO);
        category.setIconUrl(filename);

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, CategoryCreateDTO updateDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe"));

        // Procesar la imagen si se proporcionó
        String fileName = existingCategory.getIconUrl();
        if (updateDTO.getIconUrl() != null && !updateDTO.getIconUrl().isEmpty()) {
            fileName = fileStorageService.saveFile(updateDTO.getIconUrl());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la nueva imagen.");
            }
        }

        existingCategory.setIconUrl(fileName);

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe"));

        if(category.getIconUrl() != null && !category.getIconUrl().isEmpty()){
            fileStorageService.deleteFile(category.getIconUrl());
        }

        categoryRepository.deleteById(id);
    }
}
