
public class BerlinClock {

    public String convertToBerlinClock(String time) {
        String[] parts = time.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

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
}
