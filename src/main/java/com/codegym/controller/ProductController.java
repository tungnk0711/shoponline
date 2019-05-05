package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    // thu muc luu tru file tren server
    private static String UPLOAD_LOCATION = "/Users/nguyenkhanhtung/Documents/JAVABOOTCAMP/shop/src/main/webapp/WEB-INF/resources/image/";

    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    public ModelAndView listProducts() {
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list", "products", products);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productform", new ProductForm());
        return modelAndView;
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public ModelAndView saveProduct(@ModelAttribute("productform") ProductForm productform, BindingResult result) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = productform.getImage();
        String fileName = multipartFile.getOriginalFilename();


        // luu file len server
        try {
            FileCopyUtils.copy(productform.getImage().getBytes(), new File(UPLOAD_LOCATION + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // tao doi tuong de luu vao db
        Product productObject = new Product(productform.getCreateDate(), fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive());

        // luu vao db
        productService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ProductForm productForm = new ProductForm(product.getId(),product.getCreateDate(), null, product.getName(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getActive());
            ModelAndView mv = new ModelAndView("/product/edit");
            mv.addObject("productform", productForm);
            mv.addObject("product", product);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/product/error");
            return mv;
        }
    }


    @PostMapping("/edit-product")
    public ModelAndView editProduct(@ModelAttribute("productform") ProductForm productform, BindingResult result){

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = productform.getImage();
        String fileName = multipartFile.getOriginalFilename();


        // luu file len server
        try {
            FileCopyUtils.copy(productform.getImage().getBytes(), new File(UPLOAD_LOCATION + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // tao doi tuong de luu vao db
        Product productObject = new Product(productform.getId(),productform.getCreateDate(), fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive());

        // luu vao db
        productService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("message", "Product edited successfully");
        return modelAndView;

    }

}
