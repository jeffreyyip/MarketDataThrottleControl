package org.clsa;

/**
 * A rate limiter to throttle the market data message in a pre-defined rate
 * Similar to acquire() from Google Guava RateLimiter
 */
public interface MarketDataRateLimiter {

    /**
     * acquire a permit throttled in a pre-defined rate
     * blocking until the request can be granted
     * @throws InterruptedException
     */
    void acquire() throws InterruptedException;
}
