(function (window) {
    if (!window.uca) window.uca = {};
    var Proxy = window.juggle.Proxy;
    var httpFilter = window.juggle.httpFilter;
    var url = window.uca.url;
    var cookieName = window.uca.cookieName;
    var cookieParam = window.uca.cookieParam;
    var notificationExt = window.uca.notificationExt;
    var UserGroupProxy = function () {
        Proxy.apply(this);
        this.createUserGroup = function (userGroupName, userGroupParentId) {
            var data = {
                "hOpCode": "1",
                "userGroupName": userGroupName,
                "userGroupParentId": userGroupParentId
            };

            var header = [];
            header["hOpCode"] = "1";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.createUserGroupSuccess, this.createUserGroupFail);
        };
        this.createUserGroupSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.CREATE_USER_GROUP_SUCCESS, result));
        };
        this.createUserGroupFail = function (result) {

        };
        this.updateUserGroup = function (userGroupId, userGroupName, isUpdateUserGroupParent, userGroupParentId, userGroupState) {
            var data = {
                "hOpCode": "2",
                "userGroupId": userGroupId,
                "userGroupName": userGroupName,
                "isUpdateUserGroupParent": isUpdateUserGroupParent == "1" ? true : false,
                "userGroupParentId": userGroupParentId,
                "userGroupState": userGroupState
            };

            var header = [];
            header["hOpCode"] = "2";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.updateUserGroupSuccess, this.updateUserGroupFail);
        };
        this.updateUserGroupSuccess = function (result) {

        };
        this.updateUserGroupFail = function (result) {

        };
        this.getUserGroup = function (userGroupId) {
            var data = {
                "hOpCode": "3",
                "userGroupId": userGroupId
            };
            var header = [];
            header["hOpCode"] = "3";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserGroupSuccess, this.getUserGroupFail);
        };
        this.getUserGroupSuccess = function (result) {

        };
        this.getUserGroupFail = function (result) {

        };
        this.getUserGroupList = function (userGroupParentId, isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState, currentPage, pageSize) {
            var data = {
                "hOpCode": "4",
                "userGroupParentId": userGroupParentId,
                "isUserGroupParentIsNull": isUserGroupParentIsNull == "1" ? true : false,
                "isRecursion": isRecursion == "1" ? true : false,
                "userGroupTopId": userGroupTopId,
                "userGroupState": userGroupState,
                "currentPage": currentPage,
                "pageSize": pageSize
            };

            var header = [];
            header["hOpCode"] = "4";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserGroupListSuccess, this.getUserGroupListFail);
        };
        this.getUserGroupListSuccess = function (result) {

        };
        this.getUserGroupListFail = function (result) {

        };
        this.deleteUserGroup = function (userGroupId) {
            var data = {
                "hOpCode": "5",
                "userGroupId": userGroupId
            };

            var header = [];
            header["hOpCode"] = "5";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.deleteUserGroupSuccess, this.deleteUserGroupFail);
        };
        this.deleteUserGroupSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.DELETE_USER_GROUP_SUCCESS, result));
        };
        this.deleteUserGroupFail = function (result) {

        };
        this.getUserGroupTree = function () {
            var data = {
                "hOpCode": "6"
            };

            var header = [];
            header["hOpCode"] = "6";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserGroupTreeSuccess, this.getUserGroupTreeFail);
        };
        this.getUserGroupTreeSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.GET_USER_GROUP_TREE_SUCCESS, result));
        };
        this.getUserGroupTreeFail = function (result) {

        };
        this.getUserGroupTreeNode = function (userGroupParentId, treeId) {
            var data = {
                "hOpCode": "6",
                "userGroupParentId": userGroupParentId
            };

            var header = [];
            header["hOpCode"] = "6";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserGroupTreeNodeSuccess, this.getUserGroupTreeNodeFail, treeId);
        };
        this.getUserGroupTreeNodeSuccess = function (result, callData) {
            this.notifyObservers(this.getNotification(notificationExt.GET_USER_GROUP_TREE_NODE_SUCCESS, {
                "result": result,
                "treeId": callData
            }));
        };
        this.getUserGroupTreeNodeFail = function (result) {

        };
        this.getUserGroupRecursion = function (userGroupId) {
            var data = {
                "hOpCode": "7",
                "userGroupId": userGroupId
            };

            var header = [];
            header["hOpCode"] = "7";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserGroupRecursionSuccess, this.getUserGroupRecursionFail);
        };
        this.getUserGroupRecursionSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.GET_USER_GROUP_RECURSION_SUCCESS, result));
        };
        this.getUserGroupRecursionFail = function (result) {

        };
    };
    window.uca.userGroupProxy = new UserGroupProxy();
})(window);