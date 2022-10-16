package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.ProductToHtmlConverter;

import java.sql.SQLException;

public abstract class ProductCommand {

    protected final ProductDAO productDAO;
    protected final ProductToHtmlConverter htmlConverter;

    protected ProductCommand() {
        this.productDAO = new ProductDAO();
        this.htmlConverter = new ProductToHtmlConverter();
    }

    public abstract String toHtml() throws SQLException;
}