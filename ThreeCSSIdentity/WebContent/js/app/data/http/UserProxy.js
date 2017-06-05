function UserProxy() {
    this.NAME = "UserProxy";
    this.createUser = function (userName, userPassword, userPhone, userEmail, userGroupId, userRealName, userSex, userAge, userRole, userImg) {
        var data = {
            "hOpCode": 10,
            "userName": userName,
            "userPassword": userPassword,
            "userPhone": userPhone,
            "userEmail": userEmail,
            "userGroupId": userGroupId,
            "userRealName": userRealName,
            "userSex": userSex,
            "userAge": userAge,
            "userRole": userRole
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.createUserSuccess;
        sendParam.failHandle = this.createUserFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        if (userImg != null & userImg.length != 0) {
            sendParam.fileArray = userImg;
        }
        $T.httpUtil.send(sendParam);
    }
    this.createUserSuccess = function (result, sendParam) {

    }
    this.createUserFail = function (result, sendParam) {

    }
    this.getUser = function (userId) {
        var data = {
            "hOpCode": 11,
            "userId": userId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getUserSuccess;
        sendParam.failHandle = this.getUserFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.getUserSuccess = function (result, sendParam) {

    }
    this.getUserFail = function (result, sendParam) {

    }
    this.updateUser = function (userId, userPassword, userPhone, userEmail, userState, isUpdateUserGroup, userGroupId, userRealName, userSex, userAge, userRole, userImg) {
        var data = {
            "hOpCode": 12,
            "userId": userId,
            "userPassword": userPassword,
            "userPhone": userPhone,
            "userEmail": userEmail,
            "userState": userState,
            "isUpdateUserGroup": isUpdateUserGroup == "1" ? true : false,
            "userGroupId": userGroupId,
            "userRealName": userRealName,
            "userSex": userSex,
            "userAge": userAge,
            "userRole": userRole
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.updateUserSuccess;
        sendParam.failHandle = this.updateUserFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        if (userImg != null & userImg.length != 0) {
            sendParam.fileArray = userImg;
        }
        $T.httpUtil.send(sendParam);
    }
    this.updateUserSuccess = function (result, sendParam) {

    }
    this.updateUserFail = function (result, sendParam) {

    }
    this.getUserList = function (userGroupId, isUserGroupIsNull, isRecursion, userState, userSex, userRole, userGroupTopId,userName,userCreateTimeGreaterThan,userCreateTimeLessThan,currentPage,pageSize) {
        var data = {
            "hOpCode": 13,
            "userGroupId": userGroupId,
            "isUserGroupIsNull": isUserGroupIsNull == "1" ? true : false,
            "isRecursion": isRecursion == "1" ? true : false,
            "userState": userState,
            "userSex": userSex,
            "userRole": userRole,
            "userGroupTopId": userGroupTopId,
            "userName":userName,
            "userCreateTimeGreaterThan":userCreateTimeGreaterThan,
            "userCreateTimeLessThan":userCreateTimeLessThan,
            "currentPage":currentPage,
            "pageSize":pageSize
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getUserListSuccess;
        sendParam.failHandle = this.getUserListFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.getUserListSuccess = function (result, sendParam) {

    }
    this.getUserListFail = function (result, sendParam) {

    }
    this.getUserImg = function (userId) {
        var data = {
            "hOpCode": 14,
            "userId": userId
        };
        var sendParam = new SendParam();
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        sendParam.sendType = $T.httpConfig.SEND_TYPE_PACKET;
        sendParam.receiveType = $T.httpConfig.RECEIVE_TYPE_IMAGE;
        return $T.httpUtil.getRequestUrl(sendParam);
    }
}
$T.userProxy = new UserProxy();