package com.jc.gof.behavioural;

import static org.junit.Assert.*;

import org.junit.Test;

public class VisitorTest {

	@Test
	public void testVisitor() {
		
		InterestRateSwap irs = new InterestRateSwapElement();
		CreditDefaultSwap cds = new CreditDefaultSwapElement();
		
		RiskCalculator calculator = new RiskCalculatorVisitor();
		calculator.visitInterestRateSwap(irs);
		calculator.visitCreditDefaultSwap(cds);
		
		assertEquals(Integer.valueOf(1), irs.pv());
		assertEquals(Integer.valueOf(1), cds.pv());
	}

}
