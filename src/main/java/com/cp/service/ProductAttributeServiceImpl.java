package com.cp.service;

import com.cp.model.ProductAttribute;
import com.cp.repository.contract.ProductAttributeRepository;
import com.cp.service.contract.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductAttributeServiceImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    public void setProductAttributeRepository(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    public List<ProductAttribute> getAllProductAttribute(boolean product, boolean attribute) {
        return this.productAttributeRepository.getAllProductAttribute(product, attribute);
    }

    public ProductAttribute getProductAttributeById(int productAttributeId, boolean product, boolean attribute) {
        return this.productAttributeRepository.getProductAttributeById(productAttributeId, product, attribute);
    }

    public List<ProductAttribute> getProductAttributeByProductId(int productId, boolean product, boolean attribute) {
        return this.productAttributeRepository.getProductAttributeByProductId(productId, product, attribute);
    }

    public boolean addProductAttribute(ProductAttribute productAttribute) {
        return this.productAttributeRepository.addProductAttribute(productAttribute);
    }

    public boolean updateProductAttribute(ProductAttribute productAttribute) {
        return this.productAttributeRepository.updateProductAttribute(productAttribute);
    }

    public boolean deleteProductAttribute(int productAttributeId) {
        return this.productAttributeRepository.deleteProductAttribute(productAttributeId);
    }

    public boolean deleteProductAttributeByProductId(int productId) {
        return this.productAttributeRepository.deleteProductAttributeByProductId(productId);
    }
}
