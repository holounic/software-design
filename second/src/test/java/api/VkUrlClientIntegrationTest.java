package api;

import com.xebialabs.restito.server.StubServer;
import http.RawUrlReader;
import org.glassfish.grizzly.http.Method;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

class VkUrlClientIntegrationTest {
    private static final int PORT = 32453;

    @Test
    public void readAsText() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method"))
                    .then(stringContent("{\"response\": {\"total_count\": 618}\n}"));
            VkNewsfeedSearchResponse result = new VkUrlClient("http://localhost:" + PORT, new RawUrlReader())
                    .searchNewsfeed("#test",
                            Instant.ofEpochSecond(100),
                            Instant.ofEpochSecond(200));

            assertThat(result.totalCount).isEqualTo(618);
            verifyHttp(s).once(
                    method(Method.GET),
                    uri("/method/newsfeed.search"),
                    parameter("q", "#test"),
                    parameter("start_time", "100"),
                    parameter("end_time", "200"));
        });
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
