public class Emulator {
    private final TuringMachine tm;
    private final Tape tape = new Tape();
    private int head = 0;
    private State state;
    private long steps = 0;
    private final boolean stepMode;
    private boolean halted = false;

    public Emulator(TuringMachine tm, String input, boolean stepMode) {
        this.tm = tm;
        this.stepMode = stepMode;
        this.state = tm.getStartState();
        // initialize tape with input at positions 0..input.length-1
        for (int i = 0; i < input.length(); i++) {
            tape.write(i, input.charAt(i));
        }
        printInitialState();
    }

    public void run() {
        Transition t;
        while ((t = tm.getTransition(state, tape.read(head))) != null) {
            // perform move
            tape.write(head, t.getWriteSymbol());
            state = t.getNextState();
            head += (t.getDirection() == Direction.RIGHT ? 1 : -1);
            steps++;

            if (stepMode) {
                printStatus();
            }

            // Stop if we've reached state q2 (which appears to be your halt state)
            if (state.isFinalState()) {
                halted = true;
                break;
            }
        }
    }

    /**
     * Prints all transitions in the Turing machine for debugging
     */
    public void printInitialState() {
        System.out.println("=== Turing Machine Initial State ===");
        System.out.println("Start state: " + tm.getStartState().getId());
        System.out.println("Input: " + tape.getResult());
        System.out.println("Tape visual:");
        printTape();
        System.out.println();
        System.out.println("Total transitions: " + tm.getTransitions().size());
        System.out.println("Transitions:");

        // Sort transitions by state ID for readability
        for (Transition t : tm.getTransitions()) {
            System.out.println(t.toString());
        }
    }

    private void printStatus() {
        System.out.println("Step: " + steps);
        printTape();
        System.out.println();
    }

    /**
     * Prints the tape with the current head position and state
     */
    private void printTape() {
        // Create tape visual with pointer
        String tapeWindow = tape.window(head);
        StringBuilder pointerLine = new StringBuilder();

        // Add 15 spaces for alignment
        for (int i = 0; i < 15; i++) {
            pointerLine.append(" ");
        }

        // Add the pointer with state
        pointerLine.append("v (").append(state.getId()).append(")");

        System.out.println(pointerLine);
        System.out.println(tapeWindow);
    }

    public void printResult() {
        System.out.println("=== Computation Finished ===");
        System.out.println("Total steps: " + steps);
        System.out.println("Final state: " + state.getId());

        // Determine if input was accepted or rejected based on the symbol under the head
        char finalSymbol = tape.read(head);
        if (finalSymbol == '1' || halted) {
            System.out.println("Result: ACCEPTED");
        } else {
            System.out.println("Result: REJECTED");
        }

        System.out.println("Result tape: " + tape.getResult());
        System.out.println("Tape visual:");
        printTape();
    }
}