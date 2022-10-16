package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
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
    private final ProductDAO productDAO;

    public GetProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println(OPEN_TAGS);
            for (Product product : productDAO.getProducts()) {
                response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            response.getWriter().println(CLOSE_TAGS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
