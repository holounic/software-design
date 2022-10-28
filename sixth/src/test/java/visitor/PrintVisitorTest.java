package visitor;

import org.junit.jupiter.api.Test;
import token.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class PrintVisitorTest {
    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSingle() throws IOException, ParseException {
        assertEquals("30", print("30"));
        assertEquals("250", print("250"));
    }

    @Test
    public void testBinaryOperation() throws IOException, ParseException {
        assertEquals("30 2 +", print("30 + 2"));
        assertEquals("250 50 /", print("250 / 50"));
    }

    @Test
    public void testMultiple() throws IOException, ParseException {
        assertEquals("30 2 + 8 /", print("(30 + 2) / 8"));
        assertEquals("250 50 /", print("250 / 50"));
        assertEquals("2 2 2 * +", print("2 + 2 * 2"));
        assertEquals("2 2 - 2 -", print("2 - 2 - 2"));
        assertEquals("2 2 2 - -", print("2 - (2 - 2)"));
        assertEquals("23 10 + 5 * 3 32 5 + * 10 4 5 * - * - 8 2 / +",
                print("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"));
    }

    private String print(String input) throws IOException, ParseException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new PrintVisitor(out).printPolish(parserVisitor.toPolish(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).tokenize()));
        return out.toString().stripTrailing();
    }

}
