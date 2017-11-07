(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var BottomMediator = function () {
        this.initView = function (view) {

        };
        Mediator.apply(this);
    };
    window.startpoint.BottomMediator = BottomMediator;
})(window);