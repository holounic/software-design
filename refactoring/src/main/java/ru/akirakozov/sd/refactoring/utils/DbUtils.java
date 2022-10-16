package ru.akirakozov.sd.refactoring.utils;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {
    public static final List<Product> SAMPLE_DATA = new ArrayList<>();
    public static final ProductDAO productDAO = new ProductDAO();

    static {
        SAMPLE_DATA.add(new Product("min", 10));
        SAMPLE_DATA.add(new Product("middle", 25));
        SAMPLE_DATA.add(new Product("max", 50));
    }

    public static void addSampleData() throws SQLException {
        for (Product product : SAMPLE_DATA) {
            productDAO.addProduct(product);
        }
    }
}