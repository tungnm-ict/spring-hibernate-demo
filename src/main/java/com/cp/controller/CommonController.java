package com.cp.controller;

import com.cp.model.Category;
import com.cp.service.contract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

/**
 * CommonController.java
 *
 * @author ManhTung
 * @version 3/1/18.
 */
@Controller
public class CommonController {

    protected Validator validator;
    protected CategoryService categoryService;

    public CommonController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("cateList")
    public List<Category> getCateList() {
        return categoryService.getAllCategory(false, false);
    }
}
