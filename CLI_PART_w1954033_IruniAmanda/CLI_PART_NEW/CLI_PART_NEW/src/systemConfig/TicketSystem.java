package systemConfig;

public class TicketSystem {
    private final TicketPool ticketPool;
    private final int vendorTicketReleaseRate;
    private final int customerTicketBuyRate;
    private final int vendorCount;
    private final int customerCount;

    public TicketSystem(TicketPool ticketPool, int vendorTicketReleaseRate, int customerTicketBuyRate,
                        int vendorCount, int customerCount) {
        this.ticketPool = ticketPool;
        this.vendorTicketReleaseRate = vendorTicketReleaseRate;
        this.customerTicketBuyRate = customerTicketBuyRate;
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
    }

    public void startVendorThread(int vendorId) {
        Thread vendorThread = new Thread(() -> {
            String vendorName = "Vendor-" + vendorId;
            while (!ticketPool.isAllTicketsSold()) {
                boolean success = ticketPool.addTickets(vendorTicketReleaseRate, vendorName);
                if (!success) {
                    try {
                        Thread.sleep(1000); // Retry after 1 second
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }

                try {
                    Thread.sleep(1000); // Vendors add tickets every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        vendorThread.start();
    }

    public void startCustomerThread(int customerId) {
        Thread customerThread = new Thread(() -> {
            String customerName = "Customer-" + customerId;
            while (!ticketPool.isAllTicketsSold()) {
                boolean success = ticketPool.buyTickets(customerTicketBuyRate, customerName);
                if (!success) break;

                try {
                    Thread.sleep(1000); // Customers buy tickets every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        customerThread.start();
    }

    public void startOperations() {
        for (int i = 1; i <= vendorCount; i++) {
            startVendorThread(i);
        }

        for (int i = 1; i <= customerCount; i++) {
            startCustomerThread(i);
        }
    }

    public void stopOperations() {
        System.out.println("System operation stopped.");
    }
}