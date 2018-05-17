package com.cp.repository;

import com.cp.model.Attribute;
import com.cp.model.Category;
import com.cp.repository.contract.CategoryRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CategoryRepositoryImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Category> getAllCategory(boolean productList, boolean attributeList) {

        List<Category> cateList = sessionFactory.getCurrentSession().createQuery("FROM Category").list();
        // LAZY LOADING
        if (cateList != null && cateList.size() > 0) {
            if (productList || attributeList) {
                for (Category cate : cateList) {
                    if (productList) cate.getProductList().size();
                    if (attributeList) cate.getAttributeList().size();
                }
            }
        }
        return cateList;
    }

    public Category getCategoryById(int categoryId, boolean productList, boolean attributeList) {
        Category category = (Category) sessionFactory.getCurrentSession().get(Category.class, categoryId);
        // LAZY LOADING
        if (category != null) {
            if (productList) category.getProductList().size();
            if (attributeList) category.getAttributeList().size();
        }
        return category;
    }

    public boolean addCategory(Category category) {
        try {
            saveOrUpdate(category);
            List<Attribute> attributeList = category.getAttributeList();
            if (attributeList != null) {
                for (Attribute attr : attributeList) {
                    attr.setCategoryId(category.getCategoryId());
                    sessionFactory.getCurrentSession().save(attr);
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        return saveOrUpdate(category);
    }

    public boolean deleteCategory(int categoryId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE Category where categoryId = :categoryId");
            query.setParameter("categoryId", categoryId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean saveOrUpdate(Category category) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(category);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
