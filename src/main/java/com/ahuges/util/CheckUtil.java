package com.ahuges.util;

import java.util.Arrays;

public class CheckUtil {
	public static final String token = "ahuges";//校验token
	/**
	 * 根据腾讯微信校验规则校验握手信息
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce){
		String[] array = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(array);
		//生成字符串
		StringBuffer content = new StringBuffer();
		for(int i = 0; i < array.length; i++){
			content.append(array[i]);
		}
		String sha1 = SHA1.encode(content.toString());
		
		return sha1.equals(signature);
	}
}
