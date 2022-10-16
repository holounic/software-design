package api;

import org.junit.jupiter.api.Test;
import com.google.gson.JsonSyntaxException;

import static org.assertj.core.api.Assertions.*;

class VkNewsfeedSearchResponseParserTest {
    private final static String CORRECT_RESPONSE = """
            {
              "response": {
                "total_count": 618
              }
            }
            """;

    private final static String INVALID_RESPONSE = """
            {
              "resp....!!%
            }
            """;

    @Test
    public void parseResponse() {
        var parser = new VkNewsfeedSearchResponseParser();
        var resp = parser.parseResponse(CORRECT_RESPONSE);

        assertThat(resp.totalCount).isEqualTo(618);
    }

    @Test
    public void parseMalformedResponse() {
        var parser = new VkNewsfeedSearchResponseParser();
        assertThatExceptionOfType(JsonSyntaxException.class)
                .isThrownBy(() -> parser.parseResponse(INVALID_RESPONSE));
    }
}
