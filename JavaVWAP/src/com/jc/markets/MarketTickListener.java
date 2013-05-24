package com.jc.markets;

import com.jc.markets.data.Tick;

public interface MarketTickListener {

	void onMarketTick(Tick tick);
}
