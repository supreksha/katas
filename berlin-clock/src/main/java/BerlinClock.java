
public class BerlinClock {

    // Feature 1: Digital time -> Berlin Clock
    public String convertToBerlinClock(String time) {
        validateDigitalTime(time);

        int[] parts = parseTime(time);

        int hours = parts[0];
        int minutes = parts[1];
        int seconds = parts[2];

        return (secondsLamp(seconds))
                + row(hours / 5, 4, 'R')
                + row(hours % 5, 4, 'R')
                + minuteRow(minutes / 5)
                + row(minutes % 5, 4, 'Y');
    }

    private int[] parseTime(String time) {
        String[] parts = time.split(":");

        return new int[] {
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        };
    }

    private void validateDigitalTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Time cannot be null or empty"
            );
        }

        String[] parts = time.split(":", -1);

        if (parts.length != 3
                || !parts[0].matches("\\d{2}")
                || !parts[1].matches("\\d{2}")
                || !parts[2].matches("\\d{2}")) {
            throw new IllegalArgumentException(
                    "Time format must be HH:mm:ss"
            );
        }

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        if (hours > 23 || minutes > 59 || seconds > 59) {
            throw new IllegalArgumentException(
                    "Time is out of range"
            );
        }
    }

    private String row(int lightsOn, int length, char color) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(i < lightsOn ? color : 'O');
        }

        return result.toString();
    }

    private String minuteRow(int lightsOn) {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= 11; i++) {
            if (i > lightsOn) {
                result.append('O');
            } else {
                result.append(i % 3 == 0 ? 'R' : 'Y');
            }
        }

        return result.toString();
    }

    private String secondsLamp(int seconds) {
        return seconds % 2 == 0 ? "Y" : "O";
    }

    // Feature 2: Berlin Clock -> Digital time
    public String toDigitalTime(String berlinTime) {

        if (berlinTime == null || berlinTime.trim().isEmpty() || berlinTime.length() != 24) {
            throw new IllegalArgumentException("Berlin time cannot be empty and must contain 24 characters");
        }

        String secondsRow = berlinTime.substring(0, 1);
        String fiveHourRow = berlinTime.substring(1, 5);
        String oneHourRow = berlinTime.substring(5, 9);
        String fiveMinuteRow = berlinTime.substring(9, 20);
        String oneMinuteRow = berlinTime.substring(20, 24);

        validateLightPatterns(
                secondsRow,
                fiveHourRow,
                oneHourRow,
                fiveMinuteRow,
                oneMinuteRow
        );

        int hours = countLights(fiveHourRow, 'R') * 5
                + countLights(oneHourRow, 'R');

        int minutes = countLights(fiveMinuteRow, 'Y')
                + countLights(fiveMinuteRow, 'R');
        minutes = minutes * 5 + countLights(oneMinuteRow, 'Y');

        int seconds = secondsRow.equals("Y") ? 0 : 1;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private int countLights(String row, char color) {
        int count = 0;

        for (char light : row.toCharArray()) {
            if (light == color) {
                count++;
            }
        }

        return count;
    }

    private void validateLightPatterns(
            String secondsRow,
            String fiveHourRow,
            String oneHourRow,
            String fiveMinuteRow,
            String oneMinuteRow) {

        if (!secondsRow.matches("[YO]")
                || !fiveHourRow.matches("R{0,4}O{0,4}")
                || !oneHourRow.matches("R{0,4}O{0,4}")
                || !oneMinuteRow.matches("Y{0,4}O{0,4}")) {
            throw new IllegalArgumentException(
                    "Invalid Berlin Clock light pattern"
            );
        }

        boolean offSeen = false;

        for (int i = 0; i < fiveMinuteRow.length(); i++) {
            char light = fiveMinuteRow.charAt(i);

            if (light == 'O') {
                offSeen = true;
                continue;
            }

            char expected = (i + 1) % 3 == 0 ? 'R' : 'Y';

            if (offSeen || light != expected) {
                throw new IllegalArgumentException(
                        "Invalid five-minute row"
                );
            }
        }
    }
}
