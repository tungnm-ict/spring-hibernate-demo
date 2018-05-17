package com.cp.repository;

import com.cp.model.Attribute;
import com.cp.repository.contract.AttributeRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AttributeRepositoryImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Repository
@Transactional
public class AttributeRepositoryImpl implements AttributeRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Attribute> getAllAttribute(boolean category) {
        List<Attribute> attrList = sessionFactory.getCurrentSession().createQuery("FROM Attribute").list();
        // LAZY LOADING
        if (category) {
            for (Attribute attr : attrList) {
                attr.getCategory().getCategoryId();
            }
        }
        return attrList;
    }

    public Attribute getAttributeById(int attributeId, boolean category) {
        Attribute attribute = (Attribute) sessionFactory.getCurrentSession().get(Attribute.class, attributeId);
        // LAZY LOADING
        if (category) attribute.getCategory().getCategoryId();
        return attribute;
    }

    public List<Attribute> getAttributeByCategoryId(int categoryId, boolean category) {
        List<Attribute> attrList = sessionFactory.getCurrentSession().createQuery("FROM Attribute WHERE categoryId = " + categoryId).list();
        // LAZY LOADING
        if (category) {
            for (Attribute attr : attrList) {
                attr.getCategory().getCategoryId();
            }
        }
        return attrList;
    }

    public boolean addAttribute(Attribute attribute) {
        return saveOrUpdate(attribute);
    }

    public boolean updateAttribute(Attribute attribute) {
        return saveOrUpdate(attribute);
    }

    public boolean deleteAttribute(int attributeId) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE Attribute where attributeId = :attributeId");
            query.setParameter("attributeId", attributeId);
            return query.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean saveOrUpdate(Attribute attribute) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(attribute);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
