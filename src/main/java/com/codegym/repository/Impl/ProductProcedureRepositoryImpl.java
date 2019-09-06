package com.codegym.repository.Impl;

import com.codegym.model.Product;
import com.codegym.repository.ProductProcedureRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.Timestamp;
import java.util.List;

public class ProductProcedureRepositoryImpl implements ProductProcedureRepository {
    @PersistenceContext
    private EntityManager em;

    // demo store procedure
    @Override
    public Iterable<Product> getAllProducts() {
        StoredProcedureQuery getAllProducts = em.createNamedStoredProcedureQuery("getAllProducts");
        getAllProducts.execute();
        Iterable<Product> productList = getAllProducts.getResultList();

        return productList;
    }

    // demo store procedure
    @Override
    public String getProductById(int id) {
        StoredProcedureQuery getProductNameStoredProcedure = em.createNamedStoredProcedureQuery("getProductById");
        getProductNameStoredProcedure.setParameter("in_id", id);
        getProductNameStoredProcedure.execute();
        String name = (String) getProductNameStoredProcedure.getOutputParameterValue("out_name");

        return name;
    }

    // demo store procedure
    @Override
    public void addProduct(Product product) {

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String createDate = sdf.format(product.getCreateDate());
        Integer categoryId = Integer.valueOf(product.getCategory().getId().intValue());

        StoredProcedureQuery spAddProduct = em.createNamedStoredProcedureQuery("addProduct");
        spAddProduct.setParameter("active", product.getActive());
        spAddProduct.setParameter("createDate", Timestamp.valueOf(createDate));
        spAddProduct.setParameter("description", product.getDescription());
        spAddProduct.setParameter("image", product.getImage());
        spAddProduct.setParameter("name", product.getName());
        spAddProduct.setParameter("price", product.getPrice());
        spAddProduct.setParameter("quantity", product.getQuantity());
        spAddProduct.setParameter("category_id", categoryId);
        spAddProduct.execute();
    }
}
