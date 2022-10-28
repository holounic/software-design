package state;

import token.Token;
import token.Tokenizer;

import java.io.IOException;

public interface State {
    Token createToken(Tokenizer tokenizer) throws IOException;

    void next(Tokenizer tokenizer);
}
