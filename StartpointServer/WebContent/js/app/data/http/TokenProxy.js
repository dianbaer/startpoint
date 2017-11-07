(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Proxy = window.juggle.Proxy;
    var HttpClient = window.juggle.HttpClient;
    var httpEventType = window.juggle.httpEventType;
    var url = window.startpoint.url;
    var cookieName = window.startpoint.cookieName;
    var cookieParam = window.startpoint.cookieParam;
    var TokenProxy = function () {
        Proxy.apply(this);
        this.getToken = function (userName, userPassword) {
            var data = {
                "hOpCode": "20",
                "userName": userName,
                "userPassword": userPassword
            };
            var header = [];
            header["hOpCode"] = "20";
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.getTokenSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.getTokenFail, this);
        };
        this.getTokenSuccess = function (event) {
            var returnData = JSON.parse(event.mData);
            cookieParam.setCookieParam(cookieName.TOKEN, returnData.tokenId);
        };
        this.getTokenFail = function (event) {

        };

        this.updateToken = function () {
            var data = {
                "hOpCode": "21"
            };

            var header = [];
            header["hOpCode"] = "21";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.updateTokenSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.updateTokenFail, this);
        };
        this.updateTokenSuccess = function (event) {

        };
        this.updateTokenFail = function (event) {

        };

        this.deleteToken = function () {
            var data = {
                "hOpCode": "22"
            };

            var header = [];
            header["hOpCode"] = "22";
            header[cookieName.TOKEN] = cookieParam.getCookieParam(cookieName.TOKEN);
            var httpClient = new HttpClient();
            httpClient.send(data, url.url, header);
            httpClient.addEventListener(httpEventType.SUCCESS, this.deleteTokenSuccess, this);
            httpClient.addEventListener(httpEventType.ERROR, this.deleteTokenFail, this);
        };
        this.deleteTokenSuccess = function (event) {

        };
        this.deleteTokenFail = function (event) {

        }
    };
    window.startpoint.tokenProxy = new TokenProxy();
})(window);