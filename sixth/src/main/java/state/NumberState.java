package state;

import token.Number;
import token.Token;
import token.Tokenizer;

import java.io.IOException;

public class NumberState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) throws IOException {
        return new Number(tokenizer.parseNumber());
    }

    @Override
    public void next(Tokenizer tokenizer) {
        if (tokenizer.isOperationOrBrace()) {
            tokenizer.state(new StartState());
        } else if (tokenizer.isEOF()) {
            tokenizer.state(new EndState());
        } else {
            tokenizer.state(new ErrorState("Expected operation|brace|EOF, but found: " + tokenizer.curChar()));
        }
    }
}
