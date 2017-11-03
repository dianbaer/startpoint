function TokenMediator() {

    this.init = function (view) {


        $("#getToken").on("click", this.onGetToken);
        $("#updateToken").on("click", this.onUpdateToken);
        $("#deleteToken").on("click", this.onDeleteToken);

    }
    // 注销方法
    this.dispose = function () {


        $("#getToken").remove("click", this.onGetToken);
        $("#updateToken").remove("click", this.onUpdateToken);
        $("#deleteToken").remove("click", this.onDeleteToken);

    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onGetToken = function (event) {
        var userName = $("#userName").val();
        var userPassword = $("#userPassword").val();

        $T.tokenProxy.getToken(userName, userPassword);
    }
    this.onUpdateToken = function (event) {

        $T.tokenProxy.updateToken();
    }
    this.onDeleteToken = function (event) {

        $T.tokenProxy.deleteToken();
    }
}
$T.tokenMediator = new TokenMediator();