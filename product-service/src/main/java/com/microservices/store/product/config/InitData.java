package com.microservices.store.product.config;

import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import com.microservices.store.product.repository.CategoryRepository;
import com.microservices.store.product.repository.ProductRepository;
import com.microservices.store.product.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitData.class);
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        Category categoryA = new Category().builder().name("Categoria A").build();
        Category categoryB = new Category().builder().name("Categoria B").build();
        Category categoryC = new Category().builder().name("Categoria C").build();

        categoryRepository.saveAll(List.of(categoryA, categoryB, categoryC));

        Product productA = new Product().builder().name("Product A").price(25D).stock(10D).status(Status.CREATED).category(categoryA).build();
        Product productB = new Product().builder().name("Product B").price(15D).stock(1D).status(Status.CREATED).category(categoryA).build();
        Product productC = new Product().builder().name("Product C").price(5D).stock(2D).status(Status.CREATED).category(categoryB).build();

        productRepository.saveAll(List.of(productA, productB, productC));

        log.info("Categorias Creadas "+categoryRepository.findAll());
        log.info("Productos Creados "+productRepository.findAll());

    }


}
