
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BerlinClockTest {

    private final BerlinClock clock = new BerlinClock();

    // Feature 1: Digital time -> Berlin Clock
    @Test
    void convertsMidnightToBerlinTime() {
        assertEquals(
                "YOOOOOOOOOOOOOOOOOOOOOOO",
                clock.convertToBerlinClock("00:00:00")
        );
    }

    @Test
    void converts131701ToBerlinTime() {
        assertEquals(
                "ORROORRROYYROOOOOOOOYYOO",
                clock.convertToBerlinClock("13:17:01")
        );
    }

    @Test
    void converts235959ToBerlinTime() {
        assertEquals(
                "ORRRRRRROYYRYYRYYRYYYYYY",
                clock.convertToBerlinClock("23:59:59")
        );
    }

    @Test
    void converts165006ToBerlinTime() {
        assertEquals(
                "YRRROROOOYYRYYRYYRYOOOOO",
                clock.convertToBerlinClock("16:50:06")
        );
    }

    @Test
    void converts113701ToBerlinTime() {
        assertEquals(
                "ORROOROOOYYRYYRYOOOOYYOO",
                clock.convertToBerlinClock("11:37:01")
        );
    }
}
