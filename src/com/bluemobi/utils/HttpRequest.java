package com.bluemobi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluemobi.constant.TokenCache;


public class HttpRequest {
	public static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	private static String CHARSET = "utf-8";

	public static String httpRequest(String url, String body, String method) {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setConnectTimeout(12000);
			c.setReadTimeout(12000);
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestMethod(method);
			c.setUseCaches(false);
			c.setRequestProperty("Content-Type", "application/json;charset="+ CHARSET);
			c.connect();
			if ("POST".equals(method.toUpperCase())
					|| "PUT".equals(method.toUpperCase())) {
				OutputStream out = c.getOutputStream();
				out.write(body.getBytes());
				out.flush();
				out.close();
			}

			System.out.println("ResponseCode:" + c.getResponseCode());
			if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = c.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, CHARSET));

				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				c.disconnect();
				return sb.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		catch (SocketTimeoutException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			
		}
		return null;
	}
	
	public static String httpNoTimeLimitRequest(String url, String body, String method) {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setConnectTimeout(12000);
			c.setReadTimeout(12000);
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestMethod(method);
			c.setUseCaches(false);
			c.setRequestProperty("Content-Type", "application/json;charset="+ CHARSET);
			c.connect();
			if ("POST".equals(method.toUpperCase())
					|| "PUT".equals(method.toUpperCase())) {
				OutputStream out = c.getOutputStream();
				out.write(body.getBytes());
				out.flush();
				out.close();
			}

			System.out.println("ResponseCode:" + c.getResponseCode());
			if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = c.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, CHARSET));

				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				c.disconnect();
				return sb.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		catch (SocketTimeoutException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			
		}
		return null;
	}

	public static void initHttpsURLConnection() {
		MyX509TrustManager xtm = new MyX509TrustManager();
		MyHostnameVerifier hnv = new MyHostnameVerifier();
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL"); // 或SSL
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}

	public static String httpsRequest(String url, String body, String method, String contentType) {
		String token = "";
		if (!EasemobUtil.GET_TOKEN_URL.equals(url)) {
			//获取token
			if (EasemobUtil.DEBUG_MODE) {
				token = EasemobUtil.TOKEN;
			}else {
				token = TokenCache.getAccessToken().getAccess_token();
			}
		}
		
		initHttpsURLConnection();
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod(method);
			urlCon.setRequestProperty("authorization", "Bearer "+token);
			if(!"".equals(contentType))
				urlCon.setRequestProperty("Content-Type", contentType);
			urlCon.setUseCaches(false);
			urlCon.setDoInput(true);

			if ("POST".equals(method.toUpperCase())
					|| "PUT".equals(method.toUpperCase())) {
				OutputStream out = urlCon.getOutputStream();
				byte[] b = body.getBytes("utf-8");
				out.write(b);
				out.flush();
				out.close();
			} 

			System.out.println("https ResponseCode:" + urlCon.getResponseCode());
			if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = urlCon.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, CHARSET));

				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				urlCon.disconnect();
				return sb.toString();
			}
				
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			
		}
		return null;
	}
	
}
