package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    public void initProducts() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement s = c.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)";
                s.executeUpdate(sql);
            }
        }
    }

    public void clearProducts() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement stmt = c.createStatement()) {
                String sql = "DELETE FROM PRODUCT";
                stmt.executeUpdate(sql);
            }
        }
    }

    public void addProduct(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public List<Product> getProducts() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
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
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
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
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
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
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
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
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
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
}