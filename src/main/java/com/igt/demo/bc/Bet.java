package com.igt.demo.bc;

import java.math.*;
import java.util.*;
import java.util.stream.*;

import lombok.*;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

@Data
@AllArgsConstructor
public class Bet {

	private BetType betType;
	private BigDecimal unitStake;
	private List<BetLeg> legs;

	public Bet(BetType betType, String unitStake, List<BetLeg> legs) {
		this.betType = requireNonNull(betType, "betType");
		this.unitStake = new BigDecimal(requireNonNull(unitStake, "unitStake"));
		this.legs = requireNonNull(legs, "legs");
		if (this.unitStake.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Bet unitStake must be positive");
		if (legs.isEmpty())
			throw new IllegalArgumentException("Bet legs cannot be empty");
	}

	public BetResult calculate() {
		return bankerAwareCombinations()
			.map(legs -> new BetUnit(legs, unitStake))
			.filter(this::isValidUnit)
			.map(BetUnit::getResult)
			.reduce(BetResult.EMPTY, BetResult::add);
	}

	private Stream<List<BetLeg>> bankerAwareCombinations() {
		var bankerLegs = legs.stream().filter(BetLeg::isBanker).collect(toList());
		if (bankerLegs.isEmpty())
			return betType.combinations(legs);
		else {
			var nonBankerLegs = legs.stream().filter(leg -> !leg.isBanker()).collect(toList());
			return betType.combinations(nonBankerLegs)
					.map(combination -> {
						var combinationWithBankers = new ArrayList<>(bankerLegs);
						combinationWithBankers.addAll(combination);
						return combinationWithBankers;
					});
		}
	}

	private boolean isValidUnit(BetUnit unit) {
		if (unit.isInterrelated()) {
			if (betType.canSkipUnits())
				return false;
			else
				throw new IllegalArgumentException("Interrelated selections detected");
		}
		return true;
	}
}
