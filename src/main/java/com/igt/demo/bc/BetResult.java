package com.igt.demo.bc;

import java.math.*;

import lombok.*;

@Value
public class BetResult {

	public static final BetResult EMPTY = new BetResult(0, BigDecimal.ZERO, BigDecimal.ZERO);

	long unitCount;
	BigDecimal stake;
	BigDecimal maxReturn;

	public BetResult add(BetResult result) {
		return new BetResult(
			unitCount + result.unitCount,
			stake.add(result.stake),
			maxReturn.add(result.maxReturn)
		);
	}

	public boolean isEmpty() {
		return equals(EMPTY);
	}
}
