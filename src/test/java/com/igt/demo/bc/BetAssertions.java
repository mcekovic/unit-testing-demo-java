package com.igt.demo.bc;

import java.math.*;

import org.assertj.core.api.*;

public abstract class BetAssertions {

	public static BetResultAssert assertThatBetResult(BetResult betResult) {
		return new BetResultAssert(betResult);
	}

	public static class BetResultAssert extends AbstractAssert<BetResultAssert, BetResult> {

		public BetResultAssert(BetResult betResult) {
			super(betResult, BetResultAssert.class);
		}

		public BetResultAssert hasUnitCount(long unitCount) {
			isNotNull();
			if (actual.getUnitCount() != unitCount)
				failWithMessage("Expected betResult to have unitCount %s but was %s", unitCount, actual.getUnitCount());
			return this;
		}

		public BetResultAssert hasStake(String stake) {
			isNotNull();
			if (actual.getStake().compareTo(new BigDecimal(stake)) != 0)
				failWithMessage("Expected betResult to have stake %s but was %s", stake, actual.getStake());
			return this;
		}

		public BetResultAssert hasMaxReturn(String maxReturn) {
			isNotNull();
			if (actual.getMaxReturn().compareTo(new BigDecimal(maxReturn)) != 0)
				failWithMessage("Expected betResult to have maxReturn %s but was %s", maxReturn, actual.getMaxReturn());
			return this;
		}

		public BetResultAssert isEmpty() {
			isNotNull();
			if (!actual.equals(BetResult.EMPTY))
				failWithMessage("Expected betResult %s to be empty", actual);
			return this;
		}
	}
}
