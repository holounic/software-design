package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VkNewsfeedSearchResponseParser {
    public VkNewsfeedSearchResponse parseResponse(String json) {
        JsonObject parsedJson = (JsonObject) new JsonParser().parse(json);
        JsonObject response = (JsonObject) parsedJson.get("response");
        return new VkNewsfeedSearchResponse(response.get("total_count").getAsLong());
    }
}
