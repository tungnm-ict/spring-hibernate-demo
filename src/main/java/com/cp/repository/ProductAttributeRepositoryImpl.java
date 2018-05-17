package com.cp.repository;

import com.cp.model.ProductAttribute;
import com.cp.repository.contract.ProductAttributeRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductAttributeRepositoryImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Repository
@Transactional
public class ProductAttributeRepositoryImpl implements ProductAttributeRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ProductAttribute> getAllProductAttribute(boolean product, boolean attribute) {
        List<ProductAttribute> productAttributeList = sessionFactory.getCurrentSession().createQuery("FROM ProductAttribute").list();
        // LAZY LOADING
        if (product || attribute) {
            for (ProductAttribute productAttribute : productAttributeList) {
                if (product) productAttribute.getProduct().getProductId();
                if (attribute) productAttribute.getAttribute().getAttributeId();
            }
        }
        return productAttributeList;
    }

    public ProductAttribute getProductAttributeById(int productAttributeId, boolean product, boolean attribute) {
        ProductAttribute productAttribute = (ProductAttribute) sessionFactory.getCurrentSession().get(ProductAttribute.class, productAttributeId);
        // LAZY LOADING
        if (product) productAttribute.getProduct().getProductId();
        if (attribute) productAttribute.getAttribute().getAttributeId();
        return productAttribute;
    }

    public List<ProductAttribute> getProductAttributeByProductId(int productId, boolean product, boolean attribute) {
        List<ProductAttribute> productAttributeList = sessionFactory.getCurrentSession().createQuery("FROM ProductAttribute WHERE productId = " + productId).list();
        // LAZY LOADING
        if (product || attribute) {
            for (ProductAttribute productAttribute : productAttributeList) {
                if (attribute) productAttribute.getAttribute().getAttributeId();
                if (product) productAttribute.getProduct().getProductId();
            }
        }
        return productAttributeList;
    }

    public boolean addProductAttribute(ProductAttribute productAttribute) {
        return saveOrUpdate(productAttribute);
    }

    public boolean updateProductAttribute(ProductAttribute productAttribute) {
        return saveOrUpdate(productAttribute);
    }

    public boolean deleteProductAttribute(int productAttributeId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE ProductAttribute where productAttributeId = :productAttributeId");
            query.setParameter("productAttributeId", productAttributeId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteProductAttributeByProductId(int productId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE ProductAttribute where productId = :productId");
            query.setParameter("productId", productId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean saveOrUpdate(ProductAttribute productAttribute) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(productAttribute);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
