(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var NotificationExt = function NotificationExt() {
        this.CHANGE_BODY = "changeBody";
    };
    window.startpoint.notificationExt = new NotificationExt();
})(window);