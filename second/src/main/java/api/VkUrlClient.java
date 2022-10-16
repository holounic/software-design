package api;

import http.UrlReader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class VkUrlClient implements VkClient {

    private static final String API_KEY = System.getenv("VK_API_KEY");
    private final VkNewsfeedSearchResponseParser parser;
    private final UrlReader reader;
    private final String host;

    public VkUrlClient(String host, UrlReader reader) {
        this.host = host;
        this.reader = reader;
        this.parser = new VkNewsfeedSearchResponseParser();
    }

    @Override
    public VkNewsfeedSearchResponse searchNewsfeed(String query, Instant start, Instant end) {
        String response = reader.readAsText(createUrl(query, start.getEpochSecond(), end.getEpochSecond()));
        return parser.parseResponse(response);
    }

    private String createUrl(String query, long start, long end) {
        return "%s/method/newsfeed.search?q=%s&start_time=%d&end_time=%d&access_token=%s&v=5.131"
                .formatted(host, urlEncode(query), start, end, API_KEY);
    }

    private String urlEncode(String query) {
        return URLEncoder.encode(query, StandardCharsets.UTF_8);
    }
}
