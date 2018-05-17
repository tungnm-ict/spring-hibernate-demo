package com.cp.controller;

import com.cp.model.Category;
import com.cp.model.Product;
import com.cp.service.contract.ProductService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ProductController.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends CommonController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createNewProduct(@RequestParam int categoryId, ModelMap model) {
        model.addAttribute("selectedCategory", categoryService.getCategoryById(categoryId, false, false));
        model.addAttribute("selectedProduct", new Product());
        model.addAttribute("action", "add");
        return "product/create";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String doCreateNewProduct(@ModelAttribute("product") Product product, ModelMap model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(product.getCategoryId(), false, false);
        Set<ConstraintViolation<Product>> violations = this.validator.validate(product);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<Product> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedProduct", product);
            return "product/create";
        }
        try {
            if (productService.addProduct(product)) {
                redirectAttributes.addFlashAttribute("msg", "Create Product Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Create Product Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
            }
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(product.getCategoryId(), true, false));
        } catch (ConstraintViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            model.addAttribute("errors", errorMap);
            model.addAttribute("selectedProduct", product);
            model.addAttribute("selectedCategory", category);
            return "product/create";
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/{product_id}/edit", method = RequestMethod.GET)
    public String updateProduct(@PathVariable("product_id") int productId, ModelMap model) {
        Product product = productService.getProductById(productId, false, true);
        Category category = categoryService.getCategoryById(product.getCategoryId(), false, false);
        model.addAttribute("selectedProduct", product);
        model.addAttribute("selectedCategory", category);
        return "product/detail";
    }

    @RequestMapping(value = "/{product_id}/update", method = RequestMethod.POST)
    public String doUpdateProduct(@PathVariable("product_id") int productId, @ModelAttribute("product") Product product, ModelMap model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(product.getCategoryId(), false, false);
        Set<ConstraintViolation<Product>> violations = this.validator.validate(product);
        if (violations.size() > 0) {
            Map<String, String> errorMap = new HashMap<String, String>();
            for (ConstraintViolation<Product> violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedProduct", product);
            model.addAttribute("errors", errorMap);
            return "product/detail";
        }
        try {
            if (productService.updateProduct(product)) {
                redirectAttributes.addFlashAttribute("msg", "Update Product Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Update Product Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
            }
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(product.getCategoryId(), true, false));
        } catch (ConstraintViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            model.addAttribute("selectedProduct", product);
            model.addAttribute("selectedCategory", category);
            model.addAttribute("errors", errorMap);
            return "product/detail";
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST)
    public String doDeleteProduct(@ModelAttribute("product") Product product, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            if (productService.deleteProduct(product.getProductId())) {
                redirectAttributes.addFlashAttribute("msg", "Delete Product Success!!!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Delete Category Fail!!!");
                redirectAttributes.addFlashAttribute("msgType", "error");
            }
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(product.getCategoryId(), true, false));

        } catch (DataIntegrityViolationException ex) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("", ex.getCause().getMessage());
            redirectAttributes.addFlashAttribute("selectedCategory", categoryService.getCategoryById(product.getCategoryId(), true, false));
            redirectAttributes.addFlashAttribute("errors", errorMap);
        }
        return "redirect:/category/list";
    }
}
