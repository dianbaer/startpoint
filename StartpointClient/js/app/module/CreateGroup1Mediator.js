(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var userGroupProxy = window.uca.userGroupProxy;
    var notificationExt = window.uca.notificationExt;
    var CreateGroup1Mediator = function () {
        this.userGroup = null;
        this.initView = function (view, userGroup) {
            this.userGroup = userGroup;
            $("#parentUserGroup").val(this.userGroup.userGroupName);
            this.addOnCreateUserGroup(this, this.onCreateUserGroup);
        };
        this.addOnCreateUserGroup = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#createUserGroup").on("click", callFunc);
        };
        this.onCreateUserGroup = function (event) {
            var userGroupName = $("#userGroupName").val();
            userGroupProxy.createUserGroup(userGroupName, this.userGroup.userGroupId);
        };
        Mediator.apply(this);
    };
    window.uca.CreateGroup1Mediator = CreateGroup1Mediator;
})(window);