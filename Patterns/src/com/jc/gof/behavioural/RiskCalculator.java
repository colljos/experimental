package com.jc.gof.behavioural;

public interface RiskCalculator {

	void visitInterestRateSwap(InterestRateSwap irs);

	void visitCreditDefaultSwap(CreditDefaultSwap cds);

	Integer calculatePV();

}
