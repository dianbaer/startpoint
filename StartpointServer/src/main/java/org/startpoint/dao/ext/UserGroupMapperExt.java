package org.startpoint.dao.ext;

import java.util.List;

import org.startpoint.model.ext.UserGroupExt;

public interface UserGroupMapperExt {
	List<UserGroupExt> selectByUserGroupParentId(String userGroupParentId);

	List<UserGroupExt> selectTopUserGroup();
}