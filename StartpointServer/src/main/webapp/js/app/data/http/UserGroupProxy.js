(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Proxy = window.juggle.Proxy;
    var HttpClient = window.juggle.HttpClient;
    var httpEventType = window.juggle.httpEventType;
    var url = window.startpoint.url;
    var cookieName = window.startpoint.cookieName;
    var cookieParam = window.startpoint.cookieParam;
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
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.createUserGroupSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.createUserGroupFail, this);
        };
        this.createUserGroupSuccess = function (event) {

        };
        this.createUserGroupFail = function (event) {

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
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.updateUserGroupSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.updateUserGroupFail, this);
        };
        this.updateUserGroupSuccess = function (event) {

        };
        this.updateUserGroupFail = function (event) {

        };
        this.getUserGroup = function (userGroupId) {
            var data = {
                "hOpCode": "3",
                "userGroupId": userGroupId
            };
            var header = [];
            header["hOpCode"] = "3";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.getUserGroupSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.getUserGroupFail, this);
        };
        this.getUserGroupSuccess = function (event) {

        };
        this.getUserGroupFail = function (event) {

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
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.getUserGroupListSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.getUserGroupListFail, this);
        };
        this.getUserGroupListSuccess = function (event) {

        };
        this.getUserGroupListFail = function (event) {

        };
        this.deleteUserGroup = function (userGroupId) {
            var data = {
                "hOpCode": "5",
                "userGroupId": userGroupId
            };

            var header = [];
            header["hOpCode"] = "5";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.deleteUserGroupSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.deleteUserGroupFail, this);
        };
        this.deleteUserGroupSuccess = function (event) {

        };
        this.deleteUserGroupFail = function (event) {

        }
    };
    window.startpoint.userGroupProxy = new UserGroupProxy();
})(window);