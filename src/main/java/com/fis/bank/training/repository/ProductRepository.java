package com.fis.bank.training.repository;


import com.fis.bank.training.model.Category;
import com.fis.bank.training.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsProductByName(String name);

    List<Product> findByCategoryName(String category);
}
