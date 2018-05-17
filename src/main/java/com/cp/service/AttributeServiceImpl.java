package com.cp.service;

import com.cp.model.Attribute;
import com.cp.repository.contract.AttributeRepository;
import com.cp.service.contract.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AttributeServiceImpl.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    private AttributeRepository attributeRepository;

    @Autowired
    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public List<Attribute> getAllAttribute(boolean category) {
        return this.attributeRepository.getAllAttribute(category);
    }

    public Attribute getAttributeById(int attributeId, boolean category) {
        return this.attributeRepository.getAttributeById(attributeId, category);
    }

    public List<Attribute> getAttributeByCategoryId(int categoryId, boolean category) {
        return this.attributeRepository.getAttributeByCategoryId(categoryId, category);
    }

    public boolean addAttribute(Attribute attribute) {
        return this.attributeRepository.addAttribute(attribute);
    }

    public boolean updateAttribute(Attribute attribute) {
        return this.attributeRepository.updateAttribute(attribute);
    }

    public boolean deleteAttribute(int attributeId) {
        return this.attributeRepository.deleteAttribute(attributeId);
    }
}
