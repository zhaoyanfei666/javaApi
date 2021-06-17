package com.btonline365.api.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;

public class SignUtil {
	
	private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "signType";
    
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名验证。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isMd5SignatureValid(JSONObject data, String key) throws Exception {
        return isSignatureValid(data, key, SignUtil.MD5);
    }
 
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(JSONObject data, String key, String signType) throws Exception {
        if (!data.containsKey(FIELD_SIGN) ) {
            return false;
        }
        String sign = data.getString(FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }
    /**
     * 生成HMACSHA256签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSHA256Signature(final JSONObject data, String key) throws Exception {
        return generateSignature(data, key, SignUtil.HMACSHA256);
    }
    /**
     * 生成MD5签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateMd5Signature(final JSONObject data, String key) throws Exception {
        return generateSignature(data, key, SignUtil.MD5);
    }
    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final JSONObject data, String key, String signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(FIELD_SIGN)) {
                continue;
            }
            if (data.getString(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.getString(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignUtil.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignUtil.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }
    /**
     * 获取随机字符串
     * @return
     */
    public static String generateNonceStr(int length) {
        char[] nonceChars = new char[length];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
    public static void main(String[] args) throws Exception {
    	String accesskey = generateNonceStr(16);
    	System.out.println("accesskey:"+accesskey);
    	String accesskeyScret = generateNonceStr(32);
    	System.out.println("accesskeyScret:"+accesskeyScret);
		JSONObject data=new JSONObject();
		data.put("aa", "123456");
		data.put("bb", "赵艳飞");
		String generateSignature = generateSignature(data, "121qwq123qwe123qweasdqwqwqwqwqqqqqqq", SignUtil.HMACSHA256);
		System.out.println(generateSignature);
		data.put(SignUtil.FIELD_SIGN, generateSignature);
		boolean md5SignatureValid = isSignatureValid(data, "121qwq123qwe123qweasdqwqwqwqwqqqqqqq", SignUtil.HMACSHA256);
		System.out.println(md5SignatureValid);
	}
}
