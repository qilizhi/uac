package com.mlx.uac.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量类
 * @author feihangchen
 *
 */
public class ConstEC {

	public static final String METHODTYPE = "methodType";
	public static final String GOODS = "goods";
	public static final String DEFAULT_PAGESIZE = "10";
	public static final String DEFAULT_PAGENO = "1";
	public static final String ORDER_INFO_SEQUENCE = "order_info_sequence";
	public static final String ORDER_JN_SEQUENCE = "order_jn_sequence";
	public static final String ORDER_REFUND_SEQUENCE = "order_refund_sequence";
	public static final String ORDER_REQUEST_SEQUENCE = "order_request_sequence";
	public static final String ENCODE_UTF8 = "utf-8";
	public static final String ENCODE_GBK = "gbk";
	
	public static Map<String, String> BINDTYPEADAPTER = new HashMap<String, String>();
	
	static {

		BINDTYPEADAPTER.put("UserBindInfo_MLX", "user_bind_mlx");
		BINDTYPEADAPTER.put("UserBindInfo_WEIXIN", "user_bind_weixin");
		BINDTYPEADAPTER.put("UserBindInfo_ALIPAY", "user_bind_alipay");
		BINDTYPEADAPTER.put("UserBindInfo_WEIBO", "user_bind_weibo");
		BINDTYPEADAPTER.put("UserBindInfo_QQ", "user_bind_qq");
		
	}

	public static final String MIMETYPE = "text/plain";

	public static final String SUCCESS = "success";
	public static final String CODE_0000 = "0000";
	public static final String CODE_200 = "200";

	public static final String VERSION = "1.0";
	public static final String SIGN = "sign";
	public static final String CURRENCY = "";
	public static final String RPID = "rpId";
	public static final String PARAMTER_VERIFY_PREFIX = "paramter_verify.";
	public static final String MLX_PRIVATEKEY = "mlx.privatekey";
}
