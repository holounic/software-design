import token.Token;
import token.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String input = new Scanner(System.in).nextLine();
        Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        List<Token> tokens = tokenizer.tokenize();
        List<Token> polish = new ParserVisitor().toPolish(tokens);
        new PrintVisitor(System.out).printPolish(polish);
        System.out.println(new CalcVisitor().calculatePolish(polish));
    }
}
