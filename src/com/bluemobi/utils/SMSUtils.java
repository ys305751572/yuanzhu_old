package com.bluemobi.utils;
/**
 * 短信验证码
 * @author longz
 *
 */
public class SMSUtils {

    /**
     * @Description 云片网 短信发送 依赖httpclient包
     * @param 参数列表
     *            String uname 用户帐号; String pword 用户密码;
     * @return "success"登录成功 "error.infowrong"登录失败
     * @date 2014-7-30 下午03:24:46
     * @author 龙哲
    
    public static void testFluent() {
        try {
            Content content = Request.Post("http://yunpian.com/v1/sms/tpl_send.json").bodyForm(
                    Form.form().add("apikey", "6590f1dc286242d3d127c941c18e4933").add("tpl_id",
                            "559877").add("tpl_value", "#code#=123456&#minr#=1").add("mobile",
                            "1597211509000").build()).execute().returnContent();
            System.out.println(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 */
    /**
     * @Description 大汉三通 短信发送 依赖util.WebserviceUtil.java,util.MD5Tools.java
     * @param 参数列表
     *            String uname 用户帐号; String pword 用户密码;
     * @return "success"登录成功 "error.infowrong"登录失败
     * @date 2014-11-25 11:28:07
     * @author 龙哲
     */
    public static void wt3Tong(String mobile,String content) {
        // 大汉三通 短信http请求地址
        String url = "http://wt.3tong.net/http/sms/Submit";
        // 帐号
        String account = "dh22110";
        // 密码
        String password = "526c30964ec3849eae471cf0dddeb116";
        
        StringBuffer message = new StringBuffer("message=");
        try {
            message.append("<?xml version='1.0' encoding='UTF-8'?>");
            message.append("<message>");
            message.append("<account>" + account + "</account>");
            message.append("<password>" + password + "</password>");
            message.append("<msgid></msgid>");
            message.append("<phones>"+ mobile +"</phones>");
            message.append("<content>"+content+"</content>");
            message.append("<sign>【爱佑福】</sign>");
            message.append("<subcode></subcode>");
            message.append("<sendtime></sendtime>");
            message.append("</message>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String result = WebserviceUtil.post(url, message.toString());
        System.out.println("result:" + result);
    }
}
