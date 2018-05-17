package com.cp.repository.contract;

import com.cp.model.Attribute;

import java.util.List;

/**
 * AttributeRepository.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
public interface AttributeRepository {

    List<Attribute> getAllAttribute(boolean category);

    Attribute getAttributeById(int attributeId, boolean category);

    List<Attribute> getAttributeByCategoryId(int categoryId, boolean category);

    boolean addAttribute(Attribute attribute);

    boolean updateAttribute(Attribute attribute);

    boolean deleteAttribute(int attributeId);
}
