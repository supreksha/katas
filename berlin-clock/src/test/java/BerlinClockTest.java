
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void convertsOddHourAndMinuteBoundaries() {
        assertEquals("OROOOOOOOYOOOOOOOOOOOOOO",
                clock.convertToBerlinClock("05:05:01"));
    }

    @Test
    void convertsEvenSecond() {
        assertEquals("YOOOOOOOOOOOOOOOOOOOOOOO",
                clock.convertToBerlinClock("00:00:02"));
    }

    @Test
    void convertsMaximumValidHour() {
        assertEquals("ORRRRRRROOOOOOOOOOOOOOOO",
                clock.convertToBerlinClock("23:00:01"));
    }

    @Test
    void convertsMaximumValidMinute() {
        assertEquals("OOOOOOOOOYYRYYRYYRYYYYYY",
                clock.convertToBerlinClock("00:59:01"));
    }

    @Test
    void rejectsNullDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock(null)
        );
    }

    @Test
    void rejectsNullStringDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("null")
        );
    }

    @Test
    void rejectsEmptyStringDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("  ")
        );
    }

    @Test
    void rejectsInvalidHourDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("25:00:00")
        );
    }

    @Test
    void rejectsInvalidMinDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:90:15")
        );
    }

    @Test
    void rejectsInvalidSecDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:00:85")
        );
    }

    @Test
    void rejectsInvalidDigitalTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("00:12")
        );
    }

    @Test
    void rejectsNegativeHour() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("-1:00:00"));
    }

    @Test
    void rejectsNegativeMinutes() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:-1:00"));
    }

    @Test
    void rejectsNegativeSeconds() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:00:-1"));
    }

    @Test
    void rejectsSingleDigitHour() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("1:00:00"));
    }

    @Test
    void rejectsMissingSeconds() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:00:"));
    }

    @Test
    void rejectsExtraTimeComponent() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("12:00:00:00"));
    }

    @Test
    void rejectsAlphabeticTime() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock("ab:cd:ef"));
    }

    @Test
    void rejectsHourWithLeadingWhitespace() {
        assertThrows(IllegalArgumentException.class,
                () -> clock.convertToBerlinClock(" 12:00:00"));
    }

    // Feature 2: Berlin Clock -> Digital time
    @Test
    void convertsBerlinMidnightToDigitalTime() {
        assertEquals(
                "00:00:00",
                clock.toDigitalTime("YOOOOOOOOOOOOOOOOOOOOOOO")
        );
    }

    @Test
    void convertsBerlinTimeTo131701() {
        assertEquals(
                "13:17:01",
                clock.toDigitalTime("ORROORRROYYROOOOOOOOYYOO")
        );
    }

    @Test
    void convertsBerlinTimeTo113701() {
        assertEquals(
                "11:37:01",
                clock.toDigitalTime("ORROOROOOYYRYYRYOOOOYYOO")
        );
    }

    @Test
    void convertsBerlinEndOfDayOneHourBeforeToDigitalTime() {
        assertEquals(
                "23:59:00",
                clock.toDigitalTime("YRRRRRRROYYRYYRYYRYYYYYY")
        );
    }

    @Test
    void convertsBerlinEndOfDayToDigitalTime() {
        assertEquals(
                "23:59:59",
                clock.toDigitalTime("ORRRRRRROYYRYYRYYRYYYYYY")
        );
    }

    @Test
    void convertsBerlinTimeTo000001() {
        assertEquals("00:00:01",
                clock.toDigitalTime("OOOOOOOOOOOOOOOOOOOOOOOO"));
    }

    @Test
    void convertsBerlinTimeTo1234() {
        assertEquals("12:34:00",
                clock.toDigitalTime("YRROORROOYYRYYROOOOOYYYY"));
    }

    @Test
    void convertsBerlinTimeTo2359() {
        assertEquals("23:59:00",
                clock.toDigitalTime("YRRRRRRROYYRYYRYYRYYYYYY"));
    }
}
