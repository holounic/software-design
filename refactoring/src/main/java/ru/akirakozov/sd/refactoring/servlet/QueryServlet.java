package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.servlet.Utils.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private static final String MAX_PRICE_HEADER = "<h1>Product with max price: </h1>";
    private static final String MIN_PRICE_HEADER = "<h1>Product with min price: </h1>";

    private static final String SUMMARY_PRICE_TEXT = "Summary price: ";
    private static final String NUMBER_OF_PRODUCTS = "Number of products: ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(COMMAND_PARAM);

        if (MAX_COMMAND.equals(command)) {
            try {
                response.getWriter().println(OPEN_TAGS);
                response.getWriter().println(MAX_PRICE_HEADER);
                Optional<Product> res = getMaxProduct();
                if (res.isPresent()) {
                    response.getWriter().println(res.get().getName() + "\t" + res.get().getPrice() + "</br>");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (MIN_COMMAND.equals(command)) {
            try {
                response.getWriter().println(OPEN_TAGS);
                response.getWriter().println(MIN_PRICE_HEADER);
                Optional<Product> res = getMinProduct();
                if (res.isPresent()) {
                    response.getWriter().println(res.get().getName() + "\t" + res.get().getPrice() + "</br>");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (SUM_COMMAND.equals(command)) {
            try {
                response.getWriter().println(OPEN_TAGS);
                response.getWriter().println(SUMMARY_PRICE_TEXT);
                Optional<Integer> sum = getProductPriceSum();
                if (sum.isPresent()) {
                    response.getWriter().println(sum.get());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (COUNT_COMMAND.equals(command)) {
            try {
                response.getWriter().println(OPEN_TAGS);
                response.getWriter().println(NUMBER_OF_PRODUCTS);
                Optional<Integer> count = getProductCount();
                if (count.isPresent()) {
                    response.getWriter().println(count.get());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Optional<Product> getMaxProduct() throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Product res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                int price = rs.getInt(PRICE_COLUMN);
                res = new Product(name, price);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
    }

    private Optional<Product> getMinProduct() throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Product res = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                int price = rs.getInt(PRICE_COLUMN);
                res = new Product(name, price);
            }

            rs.close();
            stmt.close();
            return Optional.ofNullable(res);
        }
    }


    private Optional<Integer> getProductPriceSum() throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
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

    private Optional<Integer> getProductCount() throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
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
