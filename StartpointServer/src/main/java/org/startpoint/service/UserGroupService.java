package org.startpoint.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grain.httpserver.HttpException;
import org.grain.httpserver.HttpPacket;
import org.grain.httpserver.IHttpListener;
import org.startpoint.action.UCErrorPack;
import org.startpoint.action.UserGroupAction;
import org.startpoint.config.UserConfig;
import org.startpoint.http.HOpCodeUCenter;
import org.startpoint.model.base.User;
import org.startpoint.model.base.UserGroup;
import org.startpoint.model.ext.UserGroupExt;
import org.startpoint.protobuf.http.UCErrorProto.UCError;
import org.startpoint.protobuf.http.UCErrorProto.UCErrorCode;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.CreateUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.DeleteUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.DeleteUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupListC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupListS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupRecursionC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupRecursionS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupS;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupTreeC;
import org.startpoint.protobuf.http.UserGroupProto.GetUserGroupTreeS;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserGroupC;
import org.startpoint.protobuf.http.UserGroupProto.UpdateUserGroupS;
import org.startpoint.tool.PageFormat;
import org.startpoint.tool.PageObj;
import org.startpoint.tool.StringUtil;

public class UserGroupService implements IHttpListener {

	@Override
	public Map<String, String> getHttps() {
		HashMap<String, String> map = new HashMap<>();
		map.put(HOpCodeUCenter.CREATE_USER_GROUP, "createUserGroupHandle");
		map.put(HOpCodeUCenter.UPDATE_USER_GROUP, "updateUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP, "getUserGroupHandle");
		map.put(HOpCodeUCenter.DELETE_USER_GROUP, "deleteUserGroupHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP_LIST, "getUserGroupListHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP_TREE, "getUserGroupTreeHandle");
		map.put(HOpCodeUCenter.GET_USER_GROUP_RECURSION, "getUserGroupRecursionHandle");
		return map;
	}

	public HttpPacket createUserGroupHandle(HttpPacket httpPacket) throws HttpException {
		CreateUserGroupC message = (CreateUserGroupC) httpPacket.getData();
		/********** 权限验证开始 ***********/
		User user = (User) httpPacket.hSession.otherData;
		if (user.getUserRole().intValue() != UserConfig.ROLE_UCENTER_MANAGER) {
			// 如果是顶级组必须是管理员身份
			if (StringUtil.stringIsNull(message.getUserGroupParentId())) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			} else {
				// 如果不是顶级组，必须是组管理员
				if (user.getUserRole().intValue() != UserConfig.ROLE_GROUP_MANAGER) {
					UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
					throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
				}
				// 必须属于这个组
				UserGroup parentUserGroup = UserGroupAction.getUserGroupById(message.getUserGroupParentId());
				if (parentUserGroup == null) {
					UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_0, httpPacket.hSession.headParam.hOpCode);
					throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
				}
				String userGroupTopId;
				if (StringUtil.stringIsNull(parentUserGroup.getUserGroupTopId())) {
					userGroupTopId = parentUserGroup.getUserGroupId();
				} else {
					userGroupTopId = parentUserGroup.getUserGroupTopId();
				}
				if (!userGroupTopId.equals(user.getUserGroupTopId())) {
					UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
					throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
				}
			}
		}
		/********** 权限验证结束 ***********/
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
		/********** 权限验证开始 ***********/
		User user = (User) httpPacket.hSession.otherData;
		if (user.getUserRole().intValue() != UserConfig.ROLE_UCENTER_MANAGER) {
			// 如果不是顶级组，必须是组管理员
			if (user.getUserRole().intValue() != UserConfig.ROLE_GROUP_MANAGER) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
			// 必须属于这个组
			UserGroup userGroupCheck = UserGroupAction.getUserGroupById(message.getUserGroupId());
			if (userGroupCheck == null) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_0, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
			String userGroupTopId;
			if (StringUtil.stringIsNull(userGroupCheck.getUserGroupTopId())) {
				// 组管理员不能修改顶级组
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			} else {
				userGroupTopId = userGroupCheck.getUserGroupTopId();
			}
			if (!userGroupTopId.equals(user.getUserGroupTopId())) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
		}
		/********** 权限验证结束 ***********/
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
		/********** 权限验证开始 ***********/
		User user = (User) httpPacket.hSession.otherData;
		if (user.getUserRole().intValue() != UserConfig.ROLE_UCENTER_MANAGER) {
			// 如果不是顶级组，必须是组管理员
			if (user.getUserRole().intValue() != UserConfig.ROLE_GROUP_MANAGER) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
			// 必须属于这个组
			UserGroup userGroupCheck = UserGroupAction.getUserGroupById(message.getUserGroupId());
			if (userGroupCheck == null) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_0, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
			String userGroupTopId;
			if (StringUtil.stringIsNull(userGroupCheck.getUserGroupTopId())) {
				// 组管理员不能修改顶级组
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			} else {
				userGroupTopId = userGroupCheck.getUserGroupTopId();
			}
			if (!userGroupTopId.equals(user.getUserGroupTopId())) {
				UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_17, httpPacket.hSession.headParam.hOpCode);
				throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
			}
		}
		/********** 权限验证结束 ***********/
		UserGroup userGroup = UserGroupAction.getUserGroupById(message.getUserGroupId());
		boolean result = UserGroupAction.deleteUserGroup(message.getUserGroupId());
		if (!result) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_15, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		DeleteUserGroupS.Builder builder = DeleteUserGroupS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		builder.setUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
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

	public HttpPacket getUserGroupTreeHandle(HttpPacket httpPacket) throws HttpException {
		GetUserGroupTreeC message = (GetUserGroupTreeC) httpPacket.getData();
		List<UserGroupExt> userGroupList = UserGroupAction.getUserGroupTree(message.getUserGroupParentId());
		if (userGroupList == null) {
			UCError errorPack = UCErrorPack.create(UCErrorCode.ERROR_CODE_15, httpPacket.hSession.headParam.hOpCode);
			throw new HttpException(HOpCodeUCenter.UC_ERROR, errorPack);
		}
		GetUserGroupTreeS.Builder builder = GetUserGroupTreeS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		for (int i = 0; i < userGroupList.size(); i++) {
			UserGroupExt userGroup = userGroupList.get(i);
			builder.addUserGroup(UserGroupAction.getUserGroupDataBuilder(userGroup));
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket getUserGroupRecursionHandle(HttpPacket httpPacket) throws HttpException {
		GetUserGroupRecursionC message = (GetUserGroupRecursionC) httpPacket.getData();
		List<UserGroup> userGroupList = UserGroupAction.getRecursionUserGroupList(message.getUserGroupId());
		GetUserGroupRecursionS.Builder builder = GetUserGroupRecursionS.newBuilder();
		builder.setHOpCode(httpPacket.hSession.headParam.hOpCode);
		for (int i = 0; i < userGroupList.size(); i++) {
			UserGroup userGroup = userGroupList.get(i);
			builder.addRecursionUserGroupList(UserGroupAction.getUserGroupDataBuilder(userGroup));
		}
		HttpPacket packet = new HttpPacket(httpPacket.hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
