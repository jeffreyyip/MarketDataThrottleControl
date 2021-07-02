package org.clsa;

import java.util.*;
import java.util.concurrent.*;

public class MarketDataSecCapBuffer implements MarketDataBuffer{

    private final ConcurrentMap<String, MarketData> lastData = new ConcurrentHashMap<>();
    private final BlockingQueue<MarketData> outBounceQueue = new LinkedBlockingQueue<>();
    private final int cappedMktDataUpdatePerSec;

    public MarketDataSecCapBuffer(int cappedMktDataUpdatePerSec) {
        this.cappedMktDataUpdatePerSec = cappedMktDataUpdatePerSec;
    }

    /**
     * process the market data and capped the update rate to pre-defined second;
     * put it into outbound queue if it is comply with the updated rate
     *
     * It is assume that the single thread is making this call
     * @param data
     */
    @Override
    public void put(MarketData data){

        MarketData sentData = lastData.get(data.getSymbol());

        if (Objects.isNull(sentData) || sentData.getUpdateTime() + 1000L/cappedMktDataUpdatePerSec <= data.getUpdateTime()){
            lastData.put(data.getSymbol(), data);
            outBounceQueue.add(data);
        }
    }

    @Override
    public MarketData take() throws InterruptedException {
        return outBounceQueue.take();
    }

}
