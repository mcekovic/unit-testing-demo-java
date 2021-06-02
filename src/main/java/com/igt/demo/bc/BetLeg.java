package com.igt.demo.bc;

import java.math.*;

import lombok.*;

import static java.util.Objects.*;

@Data
public class BetLeg {

	public static BetLeg banker(String price) {
		return banker(price, IrDescriptor.UNKNOWN);
	}

	public static BetLeg banker(String price, IrDescriptor descriptor) {
		return new BetLeg(new BigDecimal(price), descriptor, true);
	}

	private BigDecimal price;
	private IrDescriptor descriptor;
	private boolean banker;

	public BetLeg(String price) {
		this(price, IrDescriptor.UNKNOWN);
	}

	public BetLeg(String price, IrDescriptor descriptor) {
		this.price = new BigDecimal(price);
		this.descriptor = descriptor;
	}

	public BetLeg(BigDecimal price, IrDescriptor descriptor, boolean banker) {
		this.price = requireNonNull(price, "price");
		this.descriptor = requireNonNull(descriptor, "descriptor");
		this.banker = banker;
		if (price.compareTo(BigDecimal.ONE) < 0)
			throw new IllegalArgumentException("BetLeg price cannot be less than 1");
	}
}
