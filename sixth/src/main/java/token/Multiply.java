package token;

public class Multiply extends Operation {
    @Override
    public TokenType tokenType() {
        return TokenType.MULTIPLY;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public Number apply(Number a, Number b) {
        return new Number(a.value() * b.value());
    }

    @Override
    public String toString() {
        return "*";
    }
}
