package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@PropertySource("classpath:global_config_app.properties")
public class ProductController {

    @Autowired
    Environment env;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/products")
    public ModelAndView listProducts() {

        // demo store procedure
        String name = productService.getProductById(34);

        // demo store procedure
        // https://forums.mysql.com/read.php?98,386018,386040#msg-386040
        Iterable<Product> productList = productService.getAllProducts();

        Iterable<Product> products = productService.findAll();

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
    public ModelAndView saveProduct(@ModelAttribute("productform") ProductForm productform, BindingResult result, HttpServletRequest servletRequest) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = productform.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(productform.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        Product productObject = new Product(productform.getCreateDate(), fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive(), productform.getCategory());

        // luu vao db
        //productService.save(productObject);
        productService.addProduct(productObject);


        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ProductForm productForm = new ProductForm(product.getId(), product.getCreateDate(), null, product.getName(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getActive());
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
    public ModelAndView editProduct(@ModelAttribute("productform") ProductForm productform, BindingResult result) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = productform.getImage();
        String fileName = multipartFile.getOriginalFilename();


        // luu file len server
        try {
            FileCopyUtils.copy(productform.getImage().getBytes(), new File(env.getProperty("file_upload") + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // tao doi tuong de luu vao db
        Product productObject = new Product(productform.getId(), productform.getCreateDate(), fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive(), productform.getCategory());

        // luu vao db
        productService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("message", "Product edited successfully");
        return modelAndView;

    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }


}
