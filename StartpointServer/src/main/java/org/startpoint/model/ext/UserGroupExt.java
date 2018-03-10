package org.startpoint.model.ext;

import org.startpoint.model.base.UserGroup;

public class UserGroupExt extends UserGroup {
	private static final long serialVersionUID = 1L;
	private Integer childrenNum;

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

}
