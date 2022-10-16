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
        try (Connection c = getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            List<Product> res = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                res.add(new Product(name, price));
            }
            rs.close();
            stmt.close();
            return res;
        }
    }

    public Optional<Product> getMaxProduct() throws SQLException {
        try (Connection c = getConnection()) {
            Product res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                res = new Product(name, price);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
    }

    public Optional<Product> getMinProduct() throws SQLException {
        try (Connection c = getConnection()) {
            Product res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                res = new Product(name, price);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
    }


    public Optional<Integer> getProductPriceSum() throws SQLException {
        try (Connection c = getConnection()) {
            Integer res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

            while (rs.next()) {
                res = rs.getInt(1);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
    }

    public Optional<Integer> getProductCount() throws SQLException {
        try (Connection c = getConnection()) {
            Integer res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

            while (rs.next()) {
                res = rs.getInt(1);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
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
}