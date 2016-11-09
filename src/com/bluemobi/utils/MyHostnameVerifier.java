package com.bluemobi.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		//System.out.println("Warning: URL Host: " + hostname + " vs. "
		//		+ session.getPeerHost());
		return true;
	}
}
