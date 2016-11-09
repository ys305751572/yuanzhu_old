package com.bluemobi.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对密码进行加密和验证的程序
 * @author Ray
 *
 */
public class MD5Tools {
	
    //十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /** 把inputString加密     
     * @throws NoSuchAlgorithmException */
    public static String createPassword(String pwd) throws NoSuchAlgorithmException{    	
        return encodeByMD5(encodeByMD5(pwd));
    }    
    /**
     * 验证输入的密码是否正确
     * @param password    真正的密码（加密后的真密码）
     * @param inputString    输入的字符串
     * @return    验证结果，boolean类型
     * @throws NoSuchAlgorithmException 
     */
    private static boolean authenticatePassword(String password,String newPassword) throws NoSuchAlgorithmException {
        if(password.equals(encodeByMD5(encodeByMD5(newPassword)))) {
            return true;
        } else {
            return false;
        }
    }
    
    /** 对字符串进行MD5加密     
     * @throws NoSuchAlgorithmException */
    private static String encodeByMD5(String originString) throws NoSuchAlgorithmException {
        if (originString != null) {
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                //将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
        }
        return null;
    }
    
    /**
     * 转换字节数组为十六进制字符串
     * @param b    字节数组
     * @return    十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    /** 将一个字节转化成十六进制形式的字符串     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
  public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = MD5Tools.createPassword("test");
        System.out.println("对 用MD5摘要后的字符串：" + password);
        String inputString = "test";
        System.out.println(MD5Tools.authenticatePassword(password, inputString));

   }

}

