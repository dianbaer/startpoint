(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var userGroupProxy = window.uca.userGroupProxy;
    var notificationExt = window.uca.notificationExt;
    var CreateGroupMediator = function () {
        this.initView = function (view) {
            $("#createUserGroup").on("click", this.onCreateUserGroup);
        };
        this.onCreateUserGroup = function () {
            var userGroupName = $("#userGroupName").val();
            userGroupProxy.createUserGroup(userGroupName);
        };
        Mediator.apply(this);
    };
    window.uca.CreateGroupMediator = CreateGroupMediator;
})(window);