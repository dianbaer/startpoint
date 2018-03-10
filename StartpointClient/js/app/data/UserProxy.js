(function (window) {
    if (!window.uca) window.uca = {};
    var Proxy = window.juggle.Proxy;
    var httpFilter = window.juggle.httpFilter;
    var url = window.uca.url;
    var cookieName = window.uca.cookieName;
    var cookieParam = window.uca.cookieParam;
    var notificationExt = window.uca.notificationExt;
    var UserProxy = function () {
        Proxy.apply(this);
        this.createUser = function (userName, userPassword, userPhone, userEmail, userGroupId, userRealName, userSex, userAge, userRole, userImg) {
            var data = {
                "hOpCode": "10",
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
            var header = [];
            header["hOpCode"] = "10";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            if (userImg !== null && userImg !== undefined && userImg.length !== 0) {
                httpFilter.sendFile(userImg, data, url.ucUrl, header, null, null, this, this.createUserSuccess, this.createUserFail);
            } else {
                httpFilter.send(data, url.ucUrl, header, null, null, this, this.createUserSuccess, this.createUserFail);
            }
        };
        this.createUserSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.CREATE_USER_SUCCESS));
        };
        this.createUserFail = function (result) {

        };
        this.getUser = function (userId) {
            var data = {
                "hOpCode": "11",
                "userId": userId
            };
            var header = [];
            header["hOpCode"] = "11";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserSuccess, this.getUserSuccess);
        };
        this.getUserSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.GET_USER_SUCCESS, result));
        };
        this.getUserFail = function (result) {

        };
        this.updateUser = function (userId, userPassword, userPhone, userEmail, userState, isUpdateUserGroup, userGroupId, userRealName, userSex, userAge, userRole, userImg) {
            var data = {
                "hOpCode": "12",
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
            var packet = encodeURI(JSON.stringify(data));
            var header = [];
            header["hOpCode"] = "12";
            header["packet"] = packet;
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            if (userImg !== null && userImg !== undefined && userImg.length !== 0) {
                httpFilter.sendFile(userImg, data, url.ucUrl, header, null, null, this, this.updateUserSuccess, this.updateUserFail);
            } else {
                httpFilter.send(data, url.ucUrl, header, null, null, this, this.updateUserSuccess, this.updateUserFail);
            }
        };
        this.updateUserSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.UPDATE_USER_SUCCESS));
        };
        this.updateUserFail = function (result) {

        };
        this.getUserList = function (userGroupId, isUserGroupIsNull, isRecursion, userState, userSex, userRole, userGroupTopId, userName, userCreateTimeGreaterThan, userCreateTimeLessThan, currentPage, pageSize) {
            var data = {
                "hOpCode": "13",
                "userGroupId": userGroupId,
                "isUserGroupIsNull": isUserGroupIsNull == "1" ? true : false,
                "isRecursion": isRecursion == "1" ? true : false,
                "userState": userState,
                "userSex": userSex,
                "userRole": userRole,
                "userGroupTopId": userGroupTopId,
                "userName": userName,
                "userCreateTimeGreaterThan": userCreateTimeGreaterThan,
                "userCreateTimeLessThan": userCreateTimeLessThan,
                "currentPage": currentPage,
                "pageSize": pageSize
            };

            var header = [];
            header["hOpCode"] = "13";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getUserListSuccess, this.getUserListFail);
        };
        this.getUserListSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.GET_USER_LIST_SUCCESS, result));
        };
        this.getUserListFail = function (result) {

        };
        this.getUserImg = function (userId) {
            var data = {
                "hOpCode": "14",
                "userId": userId
            };

            var packet = encodeURI(JSON.stringify(data));
            return url.url + "?hOpCode=14&token=" + cookieParam.getCookieParam(cookieName.TOKEN) + "&packet=" + packet + "&t=" + new Date().getTime();
        }
    };
    window.uca.userProxy = new UserProxy();
})(window);