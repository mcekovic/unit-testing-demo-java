package com.igt.demo.bc.bettypes;

import java.util.*;
import java.util.stream.*;

import com.igt.demo.bc.*;

import static org.paukov.combinatorics3.Generator.*;

public class Perms implements BetType {

	private final int length;

	public Perms(int length) {
		if (length <= 0)
			throw new IllegalArgumentException("Perms length must be positive");
		this.length = length;
	}

	@Override public <T> Stream<List<T>> combinations(List<T> items) {
		if (items.size() < length)
			throw new IllegalArgumentException("Perms item count must be greater than or equal to " + length);
		return combination(items).simple(length).stream();
	}

	@Override public boolean canSkipUnits() {
		return true;
	}
}
