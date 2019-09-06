package com.codegym.repository;

import com.codegym.model.Product;

import java.util.List;

public interface ProductProcedureRepository {

    Iterable<Product> getAllProducts();

    String getProductById(int id);

    void addProduct(Product product);
}
