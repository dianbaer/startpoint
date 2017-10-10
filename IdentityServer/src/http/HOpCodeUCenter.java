package http;

import org.grain.httpserver.HttpManager;

import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UserGroupProto.CheckUserByUserNameC;
import protobuf.http.UserGroupProto.CheckUserByUserNameS;
import protobuf.http.UserGroupProto.CheckUserPhoneC;
import protobuf.http.UserGroupProto.CheckUserPhoneS;
import protobuf.http.UserGroupProto.CreateUserC;
import protobuf.http.UserGroupProto.CreateUserGroupC;
import protobuf.http.UserGroupProto.CreateUserGroupS;
import protobuf.http.UserGroupProto.CreateUserS;
import protobuf.http.UserGroupProto.DeleteTokenC;
import protobuf.http.UserGroupProto.DeleteTokenS;
import protobuf.http.UserGroupProto.DeleteUserGroupC;
import protobuf.http.UserGroupProto.DeleteUserGroupS;
import protobuf.http.UserGroupProto.GetTokenC;
import protobuf.http.UserGroupProto.GetTokenS;
import protobuf.http.UserGroupProto.GetUserByEmailC;
import protobuf.http.UserGroupProto.GetUserByEmailS;
import protobuf.http.UserGroupProto.GetUserC;
import protobuf.http.UserGroupProto.GetUserGroupC;
import protobuf.http.UserGroupProto.GetUserGroupListC;
import protobuf.http.UserGroupProto.GetUserGroupListS;
import protobuf.http.UserGroupProto.GetUserGroupS;
import protobuf.http.UserGroupProto.GetUserImgC;
import protobuf.http.UserGroupProto.GetUserListC;
import protobuf.http.UserGroupProto.GetUserListS;
import protobuf.http.UserGroupProto.GetUserS;
import protobuf.http.UserGroupProto.UpdateTokenC;
import protobuf.http.UserGroupProto.UpdateTokenS;
import protobuf.http.UserGroupProto.UpdateUserC;
import protobuf.http.UserGroupProto.UpdateUserGroupC;
import protobuf.http.UserGroupProto.UpdateUserGroupS;
import protobuf.http.UserGroupProto.UpdateUserS;

public class HOpCodeUCenter {
	public static String UC_ERROR = "0";

	public static String CREATE_USER_GROUP = "1";
	public static String UPDATE_USER_GROUP = "2";
	public static String GET_USER_GROUP = "3";
	public static String GET_USER_GROUP_LIST = "4";
	public static String DELETE_USER_GROUP = "5";

	public static String CREATE_USER = "10";
	public static String GET_USER = "11";
	public static String UPDATE_USER = "12";
	public static String GET_USER_LIST = "13";
	public static String GET_USER_IMG = "14";
	public static String GET_USER_BY_EMAIL = "15";
	public static String CHECK_USER_BY_USER_NAME = "16";
	public static String CHECK_USER_PHONE = "17";

	public static String GET_TOKEN = "20";
	public static String UPDATE_TOKEN = "21";
	public static String DELETE_TOKEN = "22";

	public static void init() {

		HttpManager.addMapping(UC_ERROR, null, UCError.class);
		HttpManager.addMapping(CREATE_USER_GROUP, CreateUserGroupC.class, CreateUserGroupS.class);
		HttpManager.addMapping(UPDATE_USER_GROUP, UpdateUserGroupC.class, UpdateUserGroupS.class);
		HttpManager.addMapping(GET_USER_GROUP, GetUserGroupC.class, GetUserGroupS.class);
		HttpManager.addMapping(GET_USER_GROUP_LIST, GetUserGroupListC.class, GetUserGroupListS.class);
		HttpManager.addMapping(DELETE_USER_GROUP, DeleteUserGroupC.class, DeleteUserGroupS.class);
		HttpManager.addMapping(CREATE_USER, CreateUserC.class, CreateUserS.class);
		HttpManager.addMapping(GET_USER, GetUserC.class, GetUserS.class);
		HttpManager.addMapping(UPDATE_USER, UpdateUserC.class, UpdateUserS.class);
		HttpManager.addMapping(GET_USER_LIST, GetUserListC.class, GetUserListS.class);
		HttpManager.addMapping(GET_USER_IMG, GetUserImgC.class, null);
		HttpManager.addMapping(GET_USER_BY_EMAIL, GetUserByEmailC.class, GetUserByEmailS.class);
		HttpManager.addMapping(CHECK_USER_BY_USER_NAME, CheckUserByUserNameC.class, CheckUserByUserNameS.class);
		HttpManager.addMapping(CHECK_USER_PHONE, CheckUserPhoneC.class, CheckUserPhoneS.class);
		HttpManager.addMapping(GET_TOKEN, GetTokenC.class, GetTokenS.class);
		HttpManager.addMapping(UPDATE_TOKEN, UpdateTokenC.class, UpdateTokenS.class);
		HttpManager.addMapping(DELETE_TOKEN, DeleteTokenC.class, DeleteTokenS.class);

	}
}
