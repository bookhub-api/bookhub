package com.application.jetbill.service;

import com.application.jetbill.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
    Page<CategoryDTO> paginate(Pageable pageable);
    CategoryDTO findById(Integer id);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(Integer id, CategoryDTO updateCategoryDTO);
    void delete(Integer id);
}
