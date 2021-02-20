(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.startpoint.notificationExt;
    var LeftMediator = function () {
        this.initView = function (view) {
            this.addClick1(this, this.onClick);
            this.addClick2(this, this.onClick1);
            this.addClick3(this, this.onClick2);
            this.addClick4(this, this.onClick3);
            this.addClick5(this, this.onClick4);
        };
        this.addClick1 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_button").on("click", callFunc);
        };
        this.addClick2 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_button1").on("click", callFunc);
        };
        this.addClick3 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_button2").on("click", callFunc);
        };
        this.addClick4 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_button3").on("click", callFunc);
        };
        this.addClick5 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_button4").on("click", callFunc);
        };
        this.onClick = function (event) {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_BODY, "createUserGroup"));
        };
        this.onClick1 = function (event) {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_BODY, "updateUserGroup"));
        };
        this.onClick2 = function (event) {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_BODY, "User"));
        };
        this.onClick3 = function (event) {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_BODY, "UpdateUser"));
        };
        this.onClick4 = function (event) {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_BODY, "Token"));
        };
        Mediator.apply(this);
    };
    window.startpoint.LeftMediator = LeftMediator;
})(window);