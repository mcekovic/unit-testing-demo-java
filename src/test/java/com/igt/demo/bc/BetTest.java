package com.igt.demo.bc;

import java.util.*;
import java.util.stream.*;

import com.igt.demo.bc.bettypes.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static com.igt.demo.bc.BetAssertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetTest {

	@Test
	void singleBetIsCalculated() {
		var bet = new Bet(Accumulator.INSTANCE, "10", List.of(new BetLeg("2")));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(1L)
			.hasStake("10")
			.hasMaxReturn("20");
	}

	@Test
	void singleBetIsCalculated_Mockist() {
		var legs = List.of(new BetLeg("2"));
		var betType = mock(BetType.class);
		when(betType.combinations(Mockito.<BetLeg>anyList())).thenReturn(Stream.of(legs));
		var bet = new Bet(betType, "10", legs);

		var result = bet.calculate();

		verify(betType).combinations(legs);
		assertThatBetResult(result)
			.hasUnitCount(1L)
			.hasStake("10")
			.hasMaxReturn("20");
	}

	@Test
	void doubleBetIsCalculated() {
		var bet = new Bet(Accumulator.INSTANCE, "5", List.of(
			new BetLeg("2"),
			new BetLeg("3")
		));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(1L)
			.hasStake("5")
			.hasMaxReturn("30");
	}

	@Test
	void doublesBetIsCalculated() {
		var bet = new Bet(new Perms(2), "1", List.of(
			new BetLeg("2"),
			new BetLeg("3"),
			new BetLeg("4")
		));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(3L)
			.hasStake("3")
			.hasMaxReturn("26");
	}

	@Test
	void patentBetIsCalculated() {
		var bet = new Bet(FullCover.withoutSingles(), "1", List.of(
			new BetLeg("2"),
			new BetLeg("3"),
			new BetLeg("4")
		));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(4L)
			.hasStake("4")
			.hasMaxReturn("50");
	}


	// Interrelation

	@Test
	void doublesBetWithTwoInterrelatedSelectionsIsCalculated() {
		var bet = new Bet(new Perms(2), "1", List.of(
			new BetLeg("2", new IrDescriptor(11, 1, 1)),
			new BetLeg("3", new IrDescriptor(12, 1, 1)),
			new BetLeg("4", new IrDescriptor(21, 2, 1))
		));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(2L)
			.hasStake("2")
			.hasMaxReturn("20");
	}

	@Test
	void betWithAllInterrelatedSelectionsIsEmpty() {
		var bet = new Bet(new Perms(2), "1", List.of(
				new BetLeg("2", new IrDescriptor(11, 1, 1)),
				new BetLeg("3", new IrDescriptor(12, 1, 1)),
				new BetLeg("4", new IrDescriptor(13, 1, 1))
		));

		var result = bet.calculate();

		assertThatBetResult(result).isEmpty();
	}

	@Test
	void patentBetWithTwoInterrelatedSelectionsIsInvalid() {
		var bet = new Bet(FullCover.withoutSingles(), "1", List.of(
			new BetLeg("2", new IrDescriptor(11, 1, 1)),
			new BetLeg("3", new IrDescriptor(12, 1, 1)),
			new BetLeg("4", new IrDescriptor(21, 2, 1))
		));

		assertThatThrownBy(bet::calculate).isInstanceOf(IllegalArgumentException.class);
	}


	// Bankers

	@Test
	void singlesBetWithBankerIsCalculated() {
		var bet = new Bet(new Perms(1), "2", List.of(
			BetLeg.banker("2"),
			new BetLeg("3"),
			new BetLeg("4")
		));

		var result = bet.calculate();

		assertThatBetResult(result)
			.hasUnitCount(2L)
			.hasStake("4")
			.hasMaxReturn("28");
	}
}
