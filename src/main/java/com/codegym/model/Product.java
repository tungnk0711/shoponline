package com.codegym.model;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "products")
@NamedQuery(name = "findProductsWithId",
        query = "select p from Product p where  p.id=:id")
@NamedQuery(
        name = "findAllProducts",
        query = "SELECT p FROM Product p")
@NamedStoredProcedureQuery(
        name = "getProductById",
        procedureName = "getProductById",
        parameters = {
                @StoredProcedureParameter(name = "in_id", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "out_name", mode = ParameterMode.OUT, type = String.class),
        }
)
@NamedStoredProcedureQuery(
        name = "getAllProducts",
        procedureName = "getAllProducts"
)
@NamedStoredProcedureQuery(
        name = "addProduct",
        procedureName = "sp_insert_product",
        parameters = {
                @StoredProcedureParameter(name = "active", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "createDate", mode = ParameterMode.IN, type = java.sql.Timestamp.class),
                @StoredProcedureParameter(name = "description", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "image", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "name", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "price", mode = ParameterMode.IN, type = Double.class),
                @StoredProcedureParameter(name = "quantity", mode = ParameterMode.IN, type = Double.class),
                @StoredProcedureParameter(name = "category_id", mode = ParameterMode.IN, type = Integer.class)
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String image;
    private String name;
    private Double price;
    private Double quantity;
    private String description;
    private Integer active;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    public Product(Date createDate, String image, String name, Double price, Double quantity, String description, Integer active, Category category) {
        this.createDate = createDate;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.active = active;
        this.category = category;
    }

    public Product(Long id, Date createDate, String image, String name, Double price, Double quantity, String description, Integer active, Category category) {
        this.id = id;
        this.createDate = createDate;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.active = active;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
