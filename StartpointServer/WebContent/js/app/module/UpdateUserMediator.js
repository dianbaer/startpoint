(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var userProxy = window.startpoint.userProxy;
    var UpdateUserMediator = function () {
        this.initView = function (view) {
            $("#updateUser").on("click", this.onUpdateUser);
            $("#getUserImg").on("click", this.onGetUserImg);
            $("#checkUserName").on("click", this.onCheckUserName);
        };
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
            userProxy.updateUser(userId, userPassword, userPhone, userEmail, userState, isUpdateUserGroup, userGroupId, userRealName, userSex, userAge, userRole, userImg);
        };
        this.onGetUserImg = function (event) {
            var userId = $("#userId_Img").val();
            $("#userImgImg")[0].src = userProxy.getUserImg(userId);
        };
        this.onCheckUserName = function (event) {
            var userName = $("#userName_check").val();
            userProxy.checkUserByUserName(userName);
        };
        Mediator.apply(this);
    };
    window.startpoint.UpdateUserMediator = UpdateUserMediator;
})(window);