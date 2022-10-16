package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.ProductToHtmlConverter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.RESPONSE_CONTENT_TYPE;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final ProductDAO productDAO;
    private final ProductToHtmlConverter htmlConverter;

    public GetProductsServlet(ProductDAO productDAO, ProductToHtmlConverter htmlConverter) {
        this.productDAO = productDAO;
        this.htmlConverter = htmlConverter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resp;
        try {
            resp = htmlConverter.productsToHtml(productDAO.getProducts());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.getWriter().println(resp);
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
