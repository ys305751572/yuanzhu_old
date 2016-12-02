package com.bluemobi.pay.excute;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import com.bluemobi.pay.AccessTokenRequestHandler;
import com.bluemobi.pay.ClientRequestHandler;
import com.bluemobi.pay.PrepayIdRequestHandler;
import com.bluemobi.pay.util.ConstantUtil;
import com.bluemobi.pay.util.WXUtil;
import com.bluemobi.utils.CommonUtils;


public class PayRequest {

    public static Map<String, Object> pay(HttpServletRequest request, HttpServletResponse response,String notifyUrl) {

        response.resetBuffer();
        response.setHeader("ContentType", "text/xml");
        String out_trade_no = request.getAttribute("sn").toString();
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 预支付ID
        String prepayid = "";

        //token
        String token = AccessTokenRequestHandler.getAccessToken();
        if (!"".equals(token)) {
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);

            prepayReqHandler.setParameter("body", "签约支付");
            prepayReqHandler.setParameter("device_info", ConstantUtil.DEVICE_INFO);
            prepayReqHandler.setParameter("fee_type", "CNY");
            prepayReqHandler.setParameter("mch_id", ConstantUtil.PARTNER);
            String noncestr = WXUtil.getNonceStr();
            prepayReqHandler.setParameter("nonce_str", noncestr);
            prepayReqHandler.setParameter("notify_url", notifyUrl);
            prepayReqHandler.setParameter("out_trade_no", out_trade_no);
//            prepayReqHandler.setParameter("spbill_create_ip", CommonUtils.getRealAddress(request));
            prepayReqHandler.setParameter("spbill_create_ip", "4.16.2.73");
            String fee = request.getAttribute("fee").toString();
//			prepayReqHandler.setParameter("total_fee", "1");
			prepayReqHandler.setParameter("total_fee", request.getAttribute("fee").toString());
            prepayReqHandler.setParameter("trade_type", ConstantUtil.TRADE_TYPE);

            String sign = prepayReqHandler.createMD5Sign();
            prepayReqHandler.setParameter("sign", sign);
            try {
                prepayid = prepayReqHandler.sendPrepay();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (null != prepayid && !"".equals(prepayid)) {
                clientHandler.setParameter("appid", ConstantUtil.APP_ID);
                clientHandler.setParameter("noncestr", noncestr);
                clientHandler.setParameter("package", "Sign=WXPay");
                clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
                clientHandler.setParameter("prepayid", prepayid);
                clientHandler.setParameter("timestamp", WXUtil.getTimeStamp());
                sign = clientHandler.createMD5Sign();
                clientHandler.setParameter("sign", sign);
                resultMap = clientHandler.getMap();
            }
        } 
        return resultMap;
    }
}
