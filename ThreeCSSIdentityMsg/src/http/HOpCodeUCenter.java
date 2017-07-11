package http;

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

public class HOpCodeUCenter extends HOpCode {
	public static int UC_ERROR = 0;

	public static int CREATE_USER_GROUP = 1;
	public static int UPDATE_USER_GROUP = 2;
	public static int GET_USER_GROUP = 3;
	public static int GET_USER_GROUP_LIST = 4;
	public static int DELETE_USER_GROUP = 5;

	public static int CREATE_USER = 10;
	public static int GET_USER = 11;
	public static int UPDATE_USER = 12;
	public static int GET_USER_LIST = 13;
	public static int GET_USER_IMG = 14;
	public static int GET_USER_BY_EMAIL = 15;
	public static int CHECK_USER_BY_USER_NAME = 16;
	public static int CHECK_USER_PHONE = 17;

	public static int GET_TOKEN = 20;
	public static int UPDATE_TOKEN = 21;
	public static int DELETE_TOKEN = 22;

	public static void init() {

		Class<?>[] sendAndReturn = new Class[2];
		sendAndReturn[0] = null;
		sendAndReturn[1] = UCError.class;
		hOpCodeMap.put(UC_ERROR, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = CreateUserGroupC.class;
		sendAndReturn[1] = CreateUserGroupS.class;
		hOpCodeMap.put(CREATE_USER_GROUP, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UpdateUserGroupC.class;
		sendAndReturn[1] = UpdateUserGroupS.class;
		hOpCodeMap.put(UPDATE_USER_GROUP, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserGroupC.class;
		sendAndReturn[1] = GetUserGroupS.class;
		hOpCodeMap.put(GET_USER_GROUP, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserGroupListC.class;
		sendAndReturn[1] = GetUserGroupListS.class;
		hOpCodeMap.put(GET_USER_GROUP_LIST, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = DeleteUserGroupC.class;
		sendAndReturn[1] = DeleteUserGroupS.class;
		hOpCodeMap.put(DELETE_USER_GROUP, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = CreateUserC.class;
		sendAndReturn[1] = CreateUserS.class;
		hOpCodeMap.put(CREATE_USER, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserC.class;
		sendAndReturn[1] = GetUserS.class;
		hOpCodeMap.put(GET_USER, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UpdateUserC.class;
		sendAndReturn[1] = UpdateUserS.class;
		hOpCodeMap.put(UPDATE_USER, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserListC.class;
		sendAndReturn[1] = GetUserListS.class;
		hOpCodeMap.put(GET_USER_LIST, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserImgC.class;
		sendAndReturn[1] = null;
		hOpCodeMap.put(GET_USER_IMG, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserByEmailC.class;
		sendAndReturn[1] = GetUserByEmailS.class;
		hOpCodeMap.put(GET_USER_BY_EMAIL, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = CheckUserByUserNameC.class;
		sendAndReturn[1] = CheckUserByUserNameS.class;
		hOpCodeMap.put(CHECK_USER_BY_USER_NAME, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = CheckUserPhoneC.class;
		sendAndReturn[1] = CheckUserPhoneS.class;
		hOpCodeMap.put(CHECK_USER_PHONE, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetTokenC.class;
		sendAndReturn[1] = GetTokenS.class;
		hOpCodeMap.put(GET_TOKEN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UpdateTokenC.class;
		sendAndReturn[1] = UpdateTokenS.class;
		hOpCodeMap.put(UPDATE_TOKEN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = DeleteTokenC.class;
		sendAndReturn[1] = DeleteTokenS.class;
		hOpCodeMap.put(DELETE_TOKEN, sendAndReturn);

	}
}
