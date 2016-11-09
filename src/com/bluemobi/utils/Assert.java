package com.bluemobi.utils;

public class Assert {

	public static void IsNotNull(Object obj) {
		if(obj == null) {
			throw new IllegalArgumentException();
		}
	}
}

