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
    Product productA;
    Product productB ;
    Product productC;
    @BeforeEach
    void dbInsertData() {
        categoryA = new Category().builder().name("Categoria A").build();
        categoryB = new Category().builder().name("Categoria B").build();
        categoryC = new Category().builder().name("Categoria C").build();

        repositoryCategory.saveAll(List.of(categoryA, categoryB, categoryC));

        productA = new Product().builder().name("Product A").price(25D).stock(10D).status(Status.CREATED).category(categoryA).build();
        productB = new Product().builder().name("Product B").price(15D).stock(1D).status(Status.CREATED).category(categoryA).build();
        productC = new Product().builder().name("Product C").price(5D).stock(2D).status(Status.CREATED).category(categoryB).build();

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

    @Test
    void whenFindWhereArrayIds_thenListWith2Product() {

        List<Long> ids = List.of(productA.getId(), productB.getId());
        List<Product> filter = repository.findAllById(ids);
        assertEquals(filter.size(), 2);
        assertEquals(filter.get(0), productA);
        assertEquals(filter.get(1), productB);
    }
    @Test
    void whenFindWhereArrayIdsNotValid_thenListEmpty() {
        Long idInvalid = 9999L;
        List<Long> ids = List.of(idInvalid);
        List<Product> filter = repository.findAllById(ids);
    }
    @Test
    void whenFindWhereArray2ValidAnd1IdsInvalid_thenList2Element() {
        Long idInvalid = 9999L;
        List<Long> ids = List.of(productA.getId(), productB.getId(), idInvalid);
        List<Product> filter = repository.findAllById(ids);
        assertEquals(filter.size(), 2);
        assertEquals(filter.get(0), productA);
        assertEquals(filter.get(1), productB);
    }

    @Test
    void whenFindWhereArray2IdsValidRepeat_thenList1Element() {
        Long idRepeat = productA.getId();
        List<Long> ids = List.of(idRepeat, idRepeat );
        List<Product> filter = repository.findAllById(ids);
        assertEquals(1, filter.size());
    }
}
