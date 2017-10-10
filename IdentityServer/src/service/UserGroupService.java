package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grain.httpserver.HttpException;
import org.grain.httpserver.HttpPacket;
import org.grain.httpserver.IHttpListener;

import action.UCErrorPack;
import action.UserGroupAction;
import dao.model.base.UserGroup;
import http.HOpCodeUCenter;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UCErrorProto.UCErrorCode;
import protobuf.http.UserGroupProto.CreateUserGroupC;
import protobuf.http.UserGroupProto.CreateUserGroupS;
import protobuf.http.UserGroupProto.DeleteUserGroupC;
import protobuf.http.UserGroupProto.DeleteUserGroupS;
import protobuf.http.UserGroupProto.GetUserGroupC;
import protobuf.http.UserGroupProto.GetUserGroupListC;
import protobuf.http.UserGroupProto.GetUserGroupListS;
import protobuf.http.UserGroupProto.GetUserGroupS;
import protobuf.http.UserGroupProto.UpdateUserGroupC;
import protobuf.http.UserGroupProto.UpdateUserGroupS;
import tool.PageFormat;
import tool.PageObj;

public class UserGroupService implements IHttpListener {

	@Override
	public Map<String, String> getHttps() {
		HashMap<String, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.CREATE_USER_GROUP, "createUserGroupHandle");
		map.put(HOpCodeUCenter.UPDATE_USER_GROUP, "updateUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP, "getUserGroupHandle");
		map.put(HOpCodeUCenter.DELETE_USER_GROUP, "deleteUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP_LIST, "getUserGroupListHandle");
		return map;
	}

	public HttpPacket createUserGroupHandle(HttpPacket httpPacket) throws HttpException {
		CreateUserGroupC message = (CreateUserGroupC) httpPacket.getData();
		UserGroup userGroup = UserGroupAction.createUserGroup(message.getUserGroupName(), message.getUserGroupParentId());
		if (userGroup == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_10, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		CreateUserGroupS.Builder builder = CreateUserGroupS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket updateUserGroupHandle(HttpPacket httpPacket) throws HttpException {
		UpdateUserGroupC message = (UpdateUserGroupC) httpPacket.getData();
		UserGroup userGroup = UserGroupAction.updateUserGroup(message.getUserGroupId(), message.getUserGroupName(), message.getIsUpdateUserGroupParent(), message.getUserGroupParentId(), message.getUserGroupState());
		if (userGroup == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_11, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		UpdateUserGroupS.Builder builder = UpdateUserGroupS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserGroupHandle(HttpPacket httpPacket) throws HttpException {
		GetUserGroupC message = (GetUserGroupC) httpPacket.getData();

		UserGroup userGroup = UserGroupAction.getUserGroupById(message.getUserGroupId());
		if (userGroup == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_12, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}

		GetUserGroupS.Builder builder = GetUserGroupS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket deleteUserGroupHandle(HttpPacket httpPacket) throws HttpException {
		DeleteUserGroupC message = (DeleteUserGroupC) httpPacket.getData();
		boolean result = UserGroupAction.deleteUserGroup(message.getUserGroupId());
		if (!result) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_15, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		DeleteUserGroupS.Builder builder = DeleteUserGroupS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserGroupListHandle(HttpPacket httpPacket) throws HttpException {
		GetUserGroupListC message = (GetUserGroupListC) httpPacket.getData();

		List<UserGroup> userGroupList = UserGroupAction.getUserGroupList(message.getUserGroupParentId(), message.getIsUserGroupParentIsNull(), message.getIsRecursion(), message.getUserGroupTopId(), message.getUserGroupState(), message.getUserGroupCreateTimeGreaterThan(), message.getUserGroupCreateTimeLessThan(), message.getUserGroupUpdateTimeGreaterThan(), message.getUserGroupUpdateTimeLessThan());
		int currentPage = message.getCurrentPage();
		int pageSize = message.getPageSize();
		PageObj pageObj = PageFormat.getStartAndEnd(currentPage, pageSize, userGroupList.size());
		GetUserGroupListS.Builder builder = GetUserGroupListS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setCurrentPage(pageObj.currentPage);
		builder.setPageSize(pageObj.pageSize);
		builder.setTotalPage(pageObj.totalPage);
		builder.setAllNum(pageObj.allNum);
		if (userGroupList != null) {
			for (int i = pageObj.start; i < pageObj.end; i++) {
				UserGroup userGroup = userGroupList.get(i);
				builder.addUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
			}
		}

		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
