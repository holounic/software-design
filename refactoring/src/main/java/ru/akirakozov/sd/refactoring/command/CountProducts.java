package ru.akirakozov.sd.refactoring.command;

 import java.sql.SQLException;

 public class CountProducts extends ProductCommand {

     @Override
     public String toHtml() throws SQLException {
         return htmlConverter.valueToHtml(productDAO.getProductCount().get(), "Number of products");
     }
 }