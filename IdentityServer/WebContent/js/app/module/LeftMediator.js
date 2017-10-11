function LeftMediator() {

    this.init = function (view) {

        $("#left_button").on("click", this.onClick);
        $("#left_button1").on("click", this.onClick1);
        $("#left_button2").on("click", this.onClick2);
        $("#left_button3").on("click", this.onClick3);
        $("#left_button4").on("click", this.onClick4);
    }
    // 注销方法
    this.dispose = function () {
        $("#left_button").remove("click", this.onClick);
        $("#left_button1").remove("click", this.onClick1);
        $("#left_button2").remove("click", this.onClick2);
        $("#left_button3").remove("click", this.onClick3);
        $("#left_button4").remove("click", this.onClick4);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onClick = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "createUserGroup"));
    }
    this.onClick1 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "updateUserGroup"));
    }
    this.onClick2 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "User"));
    }
    this.onClick3 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "UpdateUser"));
    }
    this.onClick4 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "Token"));
    }
}
$T.leftMediator = new LeftMediator();