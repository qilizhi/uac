package com.mlx.uac.sign;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.utils.HexStr;

public class Sign {

	/**
	 * 
	 * @param requestMap
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public static String getPlain(Map<String, Object> requestMap, String[] keys) {
		StringBuffer plain = new StringBuffer("");

		Arrays.sort(keys);

		Object value = null;
		for (String key : keys) {
			value = requestMap.get(key);
			if (null != value && !"".equals(value.toString().trim())) {
				plain.append(requestMap.get(key.toString().trim()));
			}
		}

		return plain.toString();
	}

	/**
	 * 调用查询商品加密方式
	 * @return
	 * @throws Exception
	 */
	public static String getGoodsPlain(Map<String, Object> requestMap, String[] keys) throws Exception {
		StringBuffer plain = new StringBuffer("");

		Arrays.sort(keys);

		Object value = null;
		for (String key : keys) {
			value = requestMap.get(key);
			if (null != value && !"".equals(value.toString().trim())) {
				plain.append(requestMap.get(key.toString()));
			}
		}
		return Sign.goodSign(plain.toString());
	}

	/**
	 * 签名 passport MD5+Base64
	 * @param plain
	 * @return
	 * @throws Exception
	 */
	public static String sign(String plain) throws Exception {
		byte[] data;
		data = MD5Sign.encode(plain.getBytes(ConstEC.ENCODE_UTF8));
		return Base64.encodeBase64String(HexStr.bytesToHexString(data).getBytes());
	}

	/**
	 * goods MD5签名
	 * @param plain
	 * @return
	 * @throws Exception
	 */
	public static String goodSign(String plain) throws Exception {
		byte[] data;
		data = MD5Sign.encode(plain.getBytes(ConstEC.ENCODE_UTF8));
		return HexStr.bytesToHexString(data);
	}

	public static boolean verify(String plain, String sign) throws Exception {
		if (sign.equalsIgnoreCase(sign(plain))) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		byte[] data;
		data = MD5Sign.encode("1000.001passportRefundCheck45678924341457123".getBytes(ConstEC.ENCODE_UTF8));
		String hex = HexStr.bytesToHexString(data);
		System.out.println("hex[]==" + hex);
	}
}
