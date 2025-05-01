import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        boolean continueRunning = true;

        while (continueRunning) {
            try {
                // Get required inputs
                String progCode = input.getProgramCode();
                String turingInput = input.getTuringInput();
                boolean stepMode = input.getStepMode();

                // Setup and run the emulator
                TuringMachine tm = new TuringMachine(progCode.trim());

                Emulator emu = new Emulator(tm, turingInput.trim(), stepMode);
                emu.run();
                emu.printResult();

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Error in program format: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }

            // Ask if user wants to run another simulation
            System.out.println("\nDo you want to run another simulation?");
            System.out.println("1. Yes");
            System.out.println("2. No (Exit)");
            int choice = input.getIntInput("Enter choice (1 or 2): ", 1, 2);
            continueRunning = (choice == 1);
        }

        input.close();
        System.out.println("Program terminated.");
    }
}