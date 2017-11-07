(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var tokenProxy = window.startpoint.tokenProxy;
    var TokenMediator = function () {
        this.initView = function (view) {
            $("#getToken").on("click", this.onGetToken);
            $("#updateToken").on("click", this.onUpdateToken);
            $("#deleteToken").on("click", this.onDeleteToken);

        };
        this.onGetToken = function (event) {
            var userName = $("#userName").val();
            var userPassword = $("#userPassword").val();

            tokenProxy.getToken(userName, userPassword);
        };
        this.onUpdateToken = function (event) {

            tokenProxy.updateToken();
        };
        this.onDeleteToken = function (event) {

            tokenProxy.deleteToken();
        };
        Mediator.apply(this);
    };
    window.startpoint.TokenMediator = TokenMediator;
})(window);