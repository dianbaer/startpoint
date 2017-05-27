package action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import config.CommonConfigUCenter;
import config.UserConfig;
import dao.dao.base.UserGroupMapper;
import dao.dao.base.UserMapper;
import dao.model.base.User;
import dao.model.base.UserCriteria;
import dao.model.base.UserGroup;
import dao.model.base.UserGroupCriteria;
import http.HttpConfig;
import keylock.KeyLockManager;
import keylock.UCenterKeyLockType;
import log.LogManager;
import mbatis.MybatisManager;
import protobuf.http.UserGroupProto.UserData;
import tool.StringUtil;
import tool.TimeUtils;
import util.IdUtil;

public class UserAction {
	public static String USER_IMG_DIR_PATH;

	public static User createUser(String userName, String userPassword, String userPhone, String userEmail, String userGroupId, String userRealName, int userSex, int userAge, int userRole) {
		if (StringUtil.stringIsNull(userName) || StringUtil.stringIsNull(userPassword)) {
			return null;
		}
		Date date = new Date();
		User user = new User();
		user.setUserId(IdUtil.getUuid());
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserCreateTime(date);
		user.setUserUpdateTime(date);
		user.setUserState((byte) UserConfig.STATE_USABLE);
		if (!StringUtil.stringIsNull(userPhone)) {
			user.setUserPhone(userPhone);
		}
		if (!StringUtil.stringIsNull(userEmail)) {
			user.setUserEmail(userEmail);
		}
		if (!StringUtil.stringIsNull(userRealName)) {
			user.setUserRealName(userRealName);
		}
		if (userSex == UserConfig.SEX_WOMAN || userSex == UserConfig.SEX_MAN) {
			user.setUserSex((byte) userSex);
		}
		if (userAge > 0) {
			user.setUserAge((byte) userAge);
		}
		if (userRole == UserConfig.ROLE_GROUP_MANAGER) {
			user.setUserRole((byte) userRole);
		} else {
			user.setUserRole((byte) UserConfig.ROLE_MEMBER);
		}
		if (!StringUtil.stringIsNull(userGroupId)) {
			UserGroup userGroup = UserGroupAction.getUserGroupById(userGroupId);
			if (userGroup == null) {
				return null;
			}
			user.setUserGroupId(userGroup.getUserGroupId());
			if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
				user.setUserGroupTopId(userGroup.getUserGroupId());
			} else {
				user.setUserGroupTopId(userGroup.getUserGroupTopId());
			}
			/************************ 锁树开始 ************************/
			return (User) KeyLockManager.lockMethod(user.getUserGroupTopId(), UCenterKeyLockType.USER_GROUP, (params) -> createUser(params), new Object[] { user, userGroupId });

			/************************ 锁树结束 ************************/
		} else {
			return createUser(user, null);
		}

	}

	public static User createUser(Object... params) {
		User user = (User) params[0];
		String userGroupId = (String) params[1];
		// 不得空判断这棵树是否改变了
		if (userGroupId != null) {
			UserGroup userGroupNew = UserGroupAction.getUserGroupById(userGroupId);
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
			if (!userGroupTopId.equals(user.getUserGroupTopId())) {
				return null;
			}
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			int result = userMapper.insert(user);
			if (result == 0) {
				LogManager.mariadbLog.warn("创建用户失败");
				return null;
			}
			sqlSession.commit();
			return user;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("创建用户异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static User getUserById(String userId) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		SqlSession sqlSession = null;
		User user;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.selectByPrimaryKey(userId);
			if (user == null) {
				LogManager.mariadbLog.warn("通过userId:" + userId + "获取用户为空");
			}
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取用户异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return user;
	}

	public static User getUserByName(String userName) {
		if (StringUtil.stringIsNull(userName)) {
			return null;
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			UserCriteria userCriteria = new UserCriteria();
			UserCriteria.Criteria criteria = userCriteria.createCriteria();
			criteria.andUserNameEqualTo(userName);
			List<User> userList = userMapper.selectByExample(userCriteria);
			if (userList == null || userList.size() == 0) {
				LogManager.mariadbLog.warn("通过userName:" + userName + "获取用户为空");
				return null;
			}
			return userList.get(0);
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取用户异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	// dingwancheng start
	public static User getUserByEmail(String userEmail) {
		if (StringUtil.stringIsNull(userEmail)) {
			return null;
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			UserCriteria userCriteria = new UserCriteria();
			UserCriteria.Criteria criteria = userCriteria.createCriteria();
			criteria.andUserEmailEqualTo(userEmail);
			List<User> userList = userMapper.selectByExample(userCriteria);
			if (userList == null || userList.size() == 0) {
				LogManager.mariadbLog.warn("通过userEmail:" + userEmail + "获取用户为空");
				return null;
			}
			return userList.get(0);
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取用户异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	// dingwancheng end

	public static User updateUser(String userId, String userPassword, String userPhone, String userEmail, int userState, boolean isUpdateUserGroup, String userGroupId, String userRealName, int userSex, int userAge, int userRole, String userImg) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		User user = getUserById(userId);
		if (user == null) {
			return null;
		}
		// 只修改用户想修改的
		user = new User();
		user.setUserId(userId);
		Date date = new Date();
		user.setUserUpdateTime(date);
		if (!StringUtil.stringIsNull(userPassword)) {
			user.setUserPassword(userPassword);
		}
		if (!StringUtil.stringIsNull(userPhone)) {
			user.setUserPhone(userPhone);
		}
		if (!StringUtil.stringIsNull(userEmail)) {
			user.setUserEmail(userEmail);
		}
		if (userState == UserConfig.STATE_DELETE || userState == UserConfig.STATE_DISABLED || userState == UserConfig.STATE_USABLE) {
			user.setUserState((byte) userState);
		}
		if (!StringUtil.stringIsNull(userRealName)) {
			user.setUserRealName(userRealName);
		}
		if (userSex == UserConfig.SEX_MAN || userSex == UserConfig.SEX_WOMAN) {
			user.setUserSex((byte) userSex);
		}
		if (userAge > 0) {
			user.setUserAge((byte) userAge);
		}
		if (userRole == UserConfig.ROLE_MEMBER || userRole == UserConfig.ROLE_GROUP_MANAGER) {
			user.setUserRole((byte) userRole);
		}
		if (!StringUtil.stringIsNull(userImg)) {
			user.setUserImg(userImg);
		}
		if (isUpdateUserGroup) {
			if (StringUtil.stringIsNull(userGroupId)) {
				String oldUserGroupTopId = user.getUserGroupTopId();
				user.setUserGroupId(null);
				user.setUserGroupTopId(null);
				if (oldUserGroupTopId == null) {
					return updateUser(user, null);
				} else {
					/************************ 锁树开始 ************************/
					return (User) KeyLockManager.lockMethod(oldUserGroupTopId, UCenterKeyLockType.USER_GROUP, (params) -> updateUser(params), new Object[] { user, null });

					/************************ 锁树结束 ************************/
				}

			} else {
				UserGroup userGroup = UserGroupAction.getUserGroupById(userGroupId);
				if (userGroup == null) {
					return null;
				}
				user.setUserGroupId(userGroup.getUserGroupId());
				String oldUserGroupTopId = user.getUserGroupTopId();
				if (StringUtil.stringIsNull(userGroup.getUserGroupTopId())) {
					user.setUserGroupTopId(userGroup.getUserGroupId());
				} else {
					user.setUserGroupTopId(userGroup.getUserGroupTopId());
				}

				/************************ 锁树开始 ************************/
				return (User) KeyLockManager.lockMethod(user.getUserGroupTopId(), oldUserGroupTopId, UCenterKeyLockType.USER_GROUP, (params) -> updateUser(params), new Object[] { user, userGroupId });

				/************************ 锁树结束 ************************/
			}
		} else {
			return updateUser(user, null);
		}

	}

	public static User updateUser(Object... params) {
		User user = (User) params[0];
		String userGroupId = (String) params[1];
		// 不得空判断这棵树是否改变了
		if (userGroupId != null) {
			UserGroup userGroupNew = UserGroupAction.getUserGroupById(userGroupId);
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
			if (!userGroupTopId.equals(user.getUserGroupTopId())) {
				return null;
			}
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			int result = userMapper.updateByPrimaryKeySelective(user);
			if (result != 1) {
				LogManager.mariadbLog.warn("修改用户失败");
				return null;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("修改用户异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return getUserById(user.getUserId());
	}

	public static List<User> getUserList(String userGroupId, boolean isRecursion, boolean isUserGroupIsNull, int userState, int userSex, int userRole, String userGroupTopId) {
		SqlSession sqlSession = null;
		List<User> userListAll = new ArrayList<>();
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
			getUserListRecursion(userListAll, userGroupId, userState, userSex, userRole, userGroupMapper, userMapper, isRecursion, userGroupTopId, isUserGroupIsNull);
			return userListAll;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取用户列表异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static void getUserListRecursion(List<User> userListAll, String userGroupId, int userState, int userSex, int userRole, UserGroupMapper userGroupMapper, UserMapper userMapper, boolean isRecursion, String userGroupTopId, boolean isUserGroupIsNull) {
		UserCriteria userCriteria = new UserCriteria();
		UserCriteria.Criteria criteria = userCriteria.createCriteria();
		if (userState == UserConfig.STATE_DELETE || userState == UserConfig.STATE_DISABLED || userState == UserConfig.STATE_USABLE) {
			criteria.andUserStateEqualTo((byte) userState);
		}
		if (userSex == UserConfig.SEX_MAN || userSex == UserConfig.SEX_WOMAN) {
			criteria.andUserSexEqualTo((byte) userSex);
		}
		if (userRole == UserConfig.ROLE_MEMBER || userRole == UserConfig.ROLE_GROUP_MANAGER) {
			criteria.andUserRoleEqualTo((byte) userRole);
		}
		boolean isCanRecursion = false;
		if (!StringUtil.stringIsNull(userGroupId)) {
			criteria.andUserGroupIdEqualTo(userGroupId);
			isCanRecursion = true;
		} else {
			if (isUserGroupIsNull) {
				criteria.andUserGroupIdIsNull();
			} else {
				// userGroupId为空才有意义查所有
				if (!StringUtil.stringIsNull(userGroupTopId)) {
					criteria.andUserGroupTopIdEqualTo(userGroupTopId);
				}
			}
			isCanRecursion = false;
		}

		List<User> userList = userMapper.selectByExample(userCriteria);
		if (userList != null) {
			userListAll.addAll(userList);
		}
		// 不递归return
		if (!isRecursion || !isCanRecursion) {
			return;
		}

		UserGroupCriteria userGroupCriteria = new UserGroupCriteria();
		UserGroupCriteria.Criteria criteriaGroup = userGroupCriteria.createCriteria();
		criteriaGroup.andUserGroupParentIdEqualTo(userGroupId);
		List<UserGroup> userGroupList = userGroupMapper.selectByExample(userGroupCriteria);
		if (userGroupList != null) {
			for (int i = 0; i < userGroupList.size(); i++) {
				UserGroup userGroup = userGroupList.get(i);
				getUserListRecursion(userListAll, userGroup.getUserGroupId(), userState, userSex, userRole, userGroupMapper, userMapper, isRecursion, userGroupTopId, isUserGroupIsNull);
			}
		}
		return;
	}

	public static UserData.Builder getUserDataBuilder(User user) {
		UserData.Builder dataBuilder = UserData.newBuilder();
		dataBuilder.setUserId(user.getUserId());
		dataBuilder.setUserName(user.getUserName());
		if (user.getUserPhone() != null) {
			dataBuilder.setUserPhone(user.getUserPhone());
		}
		if (user.getUserEmail() != null) {
			dataBuilder.setUserEmail(user.getUserEmail());
		}
		dataBuilder.setUserCreateTime(TimeUtils.dateToString(user.getUserCreateTime()));
		dataBuilder.setUserUpdateTime(TimeUtils.dateToString(user.getUserUpdateTime()));
		dataBuilder.setUserState(user.getUserState());
		if (user.getUserGroupId() != null) {
			dataBuilder.setUserGroupId(user.getUserGroupId());
		}
		if (user.getUserRealName() != null) {
			dataBuilder.setUserRealName(user.getUserRealName());
		}
		if (user.getUserSex() != null) {
			dataBuilder.setUserSex(user.getUserSex());
		}
		if (user.getUserAge() != null) {
			dataBuilder.setUserAge(user.getUserAge());
		}
		if (user.getUserGroupTopId() != null) {
			dataBuilder.setUserGroupTopId(user.getUserGroupTopId());
		}
		dataBuilder.setUserRole(user.getUserRole());
		if (user.getUserImg() != null) {
			dataBuilder.setUserImg(user.getUserImg());
		}
		return dataBuilder;
	}

	public static void createUserImgDir() {
		USER_IMG_DIR_PATH = HttpConfig.PROJECT_PATH + "/" + CommonConfigUCenter.USER_IMG_DIR;
		File file = new File(USER_IMG_DIR_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String saveUserImg(File file) {
		try {
			String separator = File.separator;
			String filePath = file.getPath();
			int suffixPos = filePath.lastIndexOf(".");
			String nameEND = filePath.substring(suffixPos);
			String name = IdUtil.getUuid() + nameEND;
			String newPath = USER_IMG_DIR_PATH + separator + name;
			File newfile = new File(newPath);
			file.renameTo(newfile);
			return name;
		} catch (Exception e) {
			LogManager.initLog.error("保存头像异常", e);
			return null;
		}
	}

	public static boolean deleteUserImg(String name) {
		String separator = File.separator;
		String newPath = USER_IMG_DIR_PATH + separator + name;
		File newfile = new File(newPath);
		boolean result = newfile.delete();
		return result;
	}

	public static File getUserImg(String name) {
		String separator = File.separator;
		String newPath = USER_IMG_DIR_PATH + separator + name;
		File newfile = new File(newPath);
		if (newfile.isFile()) {
			return newfile;
		}
		return null;
	}
}
