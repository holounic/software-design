package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private static final String COMMAND_PARAM = "command";
    private static final String MIN_COMMAND = "min";
    private static final String MAX_COMMAND = "max";
    private static final String SUM_COMMAND = "sum";
    private static final String COUNT_COMMAND = "count";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(COMMAND_PARAM);

        if (MAX_COMMAND.equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                Optional<Product> res = getMaxProduct();
                if (res.isPresent()) {
                    response.getWriter().println(res.get().getName() + "\t" + res.get().getPrice() + "</br>");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (MIN_COMMAND.equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                Optional<Product> res = getMinProduct();
                if (res.isPresent()) {
                    response.getWriter().println(res.get().getName() + "\t" + res.get().getPrice() + "</br>");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (SUM_COMMAND.equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                Optional<Integer> sum = getProductPriceSum();
                if (sum.isPresent()) {
                    response.getWriter().println(sum.get());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (COUNT_COMMAND.equals(command)) {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
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

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Optional<Product> getMaxProduct() throws SQLException {
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

    private Optional<Product> getMinProduct() throws SQLException {
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


    private Optional<Integer> getProductPriceSum() throws SQLException {
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

    private Optional<Integer> getProductCount() throws SQLException {
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
