package com.microservices.store.product.repository;

import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import com.microservices.store.product.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

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
    void whenCategoryA_Have2Product() {

        boolean hasProducts  = repositoryCategory.hasProducts(categoryA.getId());
        assertTrue(hasProducts);
    }
    @Test
    void whenCategoryB_Have1Product() {

        boolean hasProducts  = repositoryCategory.hasProducts(categoryB.getId());
        assertTrue(hasProducts);
    }

    @Test
    void whenCategoryC_Have0Product() {

        boolean hasProducts  = repositoryCategory.hasProducts(categoryC.getId());
        assertFalse(hasProducts);
    }
    @Test
    void whenCategoryNotExist_returnFalse() {
        Long categoryIdInvalid = 9999L;
        boolean hasProducts  = repositoryCategory.hasProducts(categoryIdInvalid);
        assertFalse(hasProducts);
    }


}
