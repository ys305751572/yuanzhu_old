package com.bluemobi.utils.encryption;

import com.bluemobi.utils.WebserviceUtil;

public class Test {

	public static void main(String[] args) {

//		String content = "{\"request_no\":\"1001\",\"service_code\":\"FS0001\",\"contract_id\":\"100002\",\"order_id\":\"0\",\"phone_id\":\"13913996922\",\"plat_offer_id\":\"100094\",\"channel_id\":\"1\",\"activity_id\":\"100045\"}";
		String content = "{'token':'gNG6A6WSrom-vGQtQQiCZQ1uDhQyt9Ode7Z9ewn_','memberId':'190'}";
		                //{'userId':'190','token':'gNG6A6WSrom-vGQtQQiCZQ1uDhQyt9Ode7Z9ewn_'}
		try {
			long lStart = System.currentTimeMillis();
			byte[] encryptResultStr = BackAES.encrypt(content, BackAES.TYPE_ECB);
			String encrypt = new String(encryptResultStr);
			System.out.println("鍔犲瘑鍚�"+ encrypt);
			long lUseTime = System.currentTimeMillis() - lStart;
			System.out.println("鍔犲瘑鑰楁椂锛�" + lUseTime + "姣");


			lStart = System.currentTimeMillis();
			String decryptString=BackAES.decrypt(new String(encryptResultStr),BackAES.TYPE_ECB);
			System.out.println("瑙ｅ瘑鍚�"+decryptString);
			lUseTime = System.currentTimeMillis() - lStart;
			System.out.println("瑙ｅ瘑鑰楁椂锛�" + lUseTime + "姣");

			String url = "http://121.41.17.108:8080/yqss/app/member/allAddress?" + encrypt;
			System.out.println("璇锋眰璺緞:" + url);
			System.out.println(WebserviceUtil.post(url));
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
