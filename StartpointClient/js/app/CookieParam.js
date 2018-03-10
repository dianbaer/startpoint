(function (window) {
    if (!window.uca) window.uca = {};
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
    window.uca.cookieParam = new CookieParam();
})(window);