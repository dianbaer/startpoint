(function (window) {
    if (!window.uca) window.uca = {};
    var CookieName = function () {
        this.TOKEN = "token";
    };
    window.uca.cookieName = new CookieName();
})(window);