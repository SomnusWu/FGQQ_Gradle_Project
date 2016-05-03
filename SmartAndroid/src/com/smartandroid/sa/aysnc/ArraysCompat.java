package com.smartandroid.sa.aysnc;

import java.lang.reflect.Array;

/**
 *
 */
public class ArraysCompat {

	@SuppressWarnings("unchecked")
	public static <T> T[] copyOf(T[] original, int newLength) {
		return (T[]) copyOf(original, newLength, original.getClass());
	}

	public static <T, U> T[] copyOf(U[] original, int newLength,
			Class<? extends T[]> newType) {
		@SuppressWarnings("unchecked")
		T[] copy = (newType.equals(Object[].class) ) ? (T[]) new Object[newLength]
				: (T[]) Array
						.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, 0, copy, 0,
				Math.min(original.length, newLength));
		return copy;
	}
}
