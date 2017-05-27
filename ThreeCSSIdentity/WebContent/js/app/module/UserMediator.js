function UserMediator() {

    this.init = function (view) {


        $("#createUser").on("click", this.onCreateUser);
        $("#getUser").on("click", this.onGetUser);
        $("#getUserList").on("click", this.onGetUserList);

    }
    // 注销方法
    this.dispose = function () {


        $("#createUser").remove("click", this.onCreateUser);
        $("#getUser").remove("click", this.onGetUser);
        $("#getUserList").remove("click", this.onGetUserList);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onCreateUser = function (event) {
        var userName = $("#userName").val();
        var userPassword = $("#userPassword").val();
        var userPhone = $("#userPhone").val();
        var userEmail = $("#userEmail").val();
        var userGroupId = $("#userGroupId").val();
        var userRealName = $("#userRealName").val();
        var userSex = $("#userSex").val();
        var userAge = $("#userAge").val();
        var userRole = $("#userRole").val();
        var userImg = $("#userImg")[0].files;
        $T.userProxy.createUser(userName, userPassword, userPhone, userEmail, userGroupId, userRealName, userSex, userAge, userRole, userImg);
    }
    this.onGetUser = function (event) {
        var userId = $("#userId").val();
        $T.userProxy.getUser(userId);
    }
    this.onGetUserList = function (event) {
        var userGroupId = $("#userGroupId_list").val();
        var isUserGroupIsNull = $("#isUserGroupIsNull_list").val();
        var isRecursion = $("#isRecursion_list").val();
        var userState = $("#userState_list").val();
        var userSex = $("#userSex_list").val();
        var userRole = $("#userRole_list").val();
        var userGroupTopId = $("#userGroupTopId_list").val();
        $T.userProxy.getUserList(userGroupId, isUserGroupIsNull, isRecursion, userState, userSex, userRole, userGroupTopId);
    }

}
$T.userMediator = new UserMediator();