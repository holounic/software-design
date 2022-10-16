package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.*;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.utils.CommonUtils.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(COMMAND_PARAM);
        ProductCommand com;
        if (MAX_COMMAND.equals(command)) {
            com = new MaxProduct();
        } else if (MIN_COMMAND.equals(command)) {
            com = new MinProduct();
        } else if (SUM_COMMAND.equals(command)) {
            com = new SumPrice();
        } else if (COUNT_COMMAND.equals(command)) {
            com = new CountProducts();
        } else {
            com = new UnknownCommand(command);
        }
        try {
            response.getWriter().println(com.toHtml());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
