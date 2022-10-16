package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private static final String MAX_PRICE_HEADER = "<h1>Product with max price: </h1>";
    private static final String MIN_PRICE_HEADER = "<h1>Product with min price: </h1>";

    private static final String SUMMARY_PRICE_TEXT = "Summary price: ";
    private static final String NUMBER_OF_PRODUCTS_TEXT = "Number of products: ";

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(COMMAND_PARAM);

        StringBuilder html = new StringBuilder();
        if (MAX_COMMAND.equals(command)) {
            try {
                html.append(OPEN_TAGS).append("\n").append(MAX_PRICE_HEADER).append("\n");
                Optional<Product> res = productDAO.getMaxProduct();
                res.ifPresent(product -> html.append(product.getName())
                        .append("\t")
                        .append(product.getPrice())
                        .append("</br>\n"));
                html.append(CLOSE_TAGS).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (MIN_COMMAND.equals(command)) {
            try {
                html.append(OPEN_TAGS).append("\n").append(MIN_PRICE_HEADER).append("\n");
                Optional<Product> res = productDAO.getMinProduct();
                res.ifPresent(product -> html.append(product.getName())
                        .append("\t")
                        .append(product.getPrice())
                        .append("</br>\n"));
                html.append(CLOSE_TAGS).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (SUM_COMMAND.equals(command)) {
            try {
                html.append(OPEN_TAGS).append("\n").append(SUMMARY_PRICE_TEXT).append("\n");
                Optional<Integer> sum = productDAO.getProductPriceSum();
                sum.ifPresent(integer -> html.append(integer).append("\n"));
                html.append(CLOSE_TAGS).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (COUNT_COMMAND.equals(command)) {
            try {
                html.append(OPEN_TAGS).append("\n").append(NUMBER_OF_PRODUCTS_TEXT).append("\n");
                Optional<Integer> count = productDAO.getProductCount();
                count.ifPresent(integer -> html.append(integer).append("\n"));
                html.append(CLOSE_TAGS).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            html.append("Unknown command: ").append(command).append("\n");
        }
        response.getWriter().println(html);
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
