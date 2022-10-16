package ru.akirakozov.sd.refactoring.command;

import java.sql.SQLException;

public class MinProduct extends ProductCommand {

    @Override
    public String toHtml() throws SQLException {
        return htmlConverter.productToHtml(productDAO.getMinProduct().get(), "Product with min price");
    }
}