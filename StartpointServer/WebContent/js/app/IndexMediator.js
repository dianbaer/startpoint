(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var moduleManager = window.juggle.moduleManager;
    var TopMediator = window.startpoint.TopMediator;
    var LeftMediator = window.startpoint.LeftMediator;
    var BodyMediator = window.startpoint.BodyMediator;
    var BottomMediator = window.startpoint.BottomMediator;
    var IndexMediator = function () {
        this.initView = function (view) {
            // 模块
            moduleManager.loadModule("html/top.html", document.getElementById("index_top"), null, new TopMediator());
            moduleManager.loadModule("html/left.html", document.getElementById("index_left"), null, new LeftMediator());
            moduleManager.loadModule("html/body.html", document.getElementById("index_body"), null, new BodyMediator());
            moduleManager.loadModule("html/bottom.html", document.getElementById("index_bottom"), null, new BottomMediator());
        };
        Mediator.apply(this);
    };
    window.startpoint.IndexMediator = IndexMediator;
})(window);