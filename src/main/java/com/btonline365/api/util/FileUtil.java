package com.btonline365.api.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtil {
	/**
	 * 将Base64编码的字符串写入二进制文件
	 * @param base64String
	 * @param outputFileName
	 * @return
	 */
	public static boolean writeFileFromBase64(String base64String, String path, String fileName) {
		if (base64String == null){
			return false;
		}
		try	{
			byte[] b = Base64Util.decode(base64String);
			File file = new File(path);
			if(!file.exists()){
				makeDir(file);
			}
			OutputStream out = new FileOutputStream(path + fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
}
