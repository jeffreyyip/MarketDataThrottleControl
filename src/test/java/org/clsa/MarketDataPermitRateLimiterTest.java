package org.clsa;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataPermitRateLimiterTest {

    private final int messagePerSec = 2;
    private MarketDataRateLimiter rateLimiter;

    @Before
    public void setUp() {
        rateLimiter = new MarketDataPermitRateLimiter(messagePerSec);
    }

    @Test
    public void acquire() {

        List<Long> acquired = new ArrayList<>();
        for(int i = 0; i<11; i++){
            try {
                rateLimiter.acquire();
                acquired.add(System.currentTimeMillis());
                System.out.println(LocalDateTime.now());
                sleep(250);
            } catch (InterruptedException ignored) { }
        }

        int size = acquired.size();
        long diff = acquired.get(size-1) - acquired.get(0);

        System.out.printf(" size: %s, take: %s, avg; %s %n ", size, diff, diff/(size-1));

        assertTrue( diff/(size-1) >= 1000L/messagePerSec);

    }

    private void sleep(long millis){
        try{
            Thread.sleep(millis);
        }catch(InterruptedException ignored){}
    }
}