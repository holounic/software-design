import api.VkUrlClient;
import api.VkNewsfeedManager;
import http.RawUrlReader;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var c = new VkNewsfeedManager(new VkUrlClient("https://api.vk.com", new RawUrlReader()));
        List<Long> res = c.getHashtagStatistics("hello", Instant.now(), 24);
        System.out.println(res);
    }
}
