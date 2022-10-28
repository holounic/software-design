package token;

import state.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private State state = new StartState();
    private final InputStreamReader input;
    private int index = -1;
    private char curChar = (char) -1;

    public List<Token> tokenize() throws IOException, ParseException {
        List<Token> tokens = new ArrayList<>();
        nextChar();
        state.next(this);
        while (!(state instanceof ErrorState || state instanceof EndState)) {
            tokens.add(state.createToken(this));
            while (!isEOF() && isWhitespace()) {
                nextChar();
            }
            state.next(this);
        }
        if (state instanceof ErrorState) {
            throw new ParseException(((ErrorState) state).getMessage(), index);
        }
        return tokens;
    }

    public void nextChar() throws IOException {
        index++;
        curChar = (char) input.read();
    }

    public Tokenizer(InputStream is) {
        input = new InputStreamReader(is);
    }

    public void state(State state) {
        this.state = state;
    }

    public boolean isOperationOrBrace() {
        return "+-*/()".indexOf(curChar) != -1;
    }

    public boolean isDigit() {
        return Character.isDigit(curChar);
    }

    public boolean isEOF() {
        return curChar == (char) -1;
    }

    public boolean isWhitespace() {
        return Character.isWhitespace(curChar);
    }

    public char curChar() {
        return curChar;
    }

    public int parseNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (!isEOF() && isDigit()) {
            sb.append(curChar());
            nextChar();
        }
        return Integer.parseInt(sb.toString());
    }
}
