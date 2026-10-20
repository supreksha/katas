
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BerlinClockTest {

    private final BerlinClock clock = new BerlinClock();

    @Test
    void convertsMidnightToBerlinClock() {
        assertEquals(
                "YOOOOOOOOOOOOOOOOOOOOOOO",
                clock.convertToBerlinClock("00:00:00")
        );
    }
}
