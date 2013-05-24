package com.jc.gof.behavioural;

public class RiskCalculatorVisitor implements RiskCalculator {

	@Override
	public void visitInterestRateSwap(InterestRateSwap irs) {
		irs.accept(this);
	}

	@Override
	public void visitCreditDefaultSwap(CreditDefaultSwap cds) {
		cds.accept(this);		
	}
	
	public Integer calculatePV() {
		return 1;
	}
}
