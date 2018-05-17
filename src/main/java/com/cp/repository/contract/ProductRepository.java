package com.cp.repository.contract;

import com.cp.model.Product;

import java.util.List;

/**
 * ProductRepository.java
 * `
 *
 * @author ManhTung
 * @version 3/4/18.
 */
public interface ProductRepository {

    List<Product> getAllProduct(boolean category, boolean attributeList);

    Product getProductById(int productId, boolean category, boolean attributeList);

    List<Product> getProductByCategoryId(int categoryId, boolean category, boolean attributeList);

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    boolean deleteProductByCategoryId(int categoryId);
}
