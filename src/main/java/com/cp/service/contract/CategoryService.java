package com.cp.service.contract;

import com.cp.model.Category;

import java.util.List;

/**
 * CategoryService.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
public interface CategoryService {

    List<Category> getAllCategory(boolean productList, boolean attributeList);

    Category getCategoryById(int categoryId, boolean productList, boolean attributeList);

    boolean addCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(int categoryId);
}
