package token;

public class CloseBrace extends Brace {
    @Override
    public TokenType tokenType() {
        return TokenType.CLOSE_BRACE;
    }

    @Override
    public String toString() {
        return ")";
    }
}
