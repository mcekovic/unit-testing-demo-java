package com.igt.demo.bc;

import java.util.*;

import lombok.*;

@Value
public class IrDescriptor {

	public static final IrDescriptor UNKNOWN = new IrDescriptor(null, null, null);

	Object selectionId;
	Object marketId;
	Integer maxWinners;

	public boolean isInterrelated(IrDescriptor descriptor) {
		if (selectionId != null && selectionId.equals(descriptor.selectionId))
			return true;
		if (marketId != null && marketId.equals(descriptor.marketId)) {
			if (!Objects.equals(maxWinners, descriptor.maxWinners))
				throw new IllegalStateException("Different maxWinners for different Legs on the same market");
			return Objects.equals(maxWinners, 1);
		}
		return false;
	}
}
