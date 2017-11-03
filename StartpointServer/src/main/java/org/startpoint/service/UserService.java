package org.startpoint.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grain.httpserver.HttpException;
import org.grain.httpserver.HttpPacket;
import org.grain.httpserver.IHttpListener;
import org.grain.httpserver.ReplyImage;
import org.startpoint.action.UCErrorPack;
import org.startpoint.action.UserAction;
import org.startpoint.http.HOpCodeUCenter;
import org.startpoint.model.base.User;
import org.startpoint.protobuf.http.UCErrorProto.UCError;
import org.startpoint.protobuf.http.UCErrorProto.UCErrorCode;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserByUserNameC;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserByUserNameS;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserPhoneC;
import org.startpoint.protobuf.http.UserGroupProto.CheckUserPhoneS;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserC;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserByEmailC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserByEmailS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserImgC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserListC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserListS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserS;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserC;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserS;
import org.startpoint.protobuf.http.UserGroupProto.UserData;
import org.startpoint.tool.PageFormat;
import org.startpoint.tool.PageObj;
import org.startpoint.tool.StringUtil;

public class UserService implements IHttpListener {

	@Override
	public Map<String, String> getHttps() {
		HashMap<String, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.CREATE_USER, "createUserHandle");
		map.put(HOpCodeUCenter.GET_USER, "getUserHandle");
		map.put(HOpCodeUCenter.UPDATE_USER, "updateUserHandle");
		map.put(HOpCodeUCenter.GET_USER_LIST, "getUserListHandle");
		map.put(HOpCodeUCenter.GET_USER_IMG, "getUserImgHandle");
		map.put(HOpCodeUCenter.GET_USER_BY_EMAIL, "getUserByEmailHandle");
		map.put(HOpCodeUCenter.CHECK_USER_BY_USER_NAME, "checkUserByUserNameHandle");
		map.put(HOpCodeUCenter.CHECK_USER_PHONE, "checkUserPhone");
		return map;
	}

	public HttpPacket createUserHandle(HttpPacket httpPacket) throws HttpException {
		CreateUserC message = (CreateUserC) httpPacket.getData();
		User userbyphone = UserAction.getUserByUserPhone(message.getUserPhone());
		if (userbyphone != null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_3, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		User user = UserAction.createUser(message.getUserName(), message.getUserPassword(), message.getUserPhone(), message.getUserEmail(), message.getUserGroupId(), message.getUserRealName(), message.getUserSex(), message.getUserAge(), message.getUserRole());
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_13, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		if (httpPacket.fileList != null && httpPacket.fileList.size() > 0) {
			String oldName = user.getUserImg();
			String userImg = UserAction.saveUserImg(httpPacket.fileList.get(0).getFile());
			if (userImg != null) {
				user = UserAction.updateUser(user.getUserId(), null, null, null, 0, false, null, null, 0, 0, 0, userImg);
				if (user == null) {
					UserAction.deleteUserImg(userImg);
				} else {
					if (oldName != null) {
						UserAction.deleteUserImg(oldName);
					}
				}
			}
		}

		CreateUserS.Builder builder = CreateUserS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user, httpPacket.hSession.headParam.token));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserHandle(HttpPacket httpPacket) throws HttpException {
		GetUserC message = (GetUserC) httpPacket.getData();
		User user;
		if (StringUtil.stringIsNull(message.getUserId())) {
			user = (User) httpPacket.hSession.otherData;
		} else {
			user = UserAction.getUserById(message.getUserId());

		}
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_4, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		GetUserS.Builder builder = GetUserS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user, httpPacket.hSession.headParam.token));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserByEmailHandle(HttpPacket httpPacket) throws HttpException {
		GetUserByEmailC message = (GetUserByEmailC) httpPacket.getData();
		User user = UserAction.getUserByEmail(message.getUserEmail());

		GetUserByEmailS.Builder builder = GetUserByEmailS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		if (user == null) {
			builder.setUser(UserData.newBuilder());
		} else {
			builder.setUser(UserAction.getUserDataBuilder(user, httpPacket.hSession.headParam.token));
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket updateUserHandle(HttpPacket httpPacket) throws HttpException {
		UpdateUserC message = (UpdateUserC) httpPacket.getData();
		User user = UserAction.updateUser(message.getUserId(), message.getUserPassword(), message.getUserPhone(), message.getUserEmail(), message.getUserState(), message.getIsUpdateUserGroup(), message.getUserGroupId(), message.getUserRealName(), message.getUserSex(), message.getUserAge(), message.getUserRole(), null);
		if (user == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_14, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		if (httpPacket.fileList != null && httpPacket.fileList.size() > 0) {
			String oldName = user.getUserImg();
			String userImg = UserAction.saveUserImg(httpPacket.fileList.get(0).getFile());
			if (userImg != null) {
				user = UserAction.updateUser(user.getUserId(), null, null, null, 0, false, null, null, 0, 0, 0, userImg);
				if (user == null) {
					UserAction.deleteUserImg(userImg);
				} else {
					if (oldName != null) {
						UserAction.deleteUserImg(oldName);
					}
				}
			}
		}
		UpdateUserS.Builder builder = UpdateUserS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user, httpPacket.hSession.headParam.token));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserListHandle(HttpPacket httpPacket) {
		GetUserListC message = (GetUserListC) httpPacket.getData();
		List<User> userList = UserAction.getUserList(message.getUserGroupId(), message.getIsRecursion(), message.getIsUserGroupIsNull(), message.getUserState(), message.getUserSex(), message.getUserRole(), message.getUserGroupTopId(), message.getUserName(), message.getUserCreateTimeGreaterThan(), message.getUserCreateTimeLessThan(), message.getUserUpdateTimeGreaterThan(), message.getUserUpdateTimeLessThan());
		int currentPage = message.getCurrentPage();
		int pageSize = message.getPageSize();
		PageObj pageObj = PageFormat.getStartAndEnd(currentPage, pageSize, userList.size());
		GetUserListS.Builder builder = GetUserListS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setCurrentPage(pageObj.currentPage);
		builder.setPageSize(pageObj.pageSize);
		builder.setTotalPage(pageObj.totalPage);
		builder.setAllNum(pageObj.allNum);
		if (userList != null) {
			for (int i = pageObj.start; i < pageObj.end; i++) {
				User user = userList.get(i);
				builder.addUser(UserAction.getUserDataBuilder(user, httpPacket.hSession.headParam.token));
			}
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket checkUserByUserNameHandle(HttpPacket httpPacket) {
		CheckUserByUserNameC message = (CheckUserByUserNameC) httpPacket.getData();
		User user = UserAction.getUserByName(message.getUserName());
		CheckUserByUserNameS.Builder builder = CheckUserByUserNameS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		if (user == null) {
			builder.setExist(false);
		} else {
			builder.setExist(true);
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket checkUserPhone(HttpPacket httpPacket) {
		CheckUserPhoneC message = (CheckUserPhoneC) httpPacket.getData();
		User user = UserAction.getUserByUserPhone(message.getUserPhone());
		CheckUserPhoneS.Builder builder = CheckUserPhoneS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		if (user == null) {
			builder.setExist(false);
		} else {
			builder.setExist(true);
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public ReplyImage getUserImgHandle(HttpPacket httpPacket) {
		GetUserImgC message = (GetUserImgC) httpPacket.getData();
		User user;
		if (StringUtil.stringIsNull(message.getUserId())) {
			user = (User) httpPacket.hSession.otherData;
		} else {
			user = UserAction.getUserById(message.getUserId());

		}
		if (user == null) {
			return null;
		}
		File file = UserAction.getUserImg(user.getUserImg());
		ReplyImage replyImage = new ReplyImage(file);
		return replyImage;
	}

	public UserService() {
		UserAction.createUserImgDir();
	}
}
