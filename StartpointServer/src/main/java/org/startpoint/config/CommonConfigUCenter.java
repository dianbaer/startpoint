package org.startpoint.config;

public class CommonConfigUCenter {

	public static long TOKEN_EXPIRE_TIME;
	public static String USER_IMG_DIR;
	// 用户中心url
	public static String UCENTER_URL;

	public static void init() {

		TOKEN_EXPIRE_TIME = 3600000;
		USER_IMG_DIR = "IdentityUserImgDir";
		UCENTER_URL = "http://localhost:8080/IdentityServer/s";
	}
}
