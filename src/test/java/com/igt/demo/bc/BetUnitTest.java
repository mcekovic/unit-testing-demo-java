package com.igt.demo.bc;

import java.math.*;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class BetUnitTest {

	@Test
	void cumulativePriceIsCalculated() {
		var unit = new BetUnit(List.of(
			new BetLeg("2"),
			new BetLeg("3"),
			new BetLeg("4")
		), new BigDecimal("10"));

		var cumulativePrice = unit.getCumulativePrice();

		assertThat(cumulativePrice).isEqualByComparingTo("24");
	}

	@Test
	void maxReturnIsCalculated() {
		var unit = new BetUnit(List.of(
			new BetLeg("2")
		), new BigDecimal("10"));

		var maxReturn = unit.getMaxReturn();

		assertThat(maxReturn).isEqualByComparingTo("20");
	}

	@Test
	void betUnitMustHavePositiveStake() {
		assertThatThrownBy(
			() -> new BetUnit(List.of(new BetLeg("2")), new BigDecimal("0"))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void betUnitMustHaveLegs() {
		assertThatThrownBy(
			() -> new BetUnit(List.of(), new BigDecimal("1"))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void whenTwoLegsAreInterrelatedBetUnitIsInterrelated() {
		var unit = new BetUnit(List.of(
			new BetLeg("2", new IrDescriptor(11, 1, 1)),
			new BetLeg("3", new IrDescriptor(12, 1, 1)),
			new BetLeg("4", new IrDescriptor(21, 2, null))
		), new BigDecimal("10"));

		var interrelated = unit.isInterrelated();

		assertThat(interrelated).isTrue();
	}

	@Test
	void whenAllLegsAreMutuallyNotInterrelatedBetUnitIsNotInterrelated() {
		var unit = new BetUnit(List.of(
			new BetLeg("2"),
			new BetLeg("3"),
			new BetLeg("4")
		), new BigDecimal("10"));

		var interrelated = unit.isInterrelated();

		assertThat(interrelated).isFalse();
	}
}
