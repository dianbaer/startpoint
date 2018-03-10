(function (window) {
    if (!window.uca) window.uca = {};
    var Proxy = window.juggle.Proxy;
    var httpFilter = window.juggle.httpFilter;
    var url = window.uca.url;
    var notificationExt = window.uca.notificationExt;
    var cookieName = window.uca.cookieName;
    var cookieParam = window.uca.cookieParam;
    var TokenProxy = function () {
        Proxy.apply(this);
        this.getToken = function (userName, userPassword) {
            var data = {
                "hOpCode": "23",
                "userName": userName,
                "userPassword": userPassword
            };
            var header = [];
            header["hOpCode"] = "23";
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.getTokenSuccess, this.getTokenFail);
        };
        this.getTokenSuccess = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.LOGIN_SUCCESS, result));
        };
        this.getTokenFail = function (result) {
            this.notifyObservers(this.getNotification(notificationExt.LOGIN_FAIL, result));
        };
        this.deleteToken = function () {
            var data = {
                "hOpCode": "22"
            };
            var header = [];
            header["hOpCode"] = "22";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            httpFilter.send(data, url.ucUrl, header, null, null, this, this.deleteTokenSuccess, this.deleteTokenFail);
        };
        this.deleteTokenSuccess = function (result) {

        };
        this.deleteTokenFail = function (result) {

        }
    };
    window.uca.tokenProxy = new TokenProxy();
})(window);