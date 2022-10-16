package ru.akirakozov.sd.refactoring.command;

 import java.sql.SQLException;
 import java.util.Optional;

 public class CountProducts extends ProductCommand {

     @Override
     public String toHtml() throws SQLException {
         StringBuilder html = new StringBuilder();
         html.append("<html><body>\n");
         html.append("Number of products: \n");
         Optional<Integer> count = productDAO.getProductCount();
         count.ifPresent(integer -> html.append(integer).append("\n"));
         html.append("</body></html>");
         return html.toString();
     }
 }