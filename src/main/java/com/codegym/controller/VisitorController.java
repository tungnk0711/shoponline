package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Visitor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("visitor")
@RequestMapping("/trades")
public class VisitorController {

    @ModelAttribute("visitor")
    public Visitor getVisitor () {
        return new Visitor(1,"tungnk");
    }

    /*@RequestMapping("/")
    public ModelAndView listProducts() {
        Iterable<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list", "products", products);
        return modelAndView;
    }*/
}
