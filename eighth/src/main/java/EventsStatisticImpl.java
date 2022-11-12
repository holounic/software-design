import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    private final Clock clock;
    private final Map<String, List<Instant>> log = new HashMap<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        log.computeIfAbsent(name, n -> new ArrayList<>()).add(clock.now());
    }

    private double getEventStatisticByNameAfterInstant(String name, Instant start) {
        return log.getOrDefault(name, List.of())
                  .stream()
                  .filter(instant -> instant.isAfter(start)).count() / 60.0;
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant hourBefore = getHourBefore(clock.now());
        return getEventStatisticByNameAfterInstant(name, hourBefore);
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Instant hourBefore = getHourBefore(clock.now());
        return log.keySet()
                  .stream()
                  .collect(
                          Collectors.toMap(Function.identity(),
                                  name -> getEventStatisticByNameAfterInstant(name, hourBefore)));
    }

    private Instant getHourBefore(Instant from) {
        return from.minus(Duration.ofHours(1));
    }

    @Override
    public void printStatistic() {
        for (var stat : getAllEventStatistic().entrySet()) {
            System.out.printf("%.2f RPM for %s%n", stat.getValue(), stat.getKey());
        }
    }
}
