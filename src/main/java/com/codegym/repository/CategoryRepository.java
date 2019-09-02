package com.codegym.repository;

import com.codegym.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query("SELECT c.name FROM Category c where c.id = :id")
    String findNameByIdABC(@Param("id") Long id);
}
