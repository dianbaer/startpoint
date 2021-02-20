(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var userGroupProxy = window.startpoint.userGroupProxy;
    var CreateUserGroupMediator = function () {
        this.initView = function (view) {
            $("#createUserGroup").on("click", this.onCreateUserGroup);
            $("#getUserGroup").on("click", this.onGetUserGroup);
            $("#getUserGroupList").on("click", this.onGetUserGroupList);
        };
        this.onCreateUserGroup = function (event) {
            var userGroupName = $("#userGroupName").val();
            var userGroupParentId = $("#userGroupParentId").val();
            userGroupProxy.createUserGroup(userGroupName, userGroupParentId);
        };
        this.onGetUserGroup = function (event) {
            var userGroupId = $("#userGroupId").val();
            userGroupProxy.getUserGroup(userGroupId);
        };
        this.onGetUserGroupList = function (event) {
            var userGroupParentId = $("#userGroupParentId_list").val();
            var isUserGroupParentIsNull = $("#isUserGroupParentIsNull_list").val();
            var isRecursion = $("#isRecursion_list").val();
            var userGroupTopId = $("#userGroupTopId_list").val();
            var userGroupState = $("#userGroupState_list").val();
            var currentPage = $("#currentPage").val();
            var pageSize = $("#pageSize").val();
            userGroupProxy.getUserGroupList(userGroupParentId, isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState, currentPage, pageSize);
        };
        Mediator.apply(this);
    };
    window.startpoint.CreateUserGroupMediator = CreateUserGroupMediator;
})(window);