package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImplTest {
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "kek", 100L),
            new Product(2L, "KEKWait", 150L),
            new Product(3L, "lol", 200L),
            new Product(4L, "lolul", 250L),
            new Product(5L, "OMEGALuL", 300L),
            new Product(6L, "bla_bla", 350L) );
    private final List<Product> EMPTY_LIST = new ArrayList<>();
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "lol", 200L);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "kekwait", 150L);
    private final Product EXPECTED_SAVE_PRODUCT = new Product(7L, "new_prod", 500L);
    private DataSource dataSource;
    private ProductsRepository productsRepository;
    ProductsRepository emptyDataSource = new ProductsReposutoryJdbcImpl(null);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts("schema.sql", "data.sql")
                .build();
        productsRepository = new ProductsReposutoryJdbcImpl(dataSource);
    }

    @Test
    void testFindALL() {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
        Assertions.assertEquals(EMPTY_LIST, emptyDataSource.findAll());
    }

    @Test
    void testFindById() {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT,
                productsRepository.findById(3L).get());
        Assertions.assertEquals(Optional.empty(), productsRepository.findById(20L));
        Assertions.assertEquals(Optional.empty(), emptyDataSource.findById(20L));
    }

    @Test
    void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT,
                productsRepository.findById(1L).get());
        emptyDataSource.update(EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void testSave() {
        productsRepository.save(EXPECTED_SAVE_PRODUCT);
        Assertions.assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(7L).get());
        emptyDataSource.save(EXPECTED_SAVE_PRODUCT);
    }

    @Test
    void testDelete() {
        productsRepository.delete(1L);
        Assertions.assertEquals(Optional.empty(), productsRepository.findById(1L));
        emptyDataSource.delete(1L);
    }
}
