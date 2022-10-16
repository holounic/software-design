package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

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

}