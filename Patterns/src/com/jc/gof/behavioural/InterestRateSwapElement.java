package com.jc.gof.behavioural;

public class InterestRateSwapElement implements InterestRateSwap {

	private RiskCalculator riskCalculatorVisitor;

	@Override
	public void accept(RiskCalculator riskCalculatorVisitor) {
		this.riskCalculatorVisitor = riskCalculatorVisitor;
	}

	@Override
	public Integer pv() {
		return riskCalculatorVisitor.calculatePV();
	}

}
