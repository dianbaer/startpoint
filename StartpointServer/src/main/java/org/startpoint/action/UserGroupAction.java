package org.startpoint.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.grain.mariadb.MybatisManager;
import org.grain.threadkeylock.KeyLockManager;
import org.startpoint.config.UserConfig;
import org.startpoint.config.UserGroupConfig;
import org.startpoint.dao.base.UserGroupMapper;
import org.startpoint.dao.base.UserMapper;
import org.startpoint.keylock.UCenterKeyLockType;
import org.startpoint.model.base.User;
import org.startpoint.model.base.UserCriteria;
import org.startpoint.model.base.UserGroup;
import org.startpoint.model.base.UserGroupCriteria;
import org.startpoint.protobuf.http.UserGroupProto.UserGroupData;
import org.startpoint.tool.StringUtil;
import org.startpoint.tool.TimeUtils;
import org.startpoint.util.IdUtil;

public class UserGroupAction {
	public static UserGroup createUserGroup(String userGroupName, String userGroupParentId) {
		if (StringUtil.stringIsNull(userGroupName)) {
			return null;
		}
		Date date = new Date();
		UserGroup userGroup = new UserGroup();
		userGroup.setUserGroupId(IdUtil.getUuid());
		userGroup.setUserGroupName(userGroupName);
		userGroup.setUserGroupCreateTime(date);
		userGroup.setUserGroupUpdateTime(date);
		userGroup.setUserGroupState((byte) UserGroupConfig.STATE_USABLE);
		if (StringUtil.stringIsNull(userGroupParentId)) {
			return createUserGroup(userGroup, null);
		} else {
			UserGroup parentUserGroup = getUserGroupById(userGroupParentId);
			if (parentUserGroup == null) {
				return null;
			}
			if (StringUtil.stringIsNull(parentUserGroup.getUserGroupTopId())) {
				userGroup.setUserGroupTopId(parentUserGroup.getUserGroupId());
			} else {
				userGroup.setUserGroupTopId(parentUserGroup.getUserGroupTopId());
			}
			userGroup.setUserGroupParentId(parentUserGroup.getUserGroupId());

			/************************ 锁树开始 ************************/
			return (UserGroup) KeyLockManager.lockMethod(userGroup.getUserGroupTopId(), UCenterKeyLockType.USER_GROUP, (params) -> createUserGroup(params), new Object[] { userGroup, userGroupParentId });

			/************************ 锁树结束 ************************/

		}

	}

