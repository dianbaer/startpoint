package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.UserGroupAction;
import dao.model.base.UserGroup;
import http.HOpCodeUCenter;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import protobuf.http.UserGroupProto.CreateUserGroupC;
import protobuf.http.UserGroupProto.CreateUserGroupS;
import protobuf.http.UserGroupProto.GetUserGroupC;
import protobuf.http.UserGroupProto.GetUserGroupListC;
import protobuf.http.UserGroupProto.GetUserGroupListS;
import protobuf.http.UserGroupProto.GetUserGroupS;
import protobuf.http.UserGroupProto.UpdateUserGroupC;
import protobuf.http.UserGroupProto.UpdateUserGroupS;

public class UserGroupService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.CREATE_USER_GROUP, "createUserGroupHandle");
		map.put(HOpCodeUCenter.UPDATE_USER_GROUP, "updateUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP, "getUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP_LIST, "getUserGroupListHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket createUserGroupHandle(HSession hSession) {
		CreateUserGroupC message = (CreateUserGroupC) hSession.httpPacket.getData();
		UserGroup userGroup = UserGroupAction.createUserGroup(message.getUserGroupName(), message.getUserGroupParentId());
		if (userGroup == null) {
			return null;
		}
		CreateUserGroupS.Builder builder = CreateUserGroupS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket updateUserGroupHandle(HSession hSession) {
		UpdateUserGroupC message = (UpdateUserGroupC) hSession.httpPacket.getData();
		UserGroup userGroup = UserGroupAction.updateUserGroup(message.getUserGroupId(), message.getUserGroupName(), message.getIsUpdateUserGroupParent(), message.getUserGroupParentId(), message.getUserGroupState());
		if (userGroup == null) {
			return null;
		}
		UpdateUserGroupS.Builder builder = UpdateUserGroupS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserGroupHandle(HSession hSession) {
		GetUserGroupC message = (GetUserGroupC) hSession.httpPacket.getData();

		UserGroup userGroup = UserGroupAction.getUserGroupById(message.getUserGroupId());
		if (userGroup == null) {
			return null;
		}

		GetUserGroupS.Builder builder = GetUserGroupS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserGroupListHandle(HSession hSession) {
		GetUserGroupListC message = (GetUserGroupListC) hSession.httpPacket.getData();

		List<UserGroup> userGroupList = UserGroupAction.getUserGroupList(message.getUserGroupParentId(), message.getIsUserGroupParentIsNull(), message.getIsRecursion(), message.getUserGroupTopId(), message.getUserGroupState());
		GetUserGroupListS.Builder builder = GetUserGroupListS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);

		if (userGroupList != null) {
			for (int i = 0; i < userGroupList.size(); i++) {
				UserGroup userGroup = userGroupList.get(i);
				builder.addUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
			}
		}

		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
