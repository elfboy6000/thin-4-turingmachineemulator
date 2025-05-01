// Input.java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Input {
    private final Scanner scanner;

    public Input() {
        scanner = new Scanner(System.in);
    }

    public void close() {
        scanner.close();
    }

    public String getProgramCode() throws IOException {
        System.out.println("How would you like to input the program?");
        System.out.println("1. Enter program code directly");
        System.out.println("2. Read from file");
        int choice = getIntInput("Enter choice (1 or 2): ", 1, 2);

        if (choice == 1) {
            System.out.print("Enter program code: ");
            return scanner.nextLine().trim();
        } else {
            System.out.print("Enter program file path: ");
            String filePath = scanner.nextLine().trim();
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
    }

    public String getTuringInput() {
        System.out.println("How would you like to provide input?");
        System.out.println("1. Binary input");
        System.out.println("2. Decimal input (will be converted to binary)");
        int choice = getIntInput("Enter choice (1 or 2): ", 1, 2);

        if (choice == 1) {
            System.out.print("Enter binary input: ");
            return scanner.nextLine().trim();
        } else {
            System.out.print("Enter decimal input: ");
            try {
                int decimal = Integer.parseInt(scanner.nextLine().trim());
                String binary = Integer.toBinaryString(decimal);
                System.out.println("Converted to binary: " + binary);
                return binary;
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number");
                return getTuringInput(); // Retry
            }
        }
    }

    public boolean getStepMode() {
        System.out.println("Select execution mode:");
        System.out.println("1. Run mode (outputs final result only)");
        System.out.println("2. Step mode (shows each step)");
        int choice = getIntInput("Enter choice (1 or 2): ", 1, 2);
        return choice == 2;
    }

    public int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}