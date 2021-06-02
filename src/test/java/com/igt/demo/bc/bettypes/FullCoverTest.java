package com.igt.demo.bc.bettypes;

import java.util.*;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class FullCoverTest {

	@Test
	void withoutSingles() {
		var withoutSingles = FullCover.withoutSingles();

		var combinations = withoutSingles.combinations(List.of("A", "B", "C"));

		assertThat(combinations).containsExactly(
			List.of("A", "B"),
			List.of("A", "C"),
			List.of("B", "C"),
			List.of("A", "B", "C")
		);
	}

	@Test
	void withSingles() {
		var withSingles = FullCover.withSingles();

		var combinations = withSingles.combinations(List.of("A", "B", "C"));

		assertThat(combinations).containsExactly(
			List.of("A"),
			List.of("B"),
			List.of("C"),
			List.of("A", "B"),
			List.of("A", "C"),
			List.of("B", "C"),
			List.of("A", "B", "C")
		);
	}

	@Test
	void fullCoverWithoutSinglesMustBeOverAtLeastTwoItems() {
		assertThatThrownBy(
			() -> FullCover.withoutSingles().combinations(List.of("A"))
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void fullCoverWithSinglesMustBeOverAtLeastOneItem() {
		assertThatThrownBy(
			() -> FullCover.withSingles().combinations(List.of())
		).isInstanceOf(IllegalArgumentException.class);
	}
}
