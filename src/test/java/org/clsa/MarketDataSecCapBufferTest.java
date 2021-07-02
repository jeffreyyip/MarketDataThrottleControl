package org.clsa;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MarketDataSecCapBufferTest {


    private final int cappedMktDataUpdatePerSec = 2;
    private MarketDataBuffer buffer;

    @Before
    public void setUp() {
        buffer = new MarketDataSecCapBuffer(cappedMktDataUpdatePerSec);
    }


    @Test
    public void take() {
        List<MarketData> sent = new ArrayList<>();

        Thread task = new Thread( () -> {
            while(true) {
                try {
                    MarketData data = buffer.take();
                    sent.add(data);
                    System.out.println("sent: " + data);
                } catch (InterruptedException ignored) {
                    System.out.println("interrupted");
                    break;
                }
            }
        }
        );
        task.start();

        for (int i = 0; i < 10; i++) {
            sendWithDelay(buffer, mock("HSBC"), 200);
        }


        sleep(4000);
        task.interrupt();

        for (int i = 1; i < sent.size(); i++){
            assertTrue((sent.get(i).getUpdateTime()-sent.get(i-1).getUpdateTime() >= 1000L/cappedMktDataUpdatePerSec));

        }


    }

    private static MarketData mock(String symbol){
        return new MarketData(symbol, BigDecimal.valueOf(40),BigDecimal.valueOf(41),BigDecimal.valueOf(40), System.currentTimeMillis());
    }

    private static void sendWithDelay(MarketDataBuffer buffer, MarketData data, long millis){

        try {
            buffer.put(data);
            Thread.sleep(millis);

        } catch (InterruptedException ignored) { }
    }

    private static void sleep(long millis){

        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) { }
    }

}