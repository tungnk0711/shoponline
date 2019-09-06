package com.codegym.service.Impl;

import com.codegym.model.Product;
import com.codegym.repository.ProductProcedureRepository;
import com.codegym.repository.ProductRepository;
import com.codegym.service.ProductService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.activation.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class ProductServiecImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductProcedureRepository productProcedureRepository;

    @Override
    public Iterable<Product> getAllProducts() {
        return productProcedureRepository.getAllProducts();
    }

    @Override
    public void addProduct(Product product) {
        productProcedureRepository.addProduct(product);
    }

    @Override
    public String getProductById(int id) {
        return productProcedureRepository.getProductById(id);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public void save(Product customer) {
        productRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> findAllWithEagerRelationships() {
        return productRepository.findAllWithEagerRelationships();
    }
}
