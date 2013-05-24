package com.jc.gof.behavioural;

public interface InterestRateSwap {

	void accept(RiskCalculator riskCalculatorVisitor);

	Integer pv();
}
