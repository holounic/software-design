package ru.akirakozov.sd.refactoring.command;

 import java.sql.SQLException;
 import java.util.Optional;

 public class SumPrice extends ProductCommand {

     @Override
     public String toHtml() throws SQLException {
         StringBuilder html = new StringBuilder();
         html.append("<html><body>\n");
         html.append("Summary price: \n");
         Optional<Integer> sum = productDAO.getProductPriceSum();
         sum.ifPresent(integer -> html.append(integer).append("\n"));
         html.append("</body></html>");
         return htmlConverter.valueToHtml(productDAO.getProductPriceSum().get(), "Summary price");
     }
 }