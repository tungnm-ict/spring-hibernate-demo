package com.cp.repository;

import com.cp.model.Product;
import com.cp.model.ProductAttribute;
import com.cp.repository.contract.ProductRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductRepositoryImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Product> getAllProduct(boolean category, boolean attributeList) {
        List<Product> productList = sessionFactory.getCurrentSession().createQuery("FROM Product").list();
        // LAZY LOADING
        if (category || attributeList) {
            for (Product product : productList) {
                if (category) product.getCategory().getCategoryId();
                if (attributeList) {
                    List<ProductAttribute> productAttributeList = product.getProductAttributeList();
                    for (ProductAttribute productAttribute : productAttributeList) {
                        productAttribute.getAttribute().getAttributeId();
                    }
                }
            }
        }
        return productList;
    }

    public Product getProductById(int productId, boolean category, boolean attributeList) {
        Product product = (Product) sessionFactory.getCurrentSession().get(Product.class, productId);
        // LAZY LOADING
        if (category) product.getCategory().getCategoryId();
        if (attributeList) {
            List<ProductAttribute> productAttributeList = product.getProductAttributeList();
            for (ProductAttribute productAttribute : productAttributeList) {
                productAttribute.getAttribute().getAttributeId();
            }
        }
        return product;
    }

    public List<Product> getProductByCategoryId(int categoryId, boolean category, boolean attributeList) {
        List<Product> productList = sessionFactory.getCurrentSession().createQuery("FROM Product WHERE categoryId = " + categoryId).list();
        // LAZY LOADING
        if (category || attributeList) {
            for (Product product : productList) {
                if (attributeList) {
                    List<ProductAttribute> productAttributeList = product.getProductAttributeList();
                    for (ProductAttribute productAttribute : productAttributeList) {
                        productAttribute.getAttribute().getAttributeId();
                    }
                }
                if (category) product.getCategory().getCategoryId();
            }
        }
        return productList;
    }

    public boolean addProduct(Product product) {
        try {
            saveOrUpdate(product);
            List<ProductAttribute> productAttributeList = product.getProductAttributeList();
            if (productAttributeList != null) {
                for (ProductAttribute productAttribute : productAttributeList) {
                    productAttribute.setProductId(product.getProductId());
                    sessionFactory.getCurrentSession().save(productAttribute);
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        System.out.println("UPDATE PRODUCT ");
        System.out.println(product.getProductName());
        System.out.println(product.getProductDescription());
        System.out.println(product.getProductPrice());
        System.out.println(product.getCategoryId());
        return saveOrUpdate(product);
    }

    public boolean deleteProduct(int productId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE Product where productId = :productId");
            query.setParameter("productId", productId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteProductByCategoryId(int categoryId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE Product where categoryId = :categoryId");
            query.setParameter("categoryId", categoryId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean saveOrUpdate(Product product) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(product);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
