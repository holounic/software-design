package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private static final String SELECT_FROM_PRODUCTS = "SELECT * FROM PRODUCT";

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
