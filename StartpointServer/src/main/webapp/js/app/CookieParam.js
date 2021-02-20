(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var CookieParam = function () {
        this.getCookieParam = function (name) {
            return $.cookie(name);
        };
        this.setCookieParam = function (name, value) {
            $.cookie(name, value);
        };
        this.deleteCookieParam = function (name) {
            $.cookie(name, null);
        }
    };
    window.startpoint.cookieParam = new CookieParam();
})(window);