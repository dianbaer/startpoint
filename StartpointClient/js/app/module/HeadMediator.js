(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var userProxy = window.uca.userProxy;
    var notificationExt = window.uca.notificationExt;
    var HeadMediator = function () {
        this.initView = function (view) {
            userProxy.getUser();
        };
        this.listNotificationInterests = [notificationExt.GET_USER_SUCCESS];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.GET_USER_SUCCESS:
                    $("#head_userName").text("你好，" + data.body.user.userName);
                    break;
            }
        };
        Mediator.apply(this);
    };
    window.uca.HeadMediator = HeadMediator;
})(window);