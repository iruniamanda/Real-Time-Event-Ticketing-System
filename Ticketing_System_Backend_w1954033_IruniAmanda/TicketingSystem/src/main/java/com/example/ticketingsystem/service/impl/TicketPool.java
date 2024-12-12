package com.example.ticketingsystem.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public  class TicketPool {
    private final int totalTicketCapacity;
    private final int maxTicketCapacity;
    private final Lock lock = new ReentrantLock();
    private final Condition ticketsAvailable = lock.newCondition();
    private int ticketsAdded = 0;
    private int ticketsSold = 0;
    private boolean allTicketsSold = false;

    public TicketPool(int maxTicketCapacity, int totalTicketCapacity) {
        this.totalTicketCapacity = totalTicketCapacity;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    private void log(String message, List<String> logs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logEntry = sdf.format(new Date()) + " - " + message;
        logs.add(logEntry);
        System.out.println(logEntry);
    }

    public boolean addTickets(int tickets, String vendorName, List<String> logs) {
        lock.lock();
        try {
            if (allTicketsSold) {
                return false;
            }

            int currentTicketsInPool = ticketsAdded - ticketsSold;
            if (currentTicketsInPool >= maxTicketCapacity) {
                log("Vendor queue is on hold: Ticket pool is at maximum capacity. Waiting for customers to clear tickets.", logs);
                return false;
            }

            int remainingCapacity = totalTicketCapacity - ticketsAdded;
            if (remainingCapacity <= 0) {
                return false;
            }

            int ticketsToAdd = Math.min(tickets, Math.min(remainingCapacity, maxTicketCapacity - currentTicketsInPool));
            ticketsAdded += ticketsToAdd;

            log(vendorName + " added " + ticketsToAdd + " tickets. Total Tickets Added: " + ticketsAdded +
                    " | Remaining Capacity for Vendors: " + (totalTicketCapacity - ticketsAdded), logs);

            ticketsAvailable.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean buyTickets(int ticketsToBuy, String customerName, List<String> logs) {
        lock.lock();
        try {
            if (allTicketsSold) {
                return false;
            }

            while (ticketsAdded - ticketsSold <= 0 && !allTicketsSold) {
                ticketsAvailable.await();
            }

            if (allTicketsSold) {
                return false;
            }

            int availableTickets = ticketsAdded - ticketsSold;
            int ticketsBought = Math.min(ticketsToBuy, availableTickets);
            ticketsSold += ticketsBought;

            log(customerName + " bought " + ticketsBought + " tickets. Total Sold Tickets: " + ticketsSold +
                    " | Tickets Remaining for Customers: " + (ticketsAdded - ticketsSold), logs);

            if (ticketsSold == totalTicketCapacity) {
                allTicketsSold = true;
                log("All tickets are sold out!", logs);
                ticketsAvailable.signalAll();
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

