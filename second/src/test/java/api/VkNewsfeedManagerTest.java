package api;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class VkNewsfeedManagerTest {

    private VkNewsfeedManager vkNewsfeedManager;

    @Mock
    private VkClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vkNewsfeedManager = new VkNewsfeedManager(client);
    }

    @Test
    public void testSearchNewsFeedSingle() {
        var start = Instant.ofEpochSecond(3600);
        when(client.searchNewsfeed(eq("#test"), eq(Instant.ofEpochSecond(0)), eq(Instant.ofEpochSecond(3600))))
                .thenReturn(new VkNewsfeedSearchResponse(50));

        List<Long> stats = vkNewsfeedManager.getHashtagStatistics("#test", start, 1);

        verify(client, times(1))
                .searchNewsfeed(any(), any(), any());
        assertThat(stats).isEqualTo(List.of(50L));
    }

    @Test
    public void testSearchNewsFeedMultiple() {
        var start = Instant.now();
        when(client.searchNewsfeed(eq("#test"), any(), any()))
                .thenReturn(new VkNewsfeedSearchResponse(1),
                        new VkNewsfeedSearchResponse(2),
                        new VkNewsfeedSearchResponse(3),
                        new VkNewsfeedSearchResponse(4),
                        new VkNewsfeedSearchResponse(5));

        List<Long> stats = vkNewsfeedManager.getHashtagStatistics("#test", start, 5);

        verify(client, times(5)).searchNewsfeed(any(), any(), any());
        assertThat(stats).isEqualTo(List.of(1L, 2L, 3L, 4L, 5L));
    }
}
