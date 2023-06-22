package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImpl implements ProductsRepository {
    private static final String FIND_PRODUCT_TEMPLATE = "SELECT * FROM test.product WHERE id=?";
    private static final String FIND_ALL_TEMPLATE = "SELECT * FROM test.product";
    private static final String UPDATE_PRODUCT_TEMPLATE = "UPDATE test.product SET name=?, price=? WHERE id=?";
    private static final String SAVE_PRODUCT_TEMPLATE = "INSERT INTO test.product (id, name, price) VALUES (?, ?, ?)";
    private static final String DELETE_PRODUCT_TEMPLATE = "DELETE FROM test.product WHERE id=?";
    private DataSource dataSource;

    public ProductsReposutoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();

        try (Connection dbConnection = dataSource.getConnection();
             Statement statement = dbConnection.createStatement()) {
            ResultSet resSet = statement.executeQuery(FIND_ALL_TEMPLATE);
            while (resSet.next()) {
                result.add(new Product(resSet.getLong(1),
                        resSet.getString(2), resSet.getLong(3)));
            }
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> result = Optional.empty();

        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement pStatement = dbConnection.prepareStatement(FIND_PRODUCT_TEMPLATE)) {
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return result;
            }
            result = Optional.of(new Product(resSet.getLong(1),
                    resSet.getString(2), resSet.getLong(3)));
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    @Override
    public void update(Product product) {
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(UPDATE_PRODUCT_TEMPLATE)) {
            statement.setLong(3, product.getId());
            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        try (Connection dbConnection = dataSource.getConnection();
         PreparedStatement statement = dbConnection.prepareStatement(SAVE_PRODUCT_TEMPLATE)) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setLong(3, product.getPrice());
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(DELETE_PRODUCT_TEMPLATE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    private void printError(String err) {
        System.err.println(err);
    }
}
