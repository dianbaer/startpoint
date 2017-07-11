function UserGroupProxy() {
    this.NAME = "UserGroupProxy";
    this.createUserGroup = function (userGroupName, userGroupParentId) {
        var data = {
            "hOpCode": 1,
            "userGroupName": userGroupName,
            "userGroupParentId": userGroupParentId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.createUserGroupSuccess;
        sendParam.failHandle = this.createUserGroupFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.createUserGroupSuccess = function (result, sendParam) {

    }
    this.createUserGroupFail = function (result, sendParam) {

    }
    this.updateUserGroup = function (userGroupId, userGroupName, isUpdateUserGroupParent, userGroupParentId, userGroupState) {
        var data = {
            "hOpCode": 2,
            "userGroupId": userGroupId,
            "userGroupName": userGroupName,
            "isUpdateUserGroupParent": isUpdateUserGroupParent == "1" ? true : false,
            "userGroupParentId": userGroupParentId,
            "userGroupState": userGroupState
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.updateUserGroupSuccess;
        sendParam.failHandle = this.updateUserGroupFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.updateUserGroupSuccess = function (result, sendParam) {

    }
    this.updateUserGroupFail = function (result, sendParam) {

    }
    this.getUserGroup = function (userGroupId) {
        var data = {
            "hOpCode": 3,
            "userGroupId": userGroupId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getUserGroupSuccess;
        sendParam.failHandle = this.getUserGroupFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.getUserGroupSuccess = function (result, sendParam) {

    }
    this.getUserGroupFail = function (result, sendParam) {

    }
    this.getUserGroupList = function (userGroupParentId, isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState,currentPage,pageSize) {
        var data = {
            "hOpCode": 4,
            "userGroupParentId": userGroupParentId,
            "isUserGroupParentIsNull": isUserGroupParentIsNull == "1" ? true : false,
            "isRecursion": isRecursion == "1" ? true : false,
            "userGroupTopId": userGroupTopId,
            "userGroupState": userGroupState,
            "currentPage":currentPage,
            "pageSize":pageSize
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getUserGroupListSuccess;
        sendParam.failHandle = this.getUserGroupListFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.getUserGroupListSuccess = function (result, sendParam) {

    }
    this.getUserGroupListFail = function (result, sendParam) {

    }
    this.deleteUserGroup = function (userGroupId) {
        var data = {
            "hOpCode": 5,
            "userGroupId": userGroupId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.deleteUserGroupSuccess;
        sendParam.failHandle = this.deleteUserGroupFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.deleteUserGroupSuccess = function (result, sendParam) {

    }
    this.deleteUserGroupFail = function (result, sendParam) {

    }
}
$T.userGroupProxy = new UserGroupProxy();