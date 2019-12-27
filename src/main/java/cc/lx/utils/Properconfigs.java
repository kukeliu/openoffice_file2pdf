package cc.lx.utils;

import java.util.ResourceBundle;

/**
 * 配置文件读取类
 * */


public class Properconfigs {
	
	private static String STATE_CONFIG_FILE= "config";

	public static ResourceBundle loadConfigProperties(){
		ResourceBundle resourceBundle = ResourceBundle.getBundle(STATE_CONFIG_FILE);
		return resourceBundle;
	}

	/**
	 * 根据key获得属性值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		ResourceBundle resourceBundle = loadConfigProperties();
		return resourceBundle.getString(key);
	}

	public static void main(String[] args) {
		String result = getProperty("openofficehome");
	}

}
