package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.entity.Product;

import java.sql.SQLException;
import java.util.Optional;

public class MinProduct extends ProductCommand {

    @Override
    public String toHtml() throws SQLException {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>\n");
        html.append("<h1>Product with min price: </h1>\n");
        Optional<Product> minProduct = productDAO.getMinProduct();
        minProduct.ifPresent(product -> html.append(product.getName())
                .append("\t")
                .append(product.getPrice())
                .append("</br>\n"));
        html.append("</body></html>");
        return html.toString();
    }
}