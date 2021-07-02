package org.clsa;

import java.time.LocalDateTime;

public class MarketDataProcessor {

    private final MarketDataBuffer buffer;
    private final MarketDataRateLimiter rateLimiter;
    private Thread workerThread;

    public MarketDataProcessor(MarketDataBuffer buffer, MarketDataRateLimiter rateLimiter) {
        this.buffer = buffer;
        this.rateLimiter = rateLimiter;
        start();
    }

    public void onMessage(MarketData data){
        buffer.put(data);
    }

    public void publishMarketData(MarketData data){
        System.out.printf("%s sending data: %s%n", LocalDateTime.now(), data);
    }

    public void start(){
        workerThread = new Thread(() -> {
            while(true) {
                try {
                    MarketData data = buffer.take();
                    rateLimiter.acquire();
                    publishMarketData(data);
                } catch (InterruptedException e) {
                    System.out.println("stopped");
                    break;
                }
            }
        });

        workerThread.setDaemon(true);
        workerThread.start();

    }

    public void stop(){
        workerThread.interrupt();
    }
}
