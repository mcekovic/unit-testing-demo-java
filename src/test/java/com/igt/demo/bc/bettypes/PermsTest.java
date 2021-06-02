package com.igt.demo.bc.bettypes;

import java.util.*;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class PermsTest {

	@Test
	void perms2() {
		var perms2 = new Perms(2);

		var combinations = perms2.combinations(List.of("A", "B", "C"));

		assertThat(combinations).containsExactly(
			List.of("A", "B"),
			List.of("A", "C"),
			List.of("B", "C")
		);
	}

	@Test
	void permsLengthMustNotBeZero() {
		assertThatThrownBy(
			() -> new Perms(0)
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void permsLengthMustNotBeGreaterThenItemCount() {
		assertThatThrownBy(
			() -> new Perms(2).combinations(List.of("A"))
		).isInstanceOf(IllegalArgumentException.class);
	}
}
