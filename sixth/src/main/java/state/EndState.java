package state;

import token.Token;
import token.Tokenizer;

public class EndState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Reached EOF, cannot create token");
    }

    @Override
    public void next(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Reached EOF, no following state expected");
    }
}
