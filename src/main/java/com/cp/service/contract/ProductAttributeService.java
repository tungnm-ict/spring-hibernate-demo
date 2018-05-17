package com.cp.service.contract;

import com.cp.model.ProductAttribute;

import java.util.List;

/**
 * ProductAttributeService.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
public interface ProductAttributeService {
    List<ProductAttribute> getAllProductAttribute(boolean product, boolean attribute);

    ProductAttribute getProductAttributeById(int productAttributeId, boolean product, boolean attribute);

    List<ProductAttribute> getProductAttributeByProductId(int productId, boolean product, boolean attribute);

    boolean addProductAttribute(ProductAttribute productAttribute);

    boolean updateProductAttribute(ProductAttribute productAttribute);

    boolean deleteProductAttribute(int productAttributeId);

    boolean deleteProductAttributeByProductId(int productId);
}
