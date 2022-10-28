package visitor;

import token.Brace;
import token.Number;
import token.Operation;
import token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

public class CalcVisitor implements TokenVisitor {
    private Deque<Number> stack;

    @Override
    public void visit(Number number) {
        stack.push(number);
    }

    @Override
    public void visit(Brace brace) {
        throw new IllegalArgumentException("Brace found in notation");
    }

    @Override
    public void visit(Operation operation) {
        try {
            Number a = stack.pop();
            Number b = stack.pop();
            stack.push(operation.apply(b, a));
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Not enough operands for operation");
        }
    }

    public int calculatePolish(List<Token> tokens) {
        stack = new ArrayDeque<>();
        tokens.forEach(t -> t.accept(this));
        if (stack.size() != 1) {
            throw new IllegalStateException("Incomplete expression");
        }
        return stack.pop().value();
    }
}
