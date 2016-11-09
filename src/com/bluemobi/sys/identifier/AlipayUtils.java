package com.bluemobi.sys.identifier;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import com.bluemobi.constant.Constant;
import com.bluemobi.utils.PropertiesUtils;
import net.sf.json.JSONObject;

/**
 * 支付宝APIid生成器
 * @author yesong
 *
 */
public class AlipayUtils {
	
	/** 爱心币与人民币的比例 100:1 */
	public static final int PROPORTION = 100;
	
	/**
	 * 生成商品ID
	 * @return
	 */
	public static String generatorUUID(){
		String uuidStr = UUID.randomUUID().toString();
		uuidStr = uuidStr.replaceAll("-", "");
		return uuidStr;
	}
	
	/**
	 * 生成商品名称
	 * @param userId
	 * @return
	 */
	public static String generatorName(int userId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
		return (sdf.format(new Date()) + userId);
	}
	
	/**
	 * 根据爱心币算出人民币的价格
	 * @param coinNum
	 * @return
	 * @throws Exception 
	 */
	public static double getTotalRMB(int coinNum)  {
		double _RMB = 0;
		try {
			String coin_prop =	PropertiesUtils.getPropertiesValues("coin", Constant.COIN_PROP);
			if(StringUtils.isNotBlank(coin_prop)){
				double _rmb = (coinNum * 1.0/ Integer.parseInt(coin_prop)) ;
				DecimalFormat df = new DecimalFormat("0.00");
				_RMB = Double.valueOf(df.format(_rmb)); 
			}
			else{
				throw new Exception("coin.properties表中的比例错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _RMB;
	}
	
	public static Object getObejct(String string,String param){
		JSONObject obj = JSONObject.fromObject(string);
		Object subObject = obj.get(param);
		if(subObject != null){
			return subObject;
		}
		else{
			String subStr = obj.getString("result");
			JSONObject subject1 = JSONObject.fromObject(subStr);
			return subject1.getString(param);
			
		}
	}
	
	/**
	 * 将支付宝返回字符串结果转换为JSON类型
	 * @param result
	 * @return
	 */
	public static String result2JsonString(String result){
		if(StringUtils.isBlank(result)){
			throw new IllegalAccessError("支付宝返回结果不能空");
		}
		String[] results = result.split(";");
		StringBuffer jsonResult = new StringBuffer(1000);
		String JR = "";
		for (String string : results) {
			if(string.startsWith("resultStatus")){
				jsonResult.append(string2Json(string,true) + ",");
			}
			else if(string.startsWith("memo")){
				jsonResult.append(string2Json(string, null) + ",");
			}
			else if(string.startsWith("result")){
				jsonResult.append(string2Json(string, false) + ",") ; 
			}
		}
		JR = bulider(jsonResult);
		System.err.println(JR); 
		return JR;
	}
	
	private static String string2Json(String str, Boolean b) {
		String s = str;
		if(b == null) {
			s = s.replaceAll("\\{", "\"");
			s = s.replaceAll("\\}", "\"");
			return s;
		}
		if(b){
			s = s.replaceAll("\\{", "");
			s = s.replaceAll("\\}", "");
		}
		else{
			//s = s.replaceAll("\"\"", "\'\'");	
			s = s.replaceAll("\\&", ",");
		}
		s = s.replaceAll("\\=", "\\:");
		return s;
	}

	private static String bulider(StringBuffer jsonResult) {
		String s = jsonResult.toString().substring(0,jsonResult.length()-1);
		StringBuffer sb = new StringBuffer();
		final String prefix = "{";
		final String suffix = "}";
		sb.append(prefix).append(s).append(suffix);
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getTotalRMB(100));
	}
}
