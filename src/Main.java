import quiz5.UserInteractionManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a dummy communication manager for testing
        DummyCommunicationManager dummyCommManager = new DummyCommunicationManager();

        // Create a user interaction manager with the dummy communication manager
        UserInteractionManager userInteractionManager = new UserInteractionManager(dummyCommManager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("User: ");
            String userMessage = scanner.nextLine();

            if (userMessage.equalsIgnoreCase("exit")) {
                break;
            }

            userInteractionManager.sendUserMessage(userMessage);
        }

        scanner.close();
    }
}
