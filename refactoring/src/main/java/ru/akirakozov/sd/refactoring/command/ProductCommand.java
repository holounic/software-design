package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;

import java.sql.SQLException;

public abstract class ProductCommand {

    protected final ProductDAO productDAO;

    protected ProductCommand() {
        this.productDAO = new ProductDAO();
    }

    public abstract String toHtml() throws SQLException;
}