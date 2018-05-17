package com.cp.controller;

import com.cp.model.Category;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CategoryController.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController extends CommonController {

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String index() {
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showCategories(ModelMap model) {
        if (model.get("selectedCategory") == null) {
            List<Category> allCategory = categoryService.getAllCategory(true, false);
            if (allCategory != null && allCategory.size() > 0)
                model.addAttribute("selectedCategory", allCategory.get(0));
        }
        return "category/list";
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public String showCategory(@PathVariable("categoryId") int categoryId, ModelMap model) {
        model.addAttribute("action", "show");
        Category category = categoryService.getCategoryById(categoryId, true, false);
        if (category != null) model.addAttribute("selectedCategory", category);
        return "category/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createNewCategory(ModelMap model) {
        model.addAttribute("selectedCategory", new Category());
        return "category/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String doCreateNewCategory(@ModelAttribute("category") Category category, ModelMap model, RedirectAttributes redirectAttributes) {
        Set<ConstraintViolation<Category>> violations = this.validator.validate(category);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<Category> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedCategory", category);
            return "category/create";
        }
        try {
            if (categoryService.addCategory(category)) {
                redirectAttributes.addFlashAttribute("msg", "Create Category Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Create Category Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
            }
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(category.getCategoryId(), true, false));
        } catch (ConstraintViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedCategory", category);
            return "category/create";
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/{categoryId}/edit", method = RequestMethod.GET)
    public String updateCategory(@PathVariable("categoryId") int categoryId, ModelMap model) {

        Category category = categoryService.getCategoryById(categoryId, false, true);
        if (category != null) model.addAttribute("selectedCategory", category);
        return "category/detail";
    }

    @RequestMapping(value = "/{categoryId}/update", method = RequestMethod.POST)
    public String doUpdateCategory(@PathVariable("categoryId") int categoryId, @ModelAttribute("category") Category category, ModelMap model, RedirectAttributes redirectAttributes) {
        Category originCategory = categoryService.getCategoryById(categoryId, false, true);
        Set<ConstraintViolation<Category>> violations = this.validator.validate(category);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<Category> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedCategory", originCategory);
            return "category/detail";
        }
        category.setCategoryId(categoryId);
        try {
            if (categoryService.updateCategory(category)) {
                redirectAttributes.addFlashAttribute("msg", "Update Category Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Update Category Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
            }
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(categoryId, true, false));
        } catch (ConstraintViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedCategory", originCategory);
            return "category/detail";
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/{categoryId}/destroy", method = RequestMethod.POST)
    public String doDeleteCategory(@PathVariable("categoryId") int categoryId, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            if (categoryService.deleteCategory(categoryId)) {
                redirectAttributes.addFlashAttribute("msg", "Delete Category Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Delete Category Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
                redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(categoryId, true, false));
            }
        } catch (DataIntegrityViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(categoryId, true, false));
            redirectAttributes.addFlashAttribute("msg", "Delete Category Fail!!!");
            redirectAttributes.addFlashAttribute("msgType", "error");
            redirectAttributes.addFlashAttribute("errors", errorMap);
        }
        return "redirect:/category/list";
    }

}
