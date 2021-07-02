package org.clsa;

/**
 * A Buffer to store market data
 * with a feature to cap the update rate per symbol
 */
public interface MarketDataBuffer {
    /**
     * put the market data into the buffer for processing before publishing
     * @param data
     */
    void put(MarketData data);

    /**
     * take the market data that is available for publishing; with the pre-defined update rate
     * blocking until there is market data available
     * @return
     * @throws InterruptedException
     */
    MarketData take() throws InterruptedException;
}
