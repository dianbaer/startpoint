function UpdateUserMediator() {

    this.init = function (view) {


        $("#updateUser").on("click", this.onUpdateUser);
        $("#getUserImg").on("click", this.onGetUserImg);

    }
    // 注销方法
    this.dispose = function () {


        $("#updateUser").remove("click", this.onUpdateUser);
        $("#getUserImg").remove("click", this.onGetUserImg);

    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onUpdateUser = function (event) {
        var userId = $("#userId").val();
        var userPassword = $("#userPassword").val();
        var userPhone = $("#userPhone").val();
        var userEmail = $("#userEmail").val();
        var userState = $("#userState").val();
        var isUpdateUserGroup = $("#isUpdateUserGroup").val();
        var userGroupId = $("#userGroupId").val();
        var userRealName = $("#userRealName").val();
        var userSex = $("#userSex").val();
        var userAge = $("#userAge").val();
        var userRole = $("#userRole").val();
        var userImg = $("#userImg")[0].files;
        $T.userProxy.updateUser(userId, userPassword, userPhone, userEmail, userState, isUpdateUserGroup, userGroupId, userRealName, userSex, userAge, userRole, userImg);
    }
    this.onGetUserImg = function (event) {
        var userId = $("#userId_Img").val();
        $("#userImgImg")[0].src = $T.userProxy.getUserImg(userId);
    }

}
$T.updateUserMediator = new UpdateUserMediator();