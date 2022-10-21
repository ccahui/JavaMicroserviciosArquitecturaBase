package com.microservices.store.product.repository;

import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import com.microservices.store.product.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository repositoryCategory;

    private Category categoryA;
    private Category categoryB;
    private Category categoryC;
    @BeforeEach
    void dbInsertData() {
        categoryA = new Category().builder().name("Categoria A").build();
        categoryB = new Category().builder().name("Categoria B").build();
        categoryC = new Category().builder().name("Categoria C").build();

        repositoryCategory.saveAll(List.of(categoryA, categoryB, categoryC));

        Product productA = new Product().builder().name("Product A").price(25D).stock(10D).status(Status.CREATED).category(categoryA).build();
        Product productB = new Product().builder().name("Product B").price(15D).stock(1D).status(Status.CREATED).category(categoryA).build();
        Product productC = new Product().builder().name("Product C").price(5D).stock(2D).status(Status.CREATED).category(categoryB).build();

        repository.saveAll(List.of(productA, productB, productC));
    }


    @AfterEach
    void dbTruncate(){
        repository.deleteAll();
        repositoryCategory.deleteAll();
    }

    @Test
    void whenFindByCategoryA_thenListWith2Product() {

        List<Product> filter = repository.findByCategory(categoryA);
        assertEquals(filter.size(), 2);
    }
    @Test
    void whenFindByCategoryB_thenListWith1Product() {

        List<Product> filter = repository.findByCategory(categoryB);
        assertEquals(filter.size(), 1);
    }
    @Test
    void whenFindByCategoryC_thenListEmptyProduct() {

        List<Product> filter = repository.findByCategory(categoryC);
        assertEquals(filter.size(), 0);
    }

}
