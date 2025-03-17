package com.fis.bank.training.repository;

import com.fis.bank.training.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    boolean existsCategoryByName(String name);

    Optional<Category> findByName(String categoryName);
}
