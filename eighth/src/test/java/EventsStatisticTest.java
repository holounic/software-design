import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EventsStatisticTest {

    private static final double DELTA = 1e-6;
    private static final int MINUTES_SPAN = 20;
    private static final int MINUTES_TIMEFRAME;
    private static final int TIMEOUT_MINUTES_SPAN;

    static {
        MINUTES_TIMEFRAME = 60;
        TIMEOUT_MINUTES_SPAN = MINUTES_TIMEFRAME + 10;
    }
    SettableClock clock;
    EventsStatistic statistic;

    @BeforeEach
    public void setUp() {
        clock = new SettableClock(Instant.now());
        statistic = new EventsStatisticImpl(clock);
    }

    @Test
    public void testEmptyEvent() {
        assertEquals(0, statistic.getEventStatisticByName("no-test"), DELTA);
    }

    @Test
    public void testSingleEvent() {
        statistic.incEvent("test");
        statistic.incEvent("test");
        statistic.incEvent("test");
        statistic.incEvent("test");

        assertEquals(4.0 / MINUTES_TIMEFRAME, statistic.getEventStatisticByName("test"), DELTA);
    }

    @Test
    public void testSingleEvent_oldCleared() {
        statistic.incEvent("test");
        tick(Duration.ofMinutes(TIMEOUT_MINUTES_SPAN));

        assertEquals(0, statistic.getEventStatisticByName("test"), DELTA);
    }

    @Test
    public void testSingleEvent_oldReplaced() {
        statistic.incEvent("test");
        tick(Duration.ofMinutes(MINUTES_SPAN));

        statistic.incEvent("test");
        tick(Duration.ofMinutes(MINUTES_SPAN));
        statistic.incEvent("test");
        tick(Duration.ofMinutes(MINUTES_SPAN));
        statistic.incEvent("test");

        assertEquals(3.0 / MINUTES_TIMEFRAME, statistic.getEventStatisticByName("test"), DELTA);
    }

    @Test
    public void testMultipleEvents() {
        statistic.incEvent("test1");
        tick(Duration.ofMinutes(MINUTES_SPAN));
        statistic.incEvent("test1");
        statistic.incEvent("test2");
        tick(Duration.ofMinutes(MINUTES_SPAN));
        statistic.incEvent("test1");
        statistic.incEvent("test2");
        statistic.incEvent("test3");
        tick(Duration.ofMinutes(MINUTES_SPAN));
        statistic.incEvent("test1");

        Map<String, Double> stats = statistic.getAllEventStatistic();

        assertEquals(3.0 / MINUTES_TIMEFRAME, stats.get("test1"), DELTA);
        assertEquals(2.0 / MINUTES_TIMEFRAME, stats.get("test2"), DELTA);
        assertEquals(1.0 / MINUTES_TIMEFRAME, stats.get("test3"), DELTA);
    }

    private void tick(Duration duration) {
        clock.setNow(clock.now().plus(duration));
    }

    private static class SettableClock implements Clock {
        private Instant now;

        public SettableClock(Instant now) {
            this.now = now;
        }

        public void setNow(Instant now) {
            this.now = now;
        }

        @Override
        public Instant now() {
            return now;
        }
    }
}
