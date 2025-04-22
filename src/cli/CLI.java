package cli;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    private final Map<Integer, Command> commandMap = new LinkedHashMap<>();

    public void addCLIOption(int option, String promptText, Runnable handler) {
        commandMap.put(option, new Command(promptText, handler));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            Command command = commandMap.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid choice; please try again!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==== MENU ====");

        for (Map.Entry<Integer, Command> entry : commandMap.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getCliPrompt());
        }
    }
}
