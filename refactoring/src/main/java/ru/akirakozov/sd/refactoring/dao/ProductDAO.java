package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.CONNECTION_URL;

public class ProductDAO {
    public void initProducts() throws SQLException {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void clearProducts() throws SQLException {
        executeUpdate("DELETE FROM PRODUCT");
    }

    public void addProduct(Product product) throws SQLException {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    public List<Product> getProducts() throws SQLException {
        return executeQuery("SELECT * FROM PRODUCT", rs -> {
            String name = rs.getString("name");
            long price = rs.getLong("price");
            return new Product(name, price);
        });
    }

    public Optional<Product> getMaxProduct() throws SQLException {
        List<Product> products = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", rs -> {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            return new Product(name, price);
        });
        return lastOrEmpty(products);
    }

    public Optional<Product> getMinProduct() throws SQLException {
        List<Product> products = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", rs -> {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            return new Product(name, price);
        });
        return lastOrEmpty(products);
    }


    public Optional<Integer> getProductPriceSum() throws SQLException {
        List<Integer> sums = executeQuery("SELECT SUM(price) FROM PRODUCT", rs -> rs.getInt(1));
        return lastOrEmpty(sums);
    }

    public Optional<Integer> getProductCount() throws SQLException {
        List<Integer> sums = executeQuery("SELECT COUNT(*) FROM PRODUCT", rs -> rs.getInt(1));
        return lastOrEmpty(sums);
    }

    private void executeUpdate(String sql) throws SQLException {
        try (Connection c = getConnection()) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL);
    }

    private <T> List<T> executeQuery(String sql, SQLFunction<ResultSet, T> rsMapper) throws SQLException {
        try (Connection c = getConnection()) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    List<T> res = new ArrayList<>();
                    while (rs.next()) {
                        res.add(rsMapper.apply(rs));
                    }
                    return res;
                }
            }
        }
    }

    private <T> Optional<T> lastOrEmpty(List<T> products) {
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(products.size() - 1));
    }

    interface SQLFunction<T, R> {
        R apply(T arg) throws SQLException;
    }
}