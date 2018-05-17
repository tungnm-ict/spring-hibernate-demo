package com.cp.service.contract;

import com.cp.model.Attribute;

import java.util.List;

/**
 * AttributeService.java
 *
 * @author ManhTung
 * @version 3/4/18.
 */
public interface AttributeService {

    List<Attribute> getAllAttribute(boolean category);

    Attribute getAttributeById(int attributeId, boolean category);

    List<Attribute> getAttributeByCategoryId(int categoryId, boolean category);

    boolean addAttribute(Attribute attribute);

    boolean updateAttribute(Attribute attribute);

    boolean deleteAttribute(int attributeId);
}
