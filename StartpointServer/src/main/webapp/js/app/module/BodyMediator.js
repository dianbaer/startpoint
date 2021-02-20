(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.startpoint.notificationExt;
    var moduleManager = window.juggle.moduleManager;
    var CreateUserGroupMediator = window.startpoint.CreateUserGroupMediator;
    var UpdateUserGroupMediator = window.startpoint.UpdateUserGroupMediator;
    var UserMediator = window.startpoint.UserMediator;
    var UpdateUserMediator = window.startpoint.UpdateUserMediator;
    var TokenMediator = window.startpoint.TokenMediator;
    var BodyMediator = function () {
        this.initView = function (view) {
            moduleManager.loadModule("html/createUserGroup.html", document.getElementById("body"), "bodyview", new CreateUserGroupMediator());
        };

        // 关心消息数组
        this.listNotificationInterests = [notificationExt.CHANGE_BODY];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.CHANGE_BODY:
                    if (data.body === "createUserGroup") {
                        moduleManager.loadModule("html/createUserGroup.html", document.getElementById("body"), "bodyview", new CreateUserGroupMediator());
                    } else if (data.body === "updateUserGroup") {
                        moduleManager.loadModule("html/updateUserGroup.html", document.getElementById("body"), "bodyview", new UpdateUserGroupMediator());
                    } else if (data.body === "User") {
                        moduleManager.loadModule("html/user.html", document.getElementById("body"), "bodyview", new UserMediator());
                    } else if (data.body === "UpdateUser") {
                        moduleManager.loadModule("html/updateUser.html", document.getElementById("body"), "bodyview", new UpdateUserMediator());
                    } else if (data.body === "Token") {
                        moduleManager.loadModule("html/token.html", document.getElementById("body"), "bodyview", new TokenMediator());
                    }
                    break;
            }
        };
        Mediator.apply(this);
    };
    window.startpoint.BodyMediator = BodyMediator;
})(window);