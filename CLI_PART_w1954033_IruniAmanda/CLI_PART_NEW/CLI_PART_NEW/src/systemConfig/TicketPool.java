package systemConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final int totalTicketCapacity;
    private final int maxTicketCapacity;
    private final Lock lock = new ReentrantLock();
    private final Condition ticketsAvailable = lock.newCondition();
    private int ticketsAdded = 0;
    private int ticketsSold = 0;
    private boolean allTicketsSold = false; // Tracks if all tickets are sold

    public TicketPool(int maxTicketCapacity, int totalTicketCapacity) {
        this.totalTicketCapacity = totalTicketCapacity;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    private void log(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " - " + message);
    }

    public boolean addTickets(int tickets, String vendorName) {
        lock.lock();
        try {
            if (allTicketsSold) {
                return false; // All tickets are sold; no action required
            }

            int currentTicketsInPool = ticketsAdded - ticketsSold;
            if (currentTicketsInPool >= maxTicketCapacity) {
                log("Vendor queue is on hold: Ticket pool is at maximum capacity. Waiting for customers to clear tickets.");
                return false; // Pool is at max capacity; no action required
            }

            int remainingCapacity = totalTicketCapacity - ticketsAdded;
            if (remainingCapacity <= 0) {
                return false; // Max capacity reached; vendor cannot add tickets
            }

            int ticketsToAdd = Math.min(tickets, Math.min(remainingCapacity, maxTicketCapacity - currentTicketsInPool));
            ticketsAdded += ticketsToAdd;

            log(vendorName + " added " + ticketsToAdd + " tickets. Total Tickets Added: " + ticketsAdded +
                    " | Remaining Capacity for Vendors: " + (totalTicketCapacity - ticketsAdded));

            ticketsAvailable.signalAll(); // Notify waiting customers
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean buyTickets(int ticketsToBuy, String customerName) {
        lock.lock();
        try {
            if (allTicketsSold) {
                return false; // No action required; tickets are sold out
            }

            while (ticketsAdded - ticketsSold <= 0 && !allTicketsSold) {
                ticketsAvailable.await(); // Wait until tickets are available
            }

            if (allTicketsSold) {
                return false; // All tickets are sold; no further actions allowed
            }

            int availableTickets = ticketsAdded - ticketsSold;
            int ticketsBought = Math.min(ticketsToBuy, availableTickets);
            ticketsSold += ticketsBought;

            log(customerName + " bought " + ticketsBought + " tickets. Total Sold Tickets: " + ticketsSold +
                    " | Tickets Remaining for Customers: " + (ticketsAdded - ticketsSold));

            if (ticketsSold == totalTicketCapacity) {
                allTicketsSold = true;
                log("All tickets are sold out!");
                ticketsAvailable.signalAll(); // Notify all threads to stop
            }

            return ticketsBought > 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean isAllTicketsSold() {
        return allTicketsSold;
    }
}
