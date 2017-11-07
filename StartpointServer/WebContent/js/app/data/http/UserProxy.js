(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Proxy = window.juggle.Proxy;
    var HttpClient = window.juggle.HttpClient;
    var httpEventType = window.juggle.httpEventType;
    var url = window.startpoint.url;
    var cookieName = window.startpoint.cookieName;
    var cookieParam = window.startpoint.cookieParam;
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
            var httpClient = new HttpClient();
            if (userImg !== null && userImg.length !== 0) {
                httpClient.sendFile(userImg, data, url.url, header);
            } else {
                httpClient.send(data, url.url, header);
            }
            httpClient.addEventListener(httpEventType.SUCCESS, this.createUserSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.createUserFail, this);
        };
        this.createUserSuccess = function (event) {

        };
        this.createUserFail = function (event) {

        };
        this.getUser = function (userId) {
            var data = {
                "hOpCode": "11",
                "userId": userId
            };

            var header = [];
            header["hOpCode"] = "11";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.getUserSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.getUserFail, this);
        };
        this.getUserSuccess = function (event) {

        };
        this.getUserFail = function (event) {

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
            var httpClient = new HttpClient();
            if (userImg !== null && userImg.length !== 0) {
                httpClient.sendFile(userImg, data, url.url, header);
            } else {
                httpClient.send(data, url.url, header);
            }
            httpClient.addEventListener(httpEventType.SUCCESS, this.updateUserSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.updateUserFail, this);
        };
        this.updateUserSuccess = function (event) {

        };
        this.updateUserFail = function (event) {

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
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.getUserListSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.getUserListFail, this);
        };
        this.getUserListSuccess = function (event) {

        };
        this.getUserListFail = function (event) {

        };
        this.getUserImg = function (userId) {
            var data = {
                "hOpCode": 14,
                "userId": userId
            };

            var packet = encodeURI(JSON.stringify(data));
            return url.url + "?hOpCode=14&token=" + cookieParam.getCookieParam(cookieName.TOKEN) + "&packet=" + packet + "&t=" + new Date().getTime();
        };
        this.checkUserByUserName = function (userName) {
            var data = {
                "hOpCode": "16",
                "userName": userName
            };

            var header = [];
            header["hOpCode"] = "16";
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.checkUserByUserNameSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.checkUserByUserNameFail, this);
        };
        this.checkUserByUserNameSuccess = function (result, sendParam) {

        };
        this.checkUserByUserNameFail = function (result, sendParam) {

        }
    };
    window.startpoint.userProxy = new UserProxy();
})(window);