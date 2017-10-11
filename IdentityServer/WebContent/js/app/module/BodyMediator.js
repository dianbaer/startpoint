function BodyMediator() {

    this.init = function (view) {

        $T.moduleManager.loadModule("html/createUserGroup.html", document.getElementById("body"), "bodyview", $T.createUserGroupMediator);
    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notificationExt.CHANGE_BODY];
    // 关心的消息处理
    this.handleNotification = function (data) {
        switch (data[0].name) {
            case $T.notificationExt.CHANGE_BODY:
                if (data[0].body == "createUserGroup") {
                    $T.moduleManager.loadModule("html/createUserGroup.html", document.getElementById("body"), "bodyview", $T.createUserGroupMediator);
                } else if (data[0].body == "updateUserGroup") {
                    $T.moduleManager.loadModule("html/updateUserGroup.html", document.getElementById("body"), "bodyview", $T.updateUserGroupMediator);
                } else if (data[0].body == "User") {
                    $T.moduleManager.loadModule("html/user.html", document.getElementById("body"), "bodyview", $T.userMediator);
                } else if (data[0].body == "UpdateUser") {
                    $T.moduleManager.loadModule("html/updateUser.html", document.getElementById("body"), "bodyview", $T.updateUserMediator);
                } else if (data[0].body == "Token") {
                    $T.moduleManager.loadModule("html/token.html", document.getElementById("body"), "bodyview", $T.tokenMediator);
                }
                break;
        }
    }
}
$T.bodyMediator = new BodyMediator();