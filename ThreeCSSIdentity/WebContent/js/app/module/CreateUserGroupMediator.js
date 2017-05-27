function CreateUserGroupMediator() {

    this.init = function (view) {


        $("#createUserGroup").on("click", this.onCreateUserGroup);
        $("#getUserGroup").on("click", this.onGetUserGroup);
        $("#getUserGroupList").on("click", this.onGetUserGroupList);
    }
    // 注销方法
    this.dispose = function () {


        $("#createUserGroup").remove("click", this.onCreateUserGroup);
        $("#getUserGroup").remove("click", this.onGetUserGroup);
        $("#getUserGroupList").remove("click", this.onGetUserGroupList);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onCreateUserGroup = function (event) {
        var userGroupName = $("#userGroupName").val();
        var userGroupParentId = $("#userGroupParentId").val();
        $T.userGroupProxy.createUserGroup(userGroupName, userGroupParentId);
    }
    this.onGetUserGroup = function (event) {
        var userGroupId = $("#userGroupId").val();
        $T.userGroupProxy.getUserGroup(userGroupId);
    }
    this.onGetUserGroupList = function (event) {
        var userGroupParentId = $("#userGroupParentId_list").val();
        var isUserGroupParentIsNull = $("#isUserGroupParentIsNull_list").val();
        var isRecursion = $("#isRecursion_list").val();
        var userGroupTopId = $("#userGroupTopId_list").val();
        var userGroupState = $("#userGroupState_list").val();
        $T.userGroupProxy.getUserGroupList(userGroupParentId, isUserGroupParentIsNull, isRecursion, userGroupTopId, userGroupState);
    }
}
$T.createUserGroupMediator = new CreateUserGroupMediator();