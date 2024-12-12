package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.domain.SystemConfig;
import com.example.ticketingsystem.service.SystemConfigService;
import com.example.ticketingsystem.service.impl.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@CrossOrigin(origins = "*")
public class TicketSystemController {

    private TicketPool ticketPool;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicBoolean isVendorRunning = new AtomicBoolean(false);
    private final AtomicBoolean isCustomerRunning = new AtomicBoolean(false);
    private final List<String> logs = new ArrayList<>();
    private int vendorCount;
    private int customerCount;
    private int customerRetrievalRate;
    private int vendorRetrievalRate;

    @Autowired
    private SystemConfigService systemConfigService;

    @PostMapping("/initialize")
    public String initialize() {
        SystemConfig config = systemConfigService.getSystemConfigs();
        int maxTicketCapacity = config.getMaxCapacity();
        int totalTicketCapacity = config.getTotalTickets();
        vendorCount = config.getVendor_count();
        customerCount = config.getCustomer_count();
        customerRetrievalRate = config.getCustomerRetrievalRate();
        vendorRetrievalRate = config.getVendorId();

        this.ticketPool = new TicketPool(maxTicketCapacity, totalTicketCapacity);
        logs.add("Ticket pool initialized with maxTicketCapacity=" + maxTicketCapacity + ", totalTicketCapacity=" + totalTicketCapacity);
        logs.add("Vendor count=" + vendorCount + ", Customer count=" + customerCount);
        return "Initialization complete";
    }

    @PostMapping("/vendor/start")
    public List<String> startVendorThread() {
        if (ticketPool == null) {
            logs.add("Error: Ticket pool not initialized.");
            return logs;
        }
        if (isVendorRunning.compareAndSet(false, true)) {
            for (int i = 1; i <= vendorCount; i++) {
                int vendorId = i;
                executorService.submit(() -> {
                    String vendorName = "Vendor-" + vendorId;
                    while (!ticketPool.isAllTicketsSold() && isVendorRunning.get()) {
                        logs.add(vendorName + " is attempting to add tickets...");
                        boolean success = ticketPool.addTickets(vendorRetrievalRate, vendorName, logs);
                        if (!success) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                logs.add(vendorName + " was interrupted.");
                            }
                        }
                    }
                    logs.add(vendorName + " has stopped.");
                });
            }
            logs.add("Vendor threads started.");
        } else {
            logs.add("Vendor threads are already running.");
        }
        return logs;
    }

    @PostMapping("/vendor/stop")
    public List<String> stopVendorThread() {
        if (isVendorRunning.compareAndSet(true, false)) {
            logs.add("Vendor threads stopped.");
        } else {
            logs.add("Vendor threads are not running.");
        }
        return logs;
    }

    @PostMapping("/customer/start")
    public List<String> startCustomerThread() {
        if (ticketPool == null) {
            logs.add("Error: Ticket pool not initialized.");
            return logs;
        }
        if (isCustomerRunning.compareAndSet(false, true)) {
            for (int i = 1; i <= customerCount; i++) {
                int customerId = i;
                executorService.submit(() -> {
                    String customerName = "Customer-" + customerId;
                    while (!ticketPool.isAllTicketsSold() && isCustomerRunning.get()) {
                        logs.add(customerName + " is attempting to buy tickets...");
                        boolean success = ticketPool.buyTickets(customerRetrievalRate, customerName, logs);
                        if (!success) {
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            logs.add(customerName + " was interrupted.");
                        }
                    }
                    logs.add(customerName + " has stopped.");
                });
            }
            logs.add("Customer threads started.");
        } else {
            logs.add("Customer threads are already running.");
        }
        return logs;
    }

    @PostMapping("/customer/stop")
    public List<String> stopCustomerThread() {
        if (isCustomerRunning.compareAndSet(true, false)) {
            logs.add("Customer threads stopped.");
        } else {
            logs.add("Customer threads are not running.");
        }
        return logs;
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return logs;
    }

}