package com.igt.demo.bc.bettypes;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.igt.demo.bc.*;
import org.paukov.combinatorics3.*;

public class FullCover implements BetType {

	public static BetType withSingles() {
		return new FullCover(1);
	}

	public static BetType withoutSingles() {
		return new FullCover(2);
	}

	private final int startLength;

	private FullCover(int startLength) {
		this.startLength = startLength;
	}

	@Override public <T> Stream<List<T>> combinations(List<T> items) {
		if (items.size() < startLength)
			throw new IllegalArgumentException("FullCover item count must be greater than or equal to " + startLength);
		return IntStream.rangeClosed(startLength, items.size())
			.mapToObj(length -> Generator.combination(items).simple(length).stream())
			.flatMap(Function.identity());
	}
}
