package com.cp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HomeController.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Controller 
public class HomeController extends CommonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "index";
    }

    @RequestMapping(value="/404")
    public String error404(){
        return "error/404";
    }

    @RequestMapping(value="/500")
    public String error500(){
        return "error/500";
    }
}
