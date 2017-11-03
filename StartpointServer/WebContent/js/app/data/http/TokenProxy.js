function TokenProxy() {
    this.NAME = "TokenProxy";
    this.getToken = function (userName, userPassword) {
        var data = {
            "hOpCode": 20,
            "userName": userName,
            "userPassword": userPassword
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getTokenSuccess;
        sendParam.failHandle = this.getTokenFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        $T.httpUtil.send(sendParam);
    }
    this.getTokenSuccess = function (result, sendParam) {
        $T.cookieParam.setCookieParam($T.cookieName.TOKEN, result.tokenId);
    }
    this.getTokenFail = function (result, sendParam) {

    }

    this.updateToken = function () {
        var data = {
            "hOpCode": 21
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.updateTokenSuccess;
        sendParam.failHandle = this.updateTokenFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.updateTokenSuccess = function (result, sendParam) {

    }
    this.updateTokenFail = function (result, sendParam) {

    }

    this.deleteToken = function () {
        var data = {
            "hOpCode": 22
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.deleteTokenSuccess;
        sendParam.failHandle = this.deleteTokenFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.url;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        $T.httpUtil.send(sendParam);
    }
    this.deleteTokenSuccess = function (result, sendParam) {

    }
    this.deleteTokenFail = function (result, sendParam) {

    }
}
$T.tokenProxy = new TokenProxy();