package token;

public class OpenBrace extends Brace {
    @Override
    public TokenType tokenType() {
        return TokenType.OPEN_BRACE;
    }

    @Override
    public String toString() {
        return "(";
    }
}
