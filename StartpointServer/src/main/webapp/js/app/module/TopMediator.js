(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var TopMediator = function () {
        this.initView = function (view) {

        };
        Mediator.apply(this);
    };
    window.startpoint.TopMediator = TopMediator;
})(window);