package systemConfig;

import java.io.IOException;
import java.util.Scanner;

public class SystemConfigCli {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Gather inputs
        System.out.print("Enter total tickets");
        int totalTickets = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter max ticket capacity");
        int maxTicketCapacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter ticket release rate for vendors (tickets per second): ");
        int ticketReleaseRate = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter customer retrieval rate (tickets per second): ");
        int customerRetrievalRate = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter number of vendors: ");
        int vendorCount = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter number of customers: ");
        int customerCount = Integer.parseInt(scanner.nextLine());

        Configuration configuration = new Configuration(totalTickets,maxTicketCapacity,ticketReleaseRate,customerRetrievalRate );

        // Save configuration to file
        String configFilePath = "CLI_PART_NEW/CLI_PART_NEW/config.json";
        try {
            configuration.saveToFile(configFilePath);
            System.out.println("Configuration saved successfully to " + configFilePath);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
            e.printStackTrace();
        }
        // Initialize
        TicketPool ticketPool = new TicketPool(maxTicketCapacity, totalTickets);
        TicketSystem ticketSystem = new TicketSystem(ticketPool, ticketReleaseRate, customerRetrievalRate, vendorCount, customerCount);

        // Start operations
        System.out.println("Starting the ticketing system...");
        ticketSystem.startOperations();

        // Stop on user command
        System.out.println("Type 'stop' to end the system:");
        while (!scanner.nextLine().equalsIgnoreCase("stop")) {
            System.out.println("Invalid command. Type 'stop' to end.");
        }

        ticketSystem.stopOperations();
        scanner.close();
    }


}



