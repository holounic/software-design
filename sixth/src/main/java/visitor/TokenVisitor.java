package visitor;

import token.Brace;
import token.Number;
import token.Operation;

public interface TokenVisitor {
    void visit(Number number);
    void visit(Brace brace);
    void visit(Operation operation);
}
