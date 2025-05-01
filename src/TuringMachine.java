import java.util.ArrayList;

public class TuringMachine {
    private final ArrayList<Transition> transitions = new ArrayList<>();
    private final State startState = new State("q1");
    private final String code;
    private int index;

    public TuringMachine(String programCode) {
        this.code = programCode.replaceAll("\\s", "");
        if (code.isEmpty()) {
            throw new IllegalArgumentException("Program code cannot be empty");
        }

        if (!code.contains("0") || !code.contains("1")) {
            throw new IllegalArgumentException("Invalid program format: must contain both 0s and 1s");
        }

        this.index = 0;
        decode();
    }

    private void decode() {
        int n = code.length();
        // Skip any leading '1's before starting to decode
        while (index < n && code.charAt(index) == '1') {
            index++;
        }

        while (index < n) {
            try {
                int currentStateCode = readUnary();
                int readSymbolCode = readUnary() - 1;
                int nextStateCode = readUnary();
                int writeSymbolCode = readUnary() - 1;
                int directionCode = readUnary() - 1;

                System.err.println("directionCode = " + directionCode);

                Direction dir = switch (directionCode) {
                    case 0 -> Direction.LEFT;
                    case 1 -> Direction.RIGHT;
                    default -> throw new IllegalArgumentException("Invalid direction number: " + directionCode);
                };

                char readSymbol = convertCodeToSymbol(readSymbolCode);
                char writeSymbol = convertCodeToSymbol(writeSymbolCode);

                State currentState = new State("q" + currentStateCode);
                State nextState = new State("q" + nextStateCode);
                transitions.add(new Transition(
                        currentState, readSymbol,  nextState, writeSymbol, dir)
                );

                // skip any extra '1's between transitions
                while (index < n && code.charAt(index) == '1') {
                    index++;
                }
            } catch (Exception e) {
                System.err.println("Error at position " + index + ": " + e.getMessage());
                System.err.println("Code snippet: " + codeSnippet(index));
                throw new IllegalArgumentException("Error parsing at position " + index, e);
            }
        }
    }

    private char convertCodeToSymbol(int code) {
        if (code > ('z' - 'a' + 3)) {
            throw new IllegalArgumentException("Code exceeds valid range: " + code);
        }
        return switch (code) {
            case 0 -> '0';
            case 1 -> '1';
            case 2 -> '_';
            default -> (char) ('a' + (code - 3));
        };
    }

    private String codeSnippet(int pos) {
        int start = Math.max(0, pos - 10);
        int end = Math.min(code.length(), pos + 10);
        StringBuilder sb = new StringBuilder(code.substring(start, end));
        if (pos >= start && pos < end) {
            sb.insert(pos - start, "→");
        }
        return sb.toString();
    }

    private int readUnary() {
        int count = 0;
        int n = code.length();

        if (index >= n) {
            throw new IllegalArgumentException("Unexpected end of input");
        }

        // Count consecutive zeros
        while (index < n && code.charAt(index) == '0') {
            count++;
            index++;
        }

        // We must see a '1' after zeros (unless at the end)
        if (index < n) {
            if (code.charAt(index) != '1') {
                throw new IllegalArgumentException("Expected '1' after zeros, but found '" + code.charAt(index) + "'");
            }
            index++; // Skip the terminating '1'
        }

        return count;
    }

    public Transition getTransition(State state, char readSymbol) {
        for (Transition transition : transitions) {
            if (transition.getCurrentState().getId().equals(state.getId()) &&
                    transition.getReadSymbol() == readSymbol) {
                return transition;
            }
        }
        return null;
    }

    public State getStartState() {
        return startState;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }
}