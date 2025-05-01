// Tape.java
import java.util.HashMap;
import java.util.Map;

public class Tape {
    private final Map<Integer, Character> cells = new HashMap<>();
    private final char blank = '_';

    public char read(int position) {
        return cells.getOrDefault(position, blank);
    }

    public void write(int position, char symbol) {
        if (symbol == blank) {
            cells.remove(position);
        } else {
            cells.put(position, symbol);
        }
    }

    /**
     * Returns the tape contents from head-15 to head+15 as a String.
     */
    public String window(int headPos) {
        StringBuilder sb = new StringBuilder();
        for (int i = headPos - 15; i <= headPos + 15; i++) {
            sb.append(read(i));
        }
        return sb.toString();
    }

    /**
     * Returns the minimal contiguous tape content between
     * first and last non-blank cell (used as "result").
     */
    public String getResult() {
        if (cells.isEmpty()) return "";

        int min = cells.keySet().stream().min(Integer::compareTo).orElse(0);
        int max = cells.keySet().stream().max(Integer::compareTo).orElse(0);

        // Always start from position 0 to preserve leading zeros
        min = Math.min(min, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = min; i <= max; i++) {
            sb.append(read(i));
        }
        return sb.toString();
    }
}
