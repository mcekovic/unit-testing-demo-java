package com.igt.demo.bc.bettypes;

import java.util.*;
import java.util.stream.*;

import com.igt.demo.bc.*;

public class Accumulator implements BetType {

	public static final BetType INSTANCE = new Accumulator();

	@Override public <T> Stream<List<T>> combinations(List<T> items) {
		return Stream.of(items);
	}
}
