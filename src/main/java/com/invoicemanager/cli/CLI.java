package main.java.com.invoicemanager.cli;

import java.util.LinkedHashMap;
import java.util.Map;

import main.java.com.invoicemanager.util.InputHelper;

public class CLI {
    private final Map<Integer, Command> commandMap = new LinkedHashMap<>();

    public void addCLIOption(int option, String promptText, Runnable handler) {
        commandMap.put(option, new Command(promptText, handler));
    }

    public void run() {

        while (true) {
            printMenu();
            int choice = InputHelper.promptInt("Enter your choice: ");
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
