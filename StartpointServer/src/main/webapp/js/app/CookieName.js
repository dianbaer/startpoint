(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var CookieName = function () {
        this.TOKEN = "token";
    };
    window.startpoint.cookieName = new CookieName();
})(window);