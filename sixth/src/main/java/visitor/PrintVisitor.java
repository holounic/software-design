package visitor;

import token.Brace;
import token.Number;
import token.Operation;
import token.Token;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final PrintStream out;

    public PrintVisitor(OutputStream out) {
        this.out = new PrintStream(out);
    }

    @Override
    public void visit(Number number) {
        print(number);
    }

    @Override
    public void visit(Brace brace) {
        throw new IllegalArgumentException("Brace found in notation");
    }

    @Override
    public void visit(Operation operation) {
        print(operation);
    }

    private void print(Token token) {
        out.print(token + " ");
    }

    public void printPolish(List<Token> tokens) {
        tokens.forEach(t -> t.accept(this));
        out.println();
    }
}
