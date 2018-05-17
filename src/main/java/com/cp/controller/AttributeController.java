package com.cp.controller;

import com.cp.model.Attribute;
import com.cp.service.contract.AttributeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * AttributeController.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Controller
@RequestMapping(value = "/attribute")
public class AttributeController extends CommonController {

    private AttributeService attributeService;

    @Autowired
    public void setAttributeService(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doCreateNewAttribute(@RequestBody Attribute attribute) {
        Map<String, String> response = new HashMap<String, String>();

        Set<ConstraintViolation<Attribute>> violations = this.validator.validate(attribute);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<Attribute> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            response.put("validate", new Gson().toJson(errorMap));
            response.put("code", "9999");
            response.put("message", "Validate Failed");
        } else {
            boolean result = attributeService.addAttribute(attribute);

            if (result) {
                response.put("code", "1000");
                response.put("message", "Insert Attribute Success");
                response.put("attribute_insert", new Gson().toJson(attribute));
            } else {
                response.put("code", "9000");
                response.put("message", "Insert Attribute Failed");
            }
        }

        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doUpdateAttribute(@RequestBody Attribute attribute) {
        Map<String, String> response = new HashMap<String, String>();
        Set<ConstraintViolation<Attribute>> violations = this.validator.validate(attribute);
        if (violations.size() > 0) {
            Map<String, String> errorMapUD = new HashMap<String, String>();
            for (ConstraintViolation<Attribute> violation : violations) {
                errorMapUD.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            response.put("message", "Validate Failed");
            response.put("code", "9999");
            response.put("validate", new Gson().toJson(errorMapUD));
        } else {
            boolean result = attributeService.updateAttribute(attribute);
            if (result) {
                response.put("code", "1000");
                response.put("message", "Update Attribute Success");
                response.put("attribute_update", new Gson().toJson(attribute));
            } else {
                response.put("code", "9000");
                response.put("message", "Update Attribute Failed");
            }
        }

        return response;
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doDeleteAttribute(@RequestBody int attributeId) {
        Map<String, String> response = new HashMap<String, String>();

        try {
            boolean result = attributeService.deleteAttribute(attributeId);
            if (result) {
                response.put("code", "1000");
                response.put("message", "Delete Attribute Success");
            } else {
                response.put("code", "9000");
                response.put("message", "Delete Attribute Failed");
            }
        } catch (DataIntegrityViolationException ex) {
            response.put("code", "9999");
            response.put("message", "Delete Attribute Error");
            response.put("error", ex.getCause().getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> loadAttribute(@RequestBody int categoryId) {
        Map<String, String> response = new HashMap<String, String>();
        List<Attribute> attributeList = attributeService.getAttributeByCategoryId(categoryId, false);
        if (attributeList.size() > 0) {
            List<Attribute> newAttrList = new ArrayList<Attribute>();
            for (Attribute attr : attributeList) {
                newAttrList.add(new Attribute(attr.getAttributeId(), attr.getAttributeName(), attr.getCategoryId()));
            }
            response.put("attribute_list", new Gson().toJson(newAttrList));
        }
        return response;
    }
}
