package com.jc;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import com.jc.markets.MarketTicker;
import com.jc.markets.data.Tick;
import com.jc.processor.MarketTickProcessor;
import com.jc.processor.VWAPCalculatorController;
import com.jc.vwap.VWAPCalculator;

public class VWAPCalculatorMain {

	private void bootstrap() {
		
		List<String> instrumentList1 = Arrays.asList("ACCL","ACIW","ATVI","ADBE","AMSWA","BLKB","BMC","EPAY","BLIN","BSFT","BVSN","CA","CDNS","CYOU","CHKP","CNIT");
		List<String> instrumentList2 = Arrays.asList("ACCL","ACIW","ATVI","ADBE","AMSWA","BLKB","BMC","EPAY","BLIN","BSFT","BVSN");
		List<String> instrumentList3 = Arrays.asList("ACCL","ACIW","ATVI","ADBE","AMSWA");
		
		MarketTicker slowMarket = MarketTicker.create("MARKET A", instrumentList1, 1000);
		MarketTicker averMarket = MarketTicker.create("MARKET B", instrumentList2, 100);
		MarketTicker fastMarket = MarketTicker.create("MARKET C", instrumentList3, 10);
			
		TransferQueue<Tick> slowUpdateQ = new LinkedTransferQueue<Tick>();
		TransferQueue<Tick> averUpdateQ = new LinkedTransferQueue<Tick>();
		TransferQueue<Tick> fastUpdateQ = new LinkedTransferQueue<Tick>();
		
		MarketTickProcessor slowMarketListener = new MarketTickProcessor(slowUpdateQ);
		MarketTickProcessor averMarketListener = new MarketTickProcessor(averUpdateQ);
		MarketTickProcessor fastMarketListener = new MarketTickProcessor(fastUpdateQ);
		
		slowMarket.subscribe(slowMarketListener);
		averMarket.subscribe(averMarketListener);
		fastMarket.subscribe(fastMarketListener);
		
		ConcurrentHashMap<String,VWAPCalculator> calculators = new ConcurrentHashMap<String, VWAPCalculator>(instrumentList1.size());		
		VWAPCalculatorController slowVwapController = new VWAPCalculatorController(slowUpdateQ, calculators);
		slowVwapController.start();
		
		VWAPCalculatorController averVwapController = new VWAPCalculatorController(averUpdateQ, calculators);
		averVwapController.start();

		VWAPCalculatorController fastVwapController = new VWAPCalculatorController(fastUpdateQ, calculators);
		fastVwapController.start();

		slowMarket.init();	
		averMarket.init();
		fastMarket.init();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new VWAPCalculatorMain().bootstrap();
	}

}
