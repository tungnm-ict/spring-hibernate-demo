package com.cp.controller;

import com.cp.model.ProductAttribute;
import com.cp.service.contract.ProductAttributeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ProductAttributeController.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Controller
@RequestMapping(value = "/product_attribute")
public class ProductAttributeController extends CommonController {
    private ProductAttributeService productAttributeService;

    @Autowired
    public void setProductAttributeService(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doCreateNewAttribute(@RequestBody ProductAttribute productAttribute) {
        Map<String, String> response = new HashMap<String, String>();

        Set<ConstraintViolation<ProductAttribute>> violations = this.validator.validate(productAttribute);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<ProductAttribute> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            response.put("validate", new Gson().toJson(errorMap));
            response.put("code", "9999");
            response.put("message", "Validate Failed");
        } else {
            boolean result = productAttributeService.addProductAttribute(productAttribute);

            if (result) {
                response.put("message", "Insert Attribute Success");
                response.put("code", "1000");
                response.put("attribute_insert", new Gson().toJson(productAttribute));
            } else {
                response.put("code", "9000");
                response.put("message", "Insert Attribute Failed");
            }
        }

        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doUpdateAttribute(@RequestBody ProductAttribute productAttribute) {
        Map<String, String> response = new HashMap<String, String>();
        Set<ConstraintViolation<ProductAttribute>> violations = this.validator.validate(productAttribute);
        if (violations.size() > 0) {
            Map<String, String> errorMapUD = new HashMap<String, String>();
            for (ConstraintViolation<ProductAttribute> violation : violations) {
                errorMapUD.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            response.put("message", "Validate Failed");
            response.put("code", "9999");
            response.put("validate", new Gson().toJson(errorMapUD));
        } else {
            boolean result = productAttributeService.updateProductAttribute(productAttribute);
            if (result) {
                response.put("message", "Update Attribute Success");
                response.put("code", "1000");
                response.put("attribute_update", new Gson().toJson(productAttribute));
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
            boolean result = productAttributeService.deleteProductAttribute(attributeId);
            if (result) {
                response.put("message", "Delete Attribute Success");
                response.put("code", "1000");
            } else {
                response.put("code", "9000");
                response.put("message", "Delete Attribute Failed");
            }
        } catch (DataIntegrityViolationException ex) {
            response.put("code", "9999");
            response.put("message", "Delete ProductAttribute Error");
            response.put("error", ex.getCause().getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/destroy_all", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> doDeleteAllAttribute(@RequestBody int productId) {
        Map<String, String> response = new HashMap<String, String>();

        try {
            boolean result = productAttributeService.deleteProductAttributeByProductId(productId);
            if (result) {
                response.put("code", "1000");
                response.put("message", "Delete Attribute Success");
            } else {
                response.put("message", "Delete Attribute Failed");
                response.put("code", "9000");
            }
        } catch (DataIntegrityViolationException ex) {
            response.put("code", "9999");
            response.put("message", "Delete ProductAttribute Error");
            response.put("error", ex.getCause().getMessage());
        }
        return response;
    }
}
