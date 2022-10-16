package api;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record VkNewsfeedManager(VkClient client) {

    public List<Long> getHashtagStatistics(String hashtag, Instant from, int hoursBack) {
        var res = new ArrayList<Long>();
        for (long i = hoursBack - 1; i >= 0; i--) {
            Instant endTime = from.minus(Duration.ofHours(i));
            Instant startTime = endTime.minus(Duration.ofHours(1));
            VkNewsfeedSearchResponse response = client.searchNewsfeed(hashtag, startTime, endTime);
            res.add(response.totalCount);
        }
        return res;
    }
}
