package org.clsa;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MarketDataPermitRateLimiter implements MarketDataRateLimiter{

    private final int messagePerSec;
    private final DelayQueue<PermitEvent> permitEvents = new DelayQueue<>();

    public MarketDataPermitRateLimiter(int messagePerSec) {
        this.messagePerSec = messagePerSec;
        setupPermit();
    }

    @Override
    public void acquire() throws InterruptedException {

        permitEvents.take();
        permitEvents.put(new PermitEvent(System.currentTimeMillis() + 1000L));
    }

    /**
     * setup initial permit
     */
    private void setupPermit(){

        for (int i = 0; i < messagePerSec; i++){
            permitEvents.put(new PermitEvent(System.currentTimeMillis()));
        }
    }


    private static class PermitEvent implements Delayed {
        private final long timeAvailable;

        public PermitEvent(long timeAvailable) {
            this.timeAvailable = timeAvailable;
        }


        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert((timeAvailable-System.currentTimeMillis()), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(timeAvailable, ((PermitEvent)o).timeAvailable);
        }
    }
}
