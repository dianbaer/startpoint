(function (window) {
    if (!window.uca) window.uca = {};
    var NotificationExt = function () {
        this.LOGIN_SUCCESS = "loginSuccess";
        this.LOGIN_FAIL = "loginFail";
        this.CHANGE_RIGHT_CONTENT = "changeRightContent";
        this.OPEN_BOX = "openBox";
        this.GET_USER_SUCCESS = "getUserSuccess";
        this.GET_USER_GROUP_TREE_SUCCESS = "getUserGroupTreeSuccess";
        this.GET_USER_GROUP_TREE_NODE_SUCCESS = "getUserGroupTreeNodeSuccess";
        this.GET_USER_GROUP_RECURSION_SUCCESS = "getUserGroupRecursionSuccess";
        this.SHOW_RIGHT_USER_GROUP_INFO = "showRightUserGroupInfo";
        this.GET_USER_LIST_SUCCESS = "getUserListSuccess";
        this.CREATE_USER_GROUP_SUCCESS = "createUserGroupSuccess";
        this.DELETE_USER_GROUP_SUCCESS = "deleteUserGroupSuccess";
        this.CREATE_USER_SUCCESS = "createUserSuccess";
        this.UPDATE_USER_SUCCESS = "updateUserSuccess";
    };
    window.uca.notificationExt = new NotificationExt();
})(window);