package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.*;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(NAME_COLUMN);
        long price = Long.parseLong(request.getParameter(PRICE_COLUMN));

        try {
            addProduct(new Product(name, price));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }

    private void addProduct(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
