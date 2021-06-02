package com.igt.demo.bc;

import java.math.*;
import java.util.*;

import static java.util.Objects.*;

public class BetUnit {

	private final BigDecimal stake;
	private final List<BetLeg> legs;

	public BetUnit(List<BetLeg> legs, BigDecimal stake) {
		this.stake = requireNonNull(stake, "stake");
		this.legs = requireNonNull(legs, "legs");
		if (stake.signum() <= 0)
			throw new IllegalArgumentException("BetUnit must have positive stake");
		if (legs.isEmpty())
			throw new IllegalArgumentException("BetUnit must have legs");
	}

	public BigDecimal getCumulativePrice() {
		return legs.stream()
			.map(BetLeg::getPrice)
			.reduce(BigDecimal.ONE, BigDecimal::multiply);
	}

	public BigDecimal getMaxReturn() {
		return stake.multiply(getCumulativePrice());
	}

	public BetResult getResult() {
		return new BetResult(1L, stake, getMaxReturn());
	}

	public boolean isInterrelated() {
		int count = legs.size();
		for (int i = 0; i < count - 1; i++) {
			var descriptor1 = legs.get(i).getDescriptor();
			for (int j = i + 1; j < count; j++) {
				var descriptor2 = legs.get(j).getDescriptor();
				if (descriptor1.isInterrelated(descriptor2))
					return true;
			}
		}
		return false;
	}
}
