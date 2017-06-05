package service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.UserAction;
import dao.model.base.User;
import http.HOpCodeUCenter;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.filter.FileData;
import protobuf.http.UserGroupProto.CheckUserByUserNameC;
import protobuf.http.UserGroupProto.CheckUserByUserNameS;
import protobuf.http.UserGroupProto.CreateUserC;
import protobuf.http.UserGroupProto.CreateUserS;
import protobuf.http.UserGroupProto.GetUserByEmailC;
import protobuf.http.UserGroupProto.GetUserByEmailS;
import protobuf.http.UserGroupProto.GetUserC;
import protobuf.http.UserGroupProto.GetUserImgC;
import protobuf.http.UserGroupProto.GetUserListC;
import protobuf.http.UserGroupProto.GetUserListS;
import protobuf.http.UserGroupProto.GetUserS;
import protobuf.http.UserGroupProto.UpdateUserC;
import protobuf.http.UserGroupProto.UpdateUserS;
import protobuf.http.UserGroupProto.UserData;
import tool.PageFormat;
import tool.PageObj;
import tool.StringUtil;

public class UserService implements IHttpListener, IService {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.CREATE_USER, "createUserHandle");
		map.put(HOpCodeUCenter.GET_USER, "getUserHandle");
		map.put(HOpCodeUCenter.UPDATE_USER, "updateUserHandle");
		map.put(HOpCodeUCenter.GET_USER_LIST, "getUserListHandle");
		map.put(HOpCodeUCenter.GET_USER_IMG, "getUserImgHandle");
		// dingwancheng start
		map.put(HOpCodeUCenter.GET_USER_BY_EMAIL, "getUserByEmailHandle");
		// dingwancheng end
		map.put(HOpCodeUCenter.CHECK_USER_BY_USER_NAME, "checkUserByUserNameHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket createUserHandle(HSession hSession) {
		CreateUserC message = (CreateUserC) hSession.httpPacket.getData();
		User user = UserAction.createUser(message.getUserName(), message.getUserPassword(), message.getUserPhone(), message.getUserEmail(), message.getUserGroupId(), message.getUserRealName(), message.getUserSex(), message.getUserAge(), message.getUserRole());
		if (user == null) {
			return null;
		}
		if (hSession.fileList != null && hSession.fileList.size() > 0) {
			String oldName = user.getUserImg();
			String userImg = UserAction.saveUserImg(hSession.fileList.get(0).getFile());
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
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserHandle(HSession hSession) {
		GetUserC message = (GetUserC) hSession.httpPacket.getData();
		User user;
		if (StringUtil.stringIsNull(message.getUserId())) {
			user = (User) hSession.otherData;
		} else {
			user = UserAction.getUserById(message.getUserId());

		}
		if (user == null) {
			return null;
		}
		GetUserS.Builder builder = GetUserS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	// dingwancheng start
	public HttpPacket getUserByEmailHandle(HSession hSession) {
		GetUserByEmailC message = (GetUserByEmailC) hSession.httpPacket.getData();
		User user = UserAction.getUserByEmail(message.getUserEmail());

		GetUserByEmailS.Builder builder = GetUserByEmailS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		if (user == null) {
			builder.setUser(UserData.newBuilder());
		} else {
			builder.setUser(UserAction.getUserDataBuilder(user));
		}
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}
	// dingwancheng end

	public HttpPacket updateUserHandle(HSession hSession) {
		UpdateUserC message = (UpdateUserC) hSession.httpPacket.getData();
		User user = UserAction.updateUser(message.getUserId(), message.getUserPassword(), message.getUserPhone(), message.getUserEmail(), message.getUserState(), message.getIsUpdateUserGroup(), message.getUserGroupId(), message.getUserRealName(), message.getUserSex(), message.getUserAge(), message.getUserRole(), null);
		if (user == null) {
			return null;
		}
		if (hSession.fileList != null && hSession.fileList.size() > 0) {
			String oldName = user.getUserImg();
			String userImg = UserAction.saveUserImg(hSession.fileList.get(0).getFile());
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
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUser(UserAction.getUserDataBuilder(user));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserListHandle(HSession hSession) {
		GetUserListC message = (GetUserListC) hSession.httpPacket.getData();
		List<User> userList = UserAction.getUserList(message.getUserGroupId(), message.getIsRecursion(), message.getIsUserGroupIsNull(), message.getUserState(), message.getUserSex(), message.getUserRole(), message.getUserGroupTopId(), message.getUserName(), message.getUserCreateTimeGreaterThan(), message.getUserCreateTimeLessThan());
		int currentPage = message.getCurrentPage();
		int pageSize = message.getPageSize();
		PageObj pageObj = PageFormat.getStartAndEnd(currentPage, pageSize, userList.size());
		GetUserListS.Builder builder = GetUserListS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setCurrentPage(pageObj.currentPage);
		builder.setPageSize(pageObj.pageSize);
		builder.setTotalPage(pageObj.totalPage);
		builder.setAllNum(pageObj.allNum);
		if (userList != null) {
			for (int i = pageObj.start; i < pageObj.end; i++) {
				User user = userList.get(i);
				builder.addUser(UserAction.getUserDataBuilder(user));
			}
		}
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket checkUserByUserNameHandle(HSession hSession) {
		CheckUserByUserNameC message = (CheckUserByUserNameC) hSession.httpPacket.getData();
		User user = UserAction.getUserByName(message.getUserName());
		CheckUserByUserNameS.Builder builder = CheckUserByUserNameS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		if (user == null) {
			builder.setExist(false);
		} else {
			builder.setExist(true);
		}
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public FileData getUserImgHandle(HSession hSession) {
		GetUserImgC message = (GetUserImgC) hSession.httpPacket.getData();
		User user;
		if (StringUtil.stringIsNull(message.getUserId())) {
			user = (User) hSession.otherData;
		} else {
			user = UserAction.getUserById(message.getUserId());

		}
		if (user == null) {
			return null;
		}
		File file = UserAction.getUserImg(user.getUserImg());
		FileData fileData = new FileData(file, file.getName());
		return fileData;
	}

	@Override
	public void init() throws Exception {
		UserAction.createUserImgDir();
	}
}
