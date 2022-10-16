package ru.akirakozov.sd.refactoring.command;

import java.sql.SQLException;

 public class MaxProduct extends ProductCommand {

     @Override
     public String toHtml() throws SQLException {
         return htmlConverter.productToHtml(productDAO.getMaxProduct().get(), "Product with max price");
     }
 }