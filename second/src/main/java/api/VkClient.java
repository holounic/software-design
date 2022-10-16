package api;

import java.time.Instant;

public interface VkClient {
    VkNewsfeedSearchResponse searchNewsfeed(String query, Instant start, Instant end);
}
