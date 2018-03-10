package org.startpoint.http;

import org.grain.httpserver.HttpManager;
import org.startpoint.protobuf.http.UCErrorProto.UCError;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserByUserNameC;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserByUserNameS;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserPhoneC;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserPhoneS;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserC;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserS;
import org.startpoint.protobuf.http.UserGroupProto.DeleteTokenC;
import org.startpoint.protobuf.http.UserGroupProto.DeleteTokenS;
import org.startpoint.protobuf.http.UserGroupProto.DeleteUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.DeleteUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.GetTokenC;
import org.startpoint.protobuf.http.UserGroupProto.GetTokenS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserByEmailC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserByEmailS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupListC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupListS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupRecursionC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupRecursionS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupTreeC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupTreeS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserImgC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserListC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserListS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserS;
import org.startpoint.protobuf.http.UserGroupProto.UpdateTokenC;
import org.startpoint.protobuf.http.UserGroupProto.UpdateTokenS;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserC;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserS;

public class HOpCodeUCenter {
	public static String UC_ERROR = "0";

	public static String CREATE_USER_GROUP = "1";
	public static String UPDATE_USER_GROUP = "2";
	public static String GET_USER_GROUP = "3";
	public static String GET_USER_GROUP_LIST = "4";
	public static String DELETE_USER_GROUP = "5";
	public static String GET_USER_GROUP_TREE = "6";
	public static String GET_USER_GROUP_RECURSION = "7";

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
	public static String GET_ADMIN_TOKEN = "23";

	public static void init() {

		HttpManager.addMapping(UC_ERROR, null, UCError.class);
		HttpManager.addMapping(CREATE_USER_GROUP, CreateUserGroupC.class, CreateUserGroupS.class);
		HttpManager.addMapping(UPDATE_USER_GROUP, UpdateUserGroupC.class, UpdateUserGroupS.class);
		HttpManager.addMapping(GET_USER_GROUP, GetUserGroupC.class, GetUserGroupS.class);
		HttpManager.addMapping(GET_USER_GROUP_LIST, GetUserGroupListC.class, GetUserGroupListS.class);
		HttpManager.addMapping(DELETE_USER_GROUP, DeleteUserGroupC.class, DeleteUserGroupS.class);
		HttpManager.addMapping(GET_USER_GROUP_TREE, GetUserGroupTreeC.class, GetUserGroupTreeS.class);
		HttpManager.addMapping(GET_USER_GROUP_RECURSION, GetUserGroupRecursionC.class, GetUserGroupRecursionS.class);
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
		HttpManager.addMapping(GET_ADMIN_TOKEN, GetTokenC.class, GetTokenS.class);

	}
}
