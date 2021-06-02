package com.igt.demo.bc;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class IrDescriptorTest {

	@Test
	void sameSelectionsAreInterrelated() {
		var descriptor1 = new IrDescriptor(11, 1, 1);
		var descriptor2 = new IrDescriptor(11, 1, 1);

		var interrelated = descriptor1.isInterrelated(descriptor2);

		assertTrue(interrelated);
	}

	@Test
	void differentSelectionsAndSameSingleWinnerMarketsAreInterrelated() {
		var descriptor1 = new IrDescriptor(11, 1, 1);
		var descriptor2 = new IrDescriptor(12, 1, 1);

		var interrelated = descriptor1.isInterrelated(descriptor2);

		assertTrue(interrelated);
	}

	@Test
	void differentSelectionsAndSameMultiWinnerMarketsAreNotInterrelated() {
		var descriptor1 = new IrDescriptor(11, 1, null);
		var descriptor2 = new IrDescriptor(12, 1, null);

		var interrelated = descriptor1.isInterrelated(descriptor2);

		assertFalse(interrelated);
	}

	@Test
	void differentSelectionsAndDifferentMarketsAreNotInterrelated() {
		var descriptor1 = new IrDescriptor(11, 1, 1);
		var descriptor2 = new IrDescriptor(21, 2, 1);

		var interrelated = descriptor1.isInterrelated(descriptor2);

		assertFalse(interrelated);
	}

	@Test
	void unknownDescriptorIsNeverInterrelated() {
		var descriptor = new IrDescriptor(11, 1, 1);

		var interrelated = descriptor.isInterrelated(IrDescriptor.UNKNOWN);

		assertFalse(interrelated);
	}

	@Test
	void sameMarketsWithDifferentMaxWinnersAreIllegal() {
		var descriptor1 = new IrDescriptor(11, 1, 1);
		var descriptor2 = new IrDescriptor(12, 1, 2);

		assertThrows(IllegalStateException.class, () -> descriptor1.isInterrelated(descriptor2));
	}
}
