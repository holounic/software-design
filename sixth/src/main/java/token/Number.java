package token;

import visitor.TokenVisitor;

public record Number(int value) implements Token {

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public TokenType tokenType() {
        return TokenType.NUMBER;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) {
            return false;
        }
        return value == ((Number) obj).value;
    }
}
