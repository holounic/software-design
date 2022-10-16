package ru.akirakozov.sd.refactoring.html;

 import ru.akirakozov.sd.refactoring.entity.Product;

 import java.util.List;

 public class ProductToHtmlConverter {

     public String productsToHtml(List<Product> products) {
         StringBuilder res = new StringBuilder();
         for (Product product : products) {
             res.append(productToLocalHtml(product));
         }
         return wrapBody(res.toString());
     }

     public String productToHtml(Product product, String message) {
         return wrapBody("<h1>" + message + ": </h1>\n" + productToLocalHtml(product));
     }

     public String valueToHtml(Integer value, String message) {
         return wrapBody(message + ": \n" + (value != null ? value : ""));
     }

     private String productToLocalHtml(Product product) {
         if (product == null) {
             return "";
         }
         return product.getName() + "\t" + product.getPrice() + "</br>";
     }

     private String wrapBody(String content) {
         return "<html><body>\n" + content + (content.equals("") ? "" : "\n") + "</body></html>";
     }
 }