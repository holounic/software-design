package visitor;

import org.junit.jupiter.api.Test;
import token.Tokenizer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalcVisitorTest {

    CalcVisitor calcVisitor = new CalcVisitor();
    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSingle() throws IOException, ParseException {
        assertEquals(2, eval("2"));
        assertEquals(550, eval("550  "));
    }

    @Test
    public void testBinaryOperation() throws IOException, ParseException {
        assertEquals(3, eval("1 + 2"));
        assertEquals(4, eval("32 / 8"));
    }


    @Test
    public void testMultiple() throws IOException, ParseException {
        assertEquals(6, eval("2 * 2 + 2"));
        assertEquals(-2, eval("2 - 2 - 2"));
        assertEquals(2, eval("2 - (2 - 2)"));
        assertEquals(4, eval("(30 + 2) / 8"));
        assertEquals(1279, eval("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"));
    }

    private int eval(String input) throws IOException, ParseException {
        return calcVisitor.calculatePolish(parserVisitor.toPolish(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).tokenize()));
    }

}
