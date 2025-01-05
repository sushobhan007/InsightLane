package com.blog.insight_lane.services.impl;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.CategoryDto;
import com.blog.insight_lane.repositories.CategoryRepository;
import com.blog.insight_lane.services.CategoryService;
import com.blog.insight_lane.utils.ModelMapperUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category savedCategory = this.categoryRepository.save(ModelMapperUtility.toCategory(categoryDto));

        CategoryDto savedCategoryDto = ModelMapperUtility.toCategoryDto(savedCategory);
        System.out.println(String.format("Category named: %s is saved successfully", savedCategoryDto.getCategoryName()));

        return savedCategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id ", categoryId));

        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepository.save(category);

        System.out.println("Category details are updated for the categoryId: %s" + categoryId);
        return ModelMapperUtility.toCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id ", categoryId));
        return ModelMapperUtility.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = this.categoryRepository.findAll();
        return categoryList.stream().map(category -> ModelMapperUtility.toCategoryDto(category)).toList();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id ", categoryId));
        this.categoryRepository.delete(category);
        System.out.println("Category is deleted for the categoryId: %s" + categoryId);
    }
}
