package visitor;

import org.junit.jupiter.api.Test;
import token.*;
import token.Number;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserVisitorTest {

    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSingle() throws IOException, ParseException {
        assertEquals(List.of(new Number(30)), polish("30"));
    }

    @Test
    public void testBinaryOperation() throws IOException, ParseException {
        assertEquals(List.of(new Number(1), new Number(2), new Plus()), polish("1 + 2"));
        assertEquals(List.of(new Number(350), new Number(20), new Divide()), polish("350 / 20"));
    }

    @Test
    public void testBraces() throws IOException, ParseException {
        assertEquals(List.of(
                        new Number(30),
                        new Number(2),
                        new Plus(),
                        new Number(8),
                        new Divide()),
                polish("(30 + 2) / 8"));
    }

    @Test
    public void testPriority() throws IOException, ParseException {
        assertEquals(List.of(
                        new Number(2),
                        new Number(2),
                        new Multiply(),
                        new Number(2),
                        new Plus()),
                polish("2 * 2 + 2"));
        assertEquals(List.of(
                        new Number(2),
                        new Number(2),
                        new Number(2),
                        new Multiply(),
                        new Plus()),
                polish("2 + 2 * 2"));
        assertEquals(List.of(
                        new Number(2),
                        new Number(2),
                        new Minus(),
                        new Number(2),
                        new Minus()),
                polish("2 - 2 - 2"));
        assertEquals(List.of(
                        new Number(2),
                        new Number(2),
                        new Number(2),
                        new Minus(),
                        new Minus()),
                polish("2 - (2 - 2)"));
    }

    private List<Token> polish(String input) throws IOException, ParseException {
        return parserVisitor.toPolish(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).tokenize());
    }

}
