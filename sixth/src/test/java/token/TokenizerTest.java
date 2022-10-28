package token;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    public void testSingleToken() throws IOException, ParseException {
        assertEquals(List.of(new Number(9)), tokens("9"));
        assertEquals(List.of(new Minus()), tokens("-"));
        assertEquals(List.of(new CloseBrace()), tokens(")"));
    }

    @Test
    public void testBinaryOperation() throws IOException, ParseException {
        assertEquals(List.of(new Number(20), new Plus(), new Number(2)), tokens("20 + 2"));
        assertEquals(List.of(new Number(30), new Divide(), new Number(20)), tokens("30 / 20"));
    }

    private static final String EXPRESSION_WITH_BRACES = "(31 - 5) / 9";
    @Test
    public void testBraces() throws IOException, ParseException {
        assertEquals(List.of(
                        new OpenBrace(),
                        new Number(31),
                        new Minus(),
                        new Number(5),
                        new CloseBrace(),
                        new Divide(),
                        new Number(9)),
                tokens(EXPRESSION_WITH_BRACES));
    }

    private static final String EXPRESSION_WITH_MANY_WHITESPACES = "(         1111 +     92)    /     3";
    @Test
    public void testWhitespace() throws IOException, ParseException {
        assertEquals(List.of(new OpenBrace(),
                        new Number(1111),
                        new Plus(),
                        new Number(92),
                        new CloseBrace(),
                        new Divide(),
                        new Number(3)),
                tokens(EXPRESSION_WITH_MANY_WHITESPACES));
    }

    @Test
    public void testError() {
        assertThrows(ParseException.class, () -> tokens("1 2"));
        assertThrows(ParseException.class, () -> tokens("1 ps 2"));
    }

    private List<Token> tokens(String input) throws IOException, ParseException {
        return new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).tokenize();
    }

}
