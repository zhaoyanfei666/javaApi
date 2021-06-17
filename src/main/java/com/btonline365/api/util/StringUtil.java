package com.btonline365.api.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


/*
 *String 处理类
 */
public class StringUtil{

	/**
	 * NULL 转空
	 * 
	 * @param str
	 * @return
	 */
	public static String NullToEmpty(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * 判断当前字符串是否包含在字符串数组中
	 * 
	 * @param s1
	 *            数组格式字符串，以“，”分割
	 * @param s2
	 *            字符串
	 * @return
	 */
	public static boolean equelsStrs(String s1, String s2) {
		String[] strs = s1.split(",");
		for (String str : strs) {
			if (str.equals(s2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成字母数字组合 随机数
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getStringRandom(int length) {
		String val = "";
		Random random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	public static String GetUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:57:28
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		boolean result = false;
		if (obj == null) {
			result = true;
		} else if (obj instanceof CharSequence) {
			result = ((CharSequence) obj).length()==0;
		} else if (obj instanceof List) {
			result = ((List) obj).isEmpty();
		} else if (obj instanceof Map) {
			result = ((Map) obj).isEmpty();
		} else if (obj instanceof Set) {
			result = ((Set) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			result = ((Object[]) obj).length == 0;
		}
		return result;
	}

	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:55:48
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:55:54
	 */
	public static String cutEndStr(String str) {
		return cutEndStr(str, ",");
	}

	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:55:59
	 */
	public static String cutEndStr(String str, String endStr) {
		if (str.endsWith(endStr))
			str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:56:13
	 */
	public static boolean eq(String str, String str_) {
		String[] arr = str.split(","), arr_ = str_.split(",");
		boolean flag = true;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr_.length; j++) {
				if (i == j) {
					if (arr[i].equals(arr_[j]))
						flag = flag && true;
					else
						flag = flag && false;
					continue;
				}
			}
		}
		return flag;
	}

	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 上午3:14:14
	 */
	public static String set2String(Set<String> sets) {
		String temp = "";
		for (String set : sets) {
			temp += set + ",";
		}
		return cutEndStr(temp);
	}
	
	/**
	 * <code>write By 阿正<code>
	 * <pre> todo
	 * <pre> date 2017年6月30日 下午5:56:48
	 */
	public static String tirm(String str) {
		return str.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "").replaceAll("\\(|\\)", "");
	}
	public static String trim(String str) {
		if(str==null){
			return null;
		}
		try {
			str = str.trim();
			str = str.replaceAll("\u00A0", "");
			str = str.replaceAll(" ", "");
			str = str.replaceAll("\r", "");
			str = str.replaceAll("&nbsp;", "");
			str = str.replaceAll("\\s*", "");
		} catch (Exception e) {
		}
		return str;
	}
	/**
	 * <code>write By 阿正<code>
	 * <pre> todo choose the parameter
	 * If the first parames is not empty or null choose the first parameter,
	 * otherwise choose the second parameter
	 * <pre> date 2017年7月14日 下午10:17:44
	 */
    public static Object choose(Object first,Object second) {
		return isNotEmpty(first)?first:second;
	}
    /**
     * 用指定字符串值替换为null的字符串，不为空则返回原值
     * @param str
     * @param replacement
     * @return
     */
    public static String replaceNull(String str, String replacement) {
    	return str==null?replacement:str;
    }
    /**
     * 用空字符串值替换为null的字符串，不为空则返回原值
     * @param str
     * @param replacement
     * @return
     */    
    public static String replaceNull(String str) {
    	return str==null?"":str;
    }
    /**
     * 判断字符串是否有值
     * @param s
     * @return
     */
    public static boolean hasValue(String s) {
    	return (s!=null && s.trim().length()>0)?true:false;
    }
    /**
     * 删除字符串中空白字符（包括：空格、制表符、换页符等）
     * @param s
     * @return
     */
    public static String removeBlank(String s) {
    	return s.replaceAll("\\s*", "");
    }
    
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
	
	public static boolean isNumber(Object obj){
		if(obj==null || isEmpty(obj)){
			return false;
		}
		if("0".equals(obj)) return true;
		String s = String.valueOf(obj);
		String regex = "^[1-9][0-9]*\\.[0-9]+$|^[1-9][0-9]*$|^0+\\.[0-9]+$";  
        char c = s.charAt(0);  
        boolean bool;  
        if(c=='+'|c=='-'){
            bool = s.substring(1).matches(regex);
        }else{
            bool = s.matches(regex);
        }
        return bool;
	}
	
	
	public static String toString(Object obj){
		if(obj==null || "null".equals(obj.toString())){
			return null;
		}
		if(isNumber(obj)){
			return String.valueOf(obj);
		}
		return obj.toString();
	}
	
	public static String substr(String str, int len){
		if(isEmpty(str)) return str;
		if(str.length()<=len) return str;
		return str.substring(0, len)+"……";
	}
}
