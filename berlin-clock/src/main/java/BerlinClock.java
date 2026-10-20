
public class BerlinClock {

    // Feature 1: Digital time -> Berlin Clock
    public String convertToBerlinClock(String time) {
        if (time == null || time.equals("null") || time.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty or NULL not allowed");
        }

        String[] parts = time.split(":", -1);

        if (parts.length != 3
                || !parts[0].matches("\\d{2}")
                || !parts[1].matches("\\d{2}")
                || !parts[2].matches("\\d{2}")) {
            throw new IllegalArgumentException("Time format is incorrect, allowed format- HH:mm:ss");
        }

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        if (hours > 23 || minutes > 59 || seconds > 59) {
            throw new IllegalArgumentException("Invalid time");
        }

        return (seconds % 2 == 0 ? "Y" : "O")
                + row(hours / 5, 'R')
                + row(hours % 5, 'R')
                + minuteRow(minutes / 5)
                + row(minutes % 5, 'Y');
    }

    private String row(int lightsOn, char color) {
        StringBuilder result = new StringBuilder();

        int total = 4;
        for (int i = 0; i < total; i++) {
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

    // Feature 2: Berlin Clock -> Digital time
    public String toDigitalTime(String berlinTime) {
        if (berlinTime == null || berlinTime.equals("null") || berlinTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty or NULL not allowed");
        }

        if (berlinTime.length() != 24) {
            throw new IllegalArgumentException(
                    "Berlin time must contain 24 characters");
        }

        String secondsRow = berlinTime.substring(0, 1);
        String fiveHourRow = berlinTime.substring(1, 5);
        String oneHourRow = berlinTime.substring(5, 9);
        String fiveMinuteRow = berlinTime.substring(9, 20);
        String oneMinuteRow = berlinTime.substring(20, 24);

        if (!secondsRow.matches("[YO]")
                || !fiveHourRow.matches("R{0,4}O{0,4}")
                || !oneHourRow.matches("R{0,4}O{0,4}")
                || !fiveMinuteRow.matches("[YRO]{11}")
                || !oneMinuteRow.matches("Y{0,4}O{0,4}")) {
            throw new IllegalArgumentException("Invalid Berlin time");
        }

        for (int i = 0; i < fiveMinuteRow.length(); i++) {
            char light = fiveMinuteRow.charAt(i);
            char expected = (i + 1) % 3 == 0 ? 'R' : 'Y';

            if (light == 'O') {
                for (int j = i + 1; j < fiveMinuteRow.length(); j++) {
                    if (fiveMinuteRow.charAt(j) != 'O') {
                        throw new IllegalArgumentException("Invalid minute row");
                    }
                }
                break;
            }

            if (light != expected) {
                throw new IllegalArgumentException("Invalid minute row");
            }
        }

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

    public static void main(String[] args) {
        BerlinClock clock = new BerlinClock();

        if (args.length != 2) {
            System.out.println("Usage:");
            System.out.println("Feature1: Digital to Berlin: java BerlinClock toBerlin 13:17:01");
            System.out.println("OR");
            System.out.println("Feature2: Berlin to Digital: java BerlinClock toDigital ORRO...");
            return;
        }

        if (args[0].equals("toBerlin")) {
            System.out.println(clock.convertToBerlinClock(args[1]));
        } else if (args[0].equals("toDigital")) {
            System.out.println(clock.toDigitalTime(args[1]));
        } else {
            throw new IllegalArgumentException("Unknown feature given");
        }
    }
}
