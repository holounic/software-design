package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private static final String SELECT_FROM_PRODUCTS = "SELECT * FROM PRODUCT";
    private static final String NAME_COLUMN = "name";
    private static final String PRICE_COLUMN = "price";

    private static final String CONNECTION_URL = "jdbc:sqlite:test.db";

    private static final String RESPONSE_CONTENT_TYPE = "text/html";
    private static final String OPEN_TAGS = "<html><body>";
    private static final String CLOSE_TAGS = "</body></html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println(OPEN_TAGS);
            for (Product product : getProducts()) {
                response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            response.getWriter().println(CLOSE_TAGS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private List<Product> getProducts() throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_FROM_PRODUCTS);

            List<Product> res = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                long price = rs.getLong(PRICE_COLUMN);
                res.add(new Product(name, price));
            }
            rs.close();
            stmt.close();
            return res;
        }
    }
}
