(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var userGroupProxy = window.startpoint.userGroupProxy;
    var UpdateUserGroupMediator = function () {
        this.initView = function (view) {
            $("#updateUserGroup").on("click", this.onClick);
            $("#deleteUserGroup").on("click", this.onDeleteUserGroup);
        };
        this.onClick = function (event) {
            var userGroupId = $("#userGroupId").val();
            var userGroupName = $("#userGroupName").val();
            var isUpdateUserGroupParent = $("#isUpdateUserGroupParent").val();
            var userGroupParentId = $("#userGroupParentId").val();
            var userGroupState = $("#userGroupState").val();
            userGroupProxy.updateUserGroup(userGroupId, userGroupName, isUpdateUserGroupParent, userGroupParentId, userGroupState);
        };
        this.onDeleteUserGroup = function (event) {
            var userGroupId = $("#userGroupIdDelete").val();
            userGroupProxy.deleteUserGroup(userGroupId);
        };
        Mediator.apply(this);
    };
    window.startpoint.UpdateUserGroupMediator = UpdateUserGroupMediator;
})(window);