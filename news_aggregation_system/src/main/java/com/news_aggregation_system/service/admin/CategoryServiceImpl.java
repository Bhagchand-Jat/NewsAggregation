package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.CategoryMapper;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", "id: " + id));
        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toDto)
                .toList();
    }


    private Category getOrCreateCategory(String name) {
        return categoryRepository.findByNameIgnoreCase(name.trim())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(name.trim());
                    return categoryRepository.save(newCategory);
                });
    }

    @Override
    public Set<Category> getOrCreateCategories(Set<String> names) {
        Set<Category> categories = new HashSet<>();
        for (String name : names) {
            Category category = getOrCreateCategory(name);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new AlreadyExistsException("Category", "name: " + dto.getName());
        }
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDto(categoryRepository.save(category));
    }


    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {

        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public CategoryDTO getByCategoryName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Category", "name: " + name));
        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> getEnabledCategories() {
        return categoryRepository.findByEnabledTrue().stream().map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    public void updateCategoryStatus(Long categoryId, boolean isEnabled) {
        int updated = categoryRepository.updateEnabledByCategoryId(isEnabled, categoryId);
        if (updated < 1) {
            throw new NotFoundException("Category", "id: " + categoryId);
        }
    }


}
