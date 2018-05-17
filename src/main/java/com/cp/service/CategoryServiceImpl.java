package com.cp.service;

import com.cp.model.Category;
import com.cp.repository.contract.CategoryRepository;
import com.cp.service.contract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryServiceImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory(boolean productList, boolean attributeList) {
        return this.categoryRepository.getAllCategory(productList, attributeList);
    }

    public Category getCategoryById(int categoryId, boolean productList, boolean attributeList) {
        return this.categoryRepository.getCategoryById(categoryId, productList, attributeList);
    }

    public boolean addCategory(Category category) {
        return this.categoryRepository.addCategory(category);
    }

    public boolean updateCategory(Category category) {
        return this.categoryRepository.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) {
        return this.categoryRepository.deleteCategory(categoryId);
    }
}
