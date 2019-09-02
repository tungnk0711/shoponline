package com.codegym.service;

import com.codegym.model.Category;
import org.springframework.data.repository.query.Param;

public interface CategoryService {

    Iterable<Category> findAll();

    Category findById(Long id);

    void save(Category province);

    void remove(Long id);

    String findNameByIdABCXYZ(Long id);

}
