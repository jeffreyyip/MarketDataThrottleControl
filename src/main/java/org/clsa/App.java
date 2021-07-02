package org.clsa;

import java.math.BigDecimal;

/**
 * Client application to call MarketDataProcessor
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int messagePerSec = 5;
        int cappedMktDataUpdatePerSec = 1;

        MarketDataBuffer buffer = new MarketDataSecCapBuffer(cappedMktDataUpdatePerSec);
        MarketDataRateLimiter rateLimiter = new MarketDataPermitRateLimiter(messagePerSec);

        MarketDataProcessor processor = new MarketDataProcessor(buffer, rateLimiter);

        for(int i = 0; i < 10; i++) {

            processor.onMessage( mock("HSBC") );
            processor.onMessage( mock("HKT") );
            processor.onMessage( mock("CKH") );
            processor.onMessage( mock("WHL") );
            processor.onMessage( mock("PCCW") );

            sleep(350);
        }

    }

    private static MarketData mock(String symbol){
        return new MarketData(symbol, BigDecimal.valueOf(40),BigDecimal.valueOf(41),BigDecimal.valueOf(40), System.currentTimeMillis());
    }

    private static void sendWithDelay(MarketDataProcessor processor, MarketData data){

        try {
            Thread.sleep(350);
            processor.onMessage(data);
        } catch (InterruptedException ignored) { }
    }

    private static void sleep(long millis){

        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) { }
    }
}
