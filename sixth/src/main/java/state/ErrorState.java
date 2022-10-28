package state;

import token.Token;
import token.Tokenizer;

public class ErrorState implements State {

    private final String message;

    public ErrorState(String message) {
        this.message = message;
    }

    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Encountered error, cannot create token");

    }

    @Override
    public void next(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Encountered error, cannot proceed");
    }

    public String getMessage() {
        return message;
    }
}
