package com.bluemobi.utils;

import com.bluemobi.sys.identifier.Generator;
import com.bluemobi.sys.identifier.UUIDGenerator;

public class IdentifierUtils {

	/**
	 * 生成唯一识别码
	 * 
	 * @return
	 */
	public static Generator getId() {
		return new UUIDGenerator();
	}
}
