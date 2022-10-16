package ru.akirakozov.sd.refactoring.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class DbUtils {
    public static final Map<String, Integer> SAMPLE_DATA = new LinkedHashMap<>();

    static {
        SAMPLE_DATA.put("min", 10);
        SAMPLE_DATA.put("middle", 25);
        SAMPLE_DATA.put("max", 50);
    }
    private static final String DB_URL = "jdbc:sqlite:test.db";
    private static final String INIT_PRODUCT_SQL =
            "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    private static final String CLEAR_PRODUCTS_SQL = "DELETE FROM PRODUCT";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initProducts() throws SQLException {
        try (Connection c = getConnection()) {
            try (Statement s = c.createStatement()) {
                s.executeUpdate(INIT_PRODUCT_SQL);
            }
        }
    }

    public static void clearProducts() throws SQLException {
        try (Connection c = getConnection()) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(CLEAR_PRODUCTS_SQL);
            }
        }
    }

    public static void addSampleData() throws SQLException {
        for (Map.Entry<String, Integer> entry : DbUtils.SAMPLE_DATA.entrySet()) {
            addProduct(entry.getKey(), entry.getValue());
        }
    }

    public static void addProduct(String name, int value) throws SQLException {
        try (Connection c = getConnection()) {
            try (Statement s = c.createStatement()) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + value + ")";
                s.executeUpdate(sql);
            }
        }
    }
}