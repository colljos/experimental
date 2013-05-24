package com.jc.gof.behavioural;

public interface CreditDefaultSwap {

	void accept(RiskCalculator riskCalculatorVisitor);

	Integer pv();
}
