package visitor;

import token.*;
import token.Number;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private List<Token> polish;
    private Deque<Token> stack;

    @Override
    public void visit(Number number) {
        polish.add(number);
    }

    @Override
    public void visit(Brace brace) {
        if (brace.tokenType() == TokenType.OPEN_BRACE) {
            stack.push(brace);
        } else {
            while (!stack.isEmpty() && stack.peek().tokenType() != TokenType.OPEN_BRACE) {
                polish.add(stack.pop());
            }
            if (stack.isEmpty()) {
                throw new IllegalStateException("Mismatched parentheses");
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation operation) {
        while (!stack.isEmpty() && operation.priority() <= operationPriority(stack.peek())) {
            polish.add(stack.pop());
        }
        stack.push(operation);
    }

    private int operationPriority(Token token) {
        if (token instanceof Operation) {
            return ((Operation) token).priority();
        }
        return 0;
    }

    public List<Token> toPolish(List<Token> tokens) {
        polish = new ArrayList<>();
        stack = new ArrayDeque<>();
        tokens.forEach(t -> t.accept(this));
        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (!(token instanceof Operation)) {
                throw new IllegalStateException("Expected operation");
            }
            polish.add(token);
        }
        return polish;
    }
}
