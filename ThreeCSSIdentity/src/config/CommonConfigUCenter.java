package config;

import config.ConfigManager.JsonConfigType;
import log.LogManager;
import net.sf.json.JSONObject;

public class CommonConfigUCenter extends CommonConfig {

	public static long TOKEN_EXPIRE_TIME;
	public static String USER_IMG_DIR;
	// 用户中心url
	public static String UCENTER_URL;

	public static void init() {
		LogManager.initLog.info("初始化CommonConfigUCenter");
		JSONObject configExt = ConfigManager.getJsonData(JsonConfigType.CONFIGEXT.getTypeValue());
		TOKEN_EXPIRE_TIME = configExt.getJSONArray("tokenExpireTime").getLong(0);
		USER_IMG_DIR = configExt.getJSONArray("userImgDir").getString(0);
		UCENTER_URL = configExt.getJSONArray("uCenterUrl").getString(0);
		LogManager.initLog.info("初始化CommonConfigUCenter完成");
	}
}