	public static UserGroup createUserGroup(Object... params) {
		UserGroup userGroup = (UserGroup) params[0];
		String userGroupParentId = (String) params[1];
		// 不得空判断这棵树是否改变了
		if (userGroupParentId != null) {
			UserGroup userGroupNew = UserGroupAction.getUserGroupById(userGroupParentId);
			if (userGroupNew == null) {
				return null;
			}
			String userGroupTopId;
			if (StringUtil.stringIsNull(userGroupNew.getUserGroupTopId())) {
				userGroupTopId = userGroupNew.getUserGroupId();
			} else {
				userGroupTopId = userGroupNew.getUserGroupTopId();
			}
			// 判断锁的树还是这颗树吗？不是就说明树形结构已变化
			if (!userGroupTopId.equals(userGroup.getUserGroupTopId())) {
				return null;
			}
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			int result = userGroupMapper.insert(userGroup);
			if (result != 1) {
				MybatisManager.log.warn("新增用户组失败");
				return null;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			MybatisManager.log.error("创建用户组异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userGroup;
	}

	public static UserGroup getUserGroupById(String userGroupId) {
		if (StringUtil.stringIsNull(userGroupId)) {
			return null;
		}
		SqlSession sqlSession = null;
		UserGroup userGroup;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			userGroup = userGroupMapper.selectByPrimaryKey(userGroupId);
			if (userGroup == null) {
				MybatisManager.log.warn("通过userGroupId:" + userGroupId + "获取用户组为空");
			}
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			MybatisManager.log.error("获取用户组异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userGroup;
	}

	public static UserGroup updateUserGroup(String userGroupId, String userGroupName, boolean isUpdateUserGroupParent, String userGroupParentId, int userGroupState) {
		if (StringUtil.stringIsNull(userGroupId)) {
			return null;
		}
		UserGroup userGroup = getUserGroupById(userGroupId);
		if (userGroup == null) {
			return null;
		}
		userGroup = new UserGroup();
		userGroup.setUserGroupId(userGroupId);
		Date date = new Date();
		userGroup.setUserGroupUpdateTime(date);
		if (!StringUtil.stringIsNull(userGroupName)) {
			userGroup.setUserGroupName(userGroupName);
		}
		if (userGroupState == UserGroupConfig.STATE_USABLE || userGroupState == UserGroupConfig.STATE_DISABLED || userGroupState == UserGroupConfig.STATE_DELETE) {
			userGroup.setUserGroupState((byte) userGroupState);
		}
		boolean isUpdateUserGroupTopId = false;
		String userGroupTopId = null;
		if (isUpdateUserGroupParent) {
			if (StringUtil.stringIsNull(userGroupParentId)) {
				if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
					// 无变化
					return updateUserGroup(userGroup, false, null, null, null);
				} else {
					// 说明他下面的节点的topid就是他了
					String oldUserGroupTopId = userGroup.getUserGroupTopId();
					userGroup.setUserGroupParentId(null);
					userGroup.setUserGroupTopId(null);
					isUpdateUserGroupTopId = true;
					userGroupTopId = userGroup.getUserGroupId();

					/************************ 锁树开始 ************************/
					return (UserGroup) KeyLockManager.lockMethod(oldUserGroupTopId, UCenterKeyLockType.USER_GROUP, (params) -> updateUserGroup(params), new Object[] { userGroup, isUpdateUserGroupTopId, userGroupTopId, date, null });

					/************************ 锁树结束 ************************/
				}
			} else {
				UserGroup parentUserGroup = getUserGroupById(userGroupParentId);
				if (parentUserGroup == null) {
					return null;
				}
				String oldUserGroupTopId = userGroup.getUserGroupTopId();

				if (StringUtil.stringIsNull(parentUserGroup.getUserGroupTopId())) {
					if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
						// 都为空说明都是顶级，需要修改topid
						isUpdateUserGroupTopId = true;
						userGroupTopId = parentUserGroup.getUserGroupId();
					} else {
						if (userGroup.getUserGroupTopId().equals(parentUserGroup.getUserGroupId())) {
							// 说明当前topid是不变的
						} else {
							isUpdateUserGroupTopId = true;
							userGroupTopId = parentUserGroup.getUserGroupId();
						}
					}
					userGroup.setUserGroupTopId(parentUserGroup.getUserGroupId());
				} else {
					if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
						if (parentUserGroup.getUserGroupTopId().equals(userGroup.getUserGroupId())) {
							// 不可以这么做，自己的树归属到自己的枝上
							return null;
						} else {
							isUpdateUserGroupTopId = true;
							userGroupTopId = parentUserGroup.getUserGroupTopId();
						}
					} else {
						if (userGroup.getUserGroupTopId().equals(parentUserGroup.getUserGroupTopId())) {
							// 是一个topid不用变
						} else {
							isUpdateUserGroupTopId = true;
							userGroupTopId = parentUserGroup.getUserGroupTopId();
						}
					}
					userGroup.setUserGroupTopId(parentUserGroup.getUserGroupTopId());
				}
				userGroup.setUserGroupParentId(parentUserGroup.getUserGroupId());

				/************************ 锁树开始 ************************/
				return (UserGroup) KeyLockManager.lockMethod(userGroup.getUserGroupTopId(), oldUserGroupTopId, UCenterKeyLockType.USER_GROUP, (params) -> updateUserGroup(params), new Object[] { userGroup, isUpdateUserGroupTopId, userGroupTopId, date, userGroupParentId });
				/************************ 锁树结束 ************************/
			}
		} else {
			return updateUserGroup(userGroup, false, null, null, null);
		}
	}

	public static UserGroup updateUserGroup(Object... params) {
		UserGroup userGroup = (UserGroup) params[0];
		boolean isUpdateUserGroupTopId = (boolean) params[1];
		String userGroupTopId = (String) params[2];
		Date date = (Date) params[3];
		String userGroupParentId = (String) params[4];
		// 不得空判断这棵树是否改变了
		if (userGroupParentId != null) {
			UserGroup userGroupNew = UserGroupAction.getUserGroupById(userGroupParentId);
			if (userGroupNew == null) {
				return null;
			}
			String userGroupTopIdNew;
			if (StringUtil.stringIsNull(userGroupNew.getUserGroupTopId())) {
				userGroupTopIdNew = userGroupNew.getUserGroupId();
			} else {
				userGroupTopIdNew = userGroupNew.getUserGroupTopId();
			}
			// 判断锁的树还是这颗树吗？不是就说明树形结构已变化
			if (!userGroupTopIdNew.equals(userGroup.getUserGroupTopId())) {
				return null;
			}
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			int result = userGroupMapper.updateByPrimaryKeySelective(userGroup);
			if (result != 1) {
				MybatisManager.log.warn("修改用户组失败");
				return null;
			}
			if (isUpdateUserGroupTopId) {
				UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
				boolean bool = updateChildrenTopId(userGroup, userGroupMapper, userMapper, userGroupTopId, date);
				if (!bool) {
					MybatisManager.log.warn("修改用户组子集topid失败");
					return null;
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			MybatisManager.log.error("修改用户组异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return getUserGroupById(userGroup.getUserGroupId());
	}

	private static boolean updateChildrenTopId(UserGroup userGroup, UserGroupMapper userGroupMapper, UserMapper userMapper, String userGroupTopId, Date date) {
		UserCriteria userCriteria = new UserCriteria();
		UserCriteria.Criteria criteria = userCriteria.createCriteria();
		criteria.andUserGroupIdEqualTo(userGroup.getUserGroupId());
		List<User> userList = userMapper.selectByExample(userCriteria);
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				User newUser = new User();
				newUser.setUserId(user.getUserId());
				newUser.setUserGroupTopId(userGroupTopId);
				newUser.setUserUpdateTime(date);
				int result = userMapper.updateByPrimaryKeySelective(newUser);
				if (result != 1) {
					return false;
				}
			}
		}
		UserGroupCriteria userGroupCriteria = new UserGroupCriteria();
		UserGroupCriteria.Criteria criteriaGroup = userGroupCriteria.createCriteria();
		criteriaGroup.andUserGroupParentIdEqualTo(userGroup.getUserGroupId());
		List<UserGroup> userGroupList = userGroupMapper.selectByExample(userGroupCriteria);
		if (userGroupList != null) {
			for (int i = 0; i < userGroupList.size(); i++) {
				UserGroup chlidUserGroup = userGroupList.get(i);
				UserGroup newChlidUserGroup = new UserGroup();
				newChlidUserGroup.setUserGroupId(chlidUserGroup.getUserGroupId());
				newChlidUserGroup.setUserGroupTopId(userGroupTopId);
				newChlidUserGroup.setUserGroupUpdateTime(date);
				int result = userGroupMapper.updateByPrimaryKeySelective(newChlidUserGroup);
				if (result != 1) {
					return false;
				}
				boolean bool = updateChildrenTopId(chlidUserGroup, userGroupMapper, userMapper, userGroupTopId, date);
				if (!bool) {
					return false;
				}
			}
		}
		return true;
	}

	public static List<UserGroup> getUserGroupList(String userGroupParentId, boolean isUserGroupParentIsNull, boolean isRecursion, String userGroupTopId, int userGroupState, String userGroupCreateTimeGreaterThan, String userGroupCreateTimeLessThan, String userGroupUpdateTimeGreaterThan, String userGroupUpdateTimeLessThan) {
		SqlSession sqlSession = null;
		List<UserGroup> userGroupListAll = new ArrayList<>();
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			getUserGroupListRecursion(userGroupListAll, userGroupParentId, isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState, userGroupMapper, userGroupCreateTimeGreaterThan, userGroupCreateTimeLessThan, userGroupUpdateTimeGreaterThan, userGroupUpdateTimeLessThan);
			return userGroupListAll;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			MybatisManager.log.error("获取用户组子类异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static void getUserGroupListRecursion(List<UserGroup> userGroupListAll, String userGroupParentId, boolean isUserGroupParentIsNull, boolean isRecursion, String userGroupTopId, int userGroupState, UserGroupMapper userGroupMapper, String userGroupCreateTimeGreaterThan, String userGroupCreateTimeLessThan, String userGroupUpdateTimeGreaterThan, String userGroupUpdateTimeLessThan) {
		UserGroupCriteria userGroupCriteria = new UserGroupCriteria();
		UserGroupCriteria.Criteria criteriaGroup = userGroupCriteria.createCriteria();
		boolean isCanRecursion = false;
		if (!StringUtil.stringIsNull(userGroupParentId)) {
			criteriaGroup.andUserGroupParentIdEqualTo(userGroupParentId);
			isCanRecursion = true;
		} else {
			if (isUserGroupParentIsNull) {
				criteriaGroup.andUserGroupParentIdIsNull();
			} else {
				if (!StringUtil.stringIsNull(userGroupTopId)) {
					criteriaGroup.andUserGroupTopIdEqualTo(userGroupTopId);
				}
			}
			isCanRecursion = false;
		}
		if (userGroupState == UserConfig.STATE_DELETE || userGroupState == UserConfig.STATE_DISABLED || userGroupState == UserConfig.STATE_USABLE) {
			criteriaGroup.andUserGroupStateEqualTo((byte) userGroupState);
		}
		if (!StringUtil.stringIsNull(userGroupCreateTimeGreaterThan)) {
			Date userGroupCreateTimeGreaterThanDate = TimeUtils.stringToDate(userGroupCreateTimeGreaterThan);
			if (userGroupCreateTimeGreaterThanDate != null) {
				criteriaGroup.andUserGroupCreateTimeGreaterThanOrEqualTo(userGroupCreateTimeGreaterThanDate);
			}
		}
		if (!StringUtil.stringIsNull(userGroupCreateTimeLessThan)) {
			Date userGroupCreateTimeLessThanDate = TimeUtils.stringToDate(userGroupCreateTimeLessThan);
			if (userGroupCreateTimeLessThanDate != null) {
				criteriaGroup.andUserGroupCreateTimeLessThanOrEqualTo(userGroupCreateTimeLessThanDate);
			}
		}
		if (!StringUtil.stringIsNull(userGroupUpdateTimeGreaterThan)) {
			Date userGroupUpdateTimeGreaterThanDate = TimeUtils.stringToDate(userGroupUpdateTimeGreaterThan);
			if (userGroupUpdateTimeGreaterThanDate != null) {
				criteriaGroup.andUserGroupUpdateTimeGreaterThanOrEqualTo(userGroupUpdateTimeGreaterThanDate);
			}
		}
		if (!StringUtil.stringIsNull(userGroupUpdateTimeLessThan)) {
			Date userGroupUpdateTimeLessThanDate = TimeUtils.stringToDate(userGroupUpdateTimeLessThan);
			if (userGroupUpdateTimeLessThanDate != null) {
				criteriaGroup.andUserGroupUpdateTimeLessThanOrEqualTo(userGroupUpdateTimeLessThanDate);
			}
		}

		List<UserGroup> userGroupList = userGroupMapper.selectByExample(userGroupCriteria);
		if (userGroupList == null) {
			return;
		}
		userGroupListAll.addAll(userGroupList);
		// 不递归return
		if (!isRecursion || !isCanRecursion) {
			return;
		}
		for (int i = 0; i < userGroupList.size(); i++) {
			UserGroup userGroup = userGroupList.get(i);
			getUserGroupListRecursion(userGroupListAll, userGroup.getUserGroupId(), isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState, userGroupMapper, userGroupCreateTimeGreaterThan, userGroupCreateTimeLessThan, userGroupUpdateTimeGreaterThan, userGroupUpdateTimeLessThan);
		}
	}

	public static boolean deleteUserGroup(String userGroupId) {
		if (StringUtil.stringIsNull(userGroupId)) {
			return false;
		}
		UserGroup userGroup = getUserGroupById(userGroupId);
		if (userGroup == null) {
			return false;
		}
		// 如果是顶级不能删除
		if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
			return false;
		}

		return (boolean) KeyLockManager.lockMethod(userGroup.getUserGroupTopId(), UCenterKeyLockType.USER_GROUP, (params) -> deleteUserGroup(params), new Object[] { userGroup });
	}

	public static boolean deleteUserGroup(Object... params) {
		UserGroup userGroup = (UserGroup) params[0];
		List<User> userList = UserAction.getUserList(userGroup.getUserGroupId(), true, false, 0, 0, 0, null, null, null, null, null, null);
		if (userList == null) {
			return false;
		}
		if (userList.size() > 0) {
			List<String> userListId = new ArrayList<String>();
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				userListId.add(user.getUserId());
			}
			boolean result = UserAction.updateUserList(userListId, userGroup.getUserGroupTopId());
			if (!result) {
				return false;
			}
		}
		List<UserGroup> userGroupList = UserGroupAction.getUserGroupList(userGroup.getUserGroupId(), false, true, null, 0, null, null, null, null);
		if (userGroupList == null) {
			return false;
		}
		if (userGroupList.size() > 0) {
			for (int i = userGroupList.size() - 1; i >= 0; i--) {
				UserGroup updateUserGroup = userGroupList.get(i);
				boolean result = deleteUserGroupById(updateUserGroup.getUserGroupId());
				if (!result) {
					return false;
				}
			}
		}
		boolean result = deleteUserGroupById(userGroup.getUserGroupId());
		return result;
	}

	public static boolean deleteUserGroupById(String userGroupId) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			int result = userGroupMapper.deleteByPrimaryKey(userGroupId);
			if (result == 0) {
				MybatisManager.log.warn("删除用户组异常");
				return false;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			MybatisManager.log.error("删除用户组异常", e);
			return false;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return true;
	}

	public static UserGroupData.Builder getUserGroupDataBuilder(UserGroup userGroup) {
		UserGroupData.Builder dataBuilder = UserGroupData.newBuilder();
		dataBuilder.setUserGroupId(userGroup.getUserGroupId());
		dataBuilder.setUserGroupName(userGroup.getUserGroupName());
		if (userGroup.getUserGroupParentId() != null) {
			dataBuilder.setUserGroupParentId(userGroup.getUserGroupParentId());
		}
		dataBuilder.setUserGroupCreateTime(TimeUtils.dateToString(userGroup.getUserGroupCreateTime()));
		dataBuilder.setUserGroupUpdateTime(TimeUtils.dateToString(userGroup.getUserGroupUpdateTime()));
		dataBuilder.setUserGroupState(userGroup.getUserGroupState());
		if (userGroup.getUserGroupTopId() != null) {
			dataBuilder.setUserGroupTopId(userGroup.getUserGroupTopId());
		}
		return dataBuilder;
	}
}
