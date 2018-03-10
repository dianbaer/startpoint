(function (window) {
    if (!window.uca) window.uca = {};
    var Url = function () {
        this.ucUrl = "http://localhost:8080/StartpointServer/s";
    };
    window.uca.url = new Url();
})(window);