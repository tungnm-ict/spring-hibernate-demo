package com.cp.service;

import com.cp.model.Product;
import com.cp.repository.contract.ProductRepository;
import com.cp.service.contract.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductServiceImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(boolean category, boolean attributeList) {
        return this.productRepository.getAllProduct(category, attributeList);
    }

    public Product getProductById(int productId, boolean category, boolean attributeList) {
        return this.productRepository.getProductById(productId, category, attributeList);
    }

    public List<Product> getProductByCategoryId(int categoryId, boolean category, boolean attributeList) {
        return this.productRepository.getProductByCategoryId(categoryId, category, attributeList);
    }

    public boolean addProduct(Product product) {
        return this.productRepository.addProduct(product);
    }

    public boolean updateProduct(Product product) {
        return this.productRepository.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return this.productRepository.deleteProduct(productId);
    }

    public boolean deleteProductByCategoryId(int categoryId) {
        return this.productRepository.deleteProduct(categoryId);
    }
}
