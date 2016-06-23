package com.mlx.uac.sign;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.utils.ApplicationProperties;
import com.mlx.uac.utils.HexStr;

public class MD5Sign {

	private static final String ALGORITHM = "MD5";

	public static byte[] encode(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5Sign.ALGORITHM);
		return md.digest(data);
	}

	public static void main(String[] args) throws Exception {
		String str = "美丽行美丽行美丽行美丽行美丽行美丽行美丽行dddddddddddddddddddddfdsfdfswerfdgfdhgtrevxf" + ApplicationProperties.getMessage(ConstEC.MLX_PRIVATEKEY);
		byte[] data = encode(str.getBytes("UTF-8"));
		String hexStr = HexStr.bytesToHexString(data);
		String sign = Base64.encodeBase64String(hexStr.getBytes());
		System.out.println(sign);
	}
}
