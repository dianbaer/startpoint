(function (window) {
    if (!window.startpoint) window.startpoint = {};
    var Mediator = window.juggle.Mediator;
    var userProxy = window.startpoint.userProxy;
    var UserMediator = function () {
        this.initView = function (view) {
            $("#createUser").on("click", this.onCreateUser);
            $("#getUser").on("click", this.onGetUser);
            $("#getUserList").on("click", this.onGetUserList);
        };
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
            userProxy.createUser(userName, userPassword, userPhone, userEmail, userGroupId, userRealName, userSex, userAge, userRole, userImg);
        };
        this.onGetUser = function (event) {
            var userId = $("#userId").val();
            userProxy.getUser(userId);
        };
        this.onGetUserList = function (event) {
            var userGroupId = $("#userGroupId_list").val();
            var isUserGroupIsNull = $("#isUserGroupIsNull_list").val();
            var isRecursion = $("#isRecursion_list").val();
            var userState = $("#userState_list").val();
            var userSex = $("#userSex_list").val();
            var userRole = $("#userRole_list").val();
            var userGroupTopId = $("#userGroupTopId_list").val();
            var userName = $("#userName_list").val();
            var userCreateTimeGreaterThan = $("#userCreateTimeGreaterThan").val();
            var userCreateTimeLessThan = $("#userCreateTimeLessThan").val();
            var currentPage = $("#currentPage").val();
            var pageSize = $("#pageSize").val();
            userProxy.getUserList(userGroupId, isUserGroupIsNull, isRecursion, userState, userSex, userRole, userGroupTopId, userName, userCreateTimeGreaterThan, userCreateTimeLessThan, currentPage, pageSize);
        };
        Mediator.apply(this);
    };
    window.startpoint.UserMediator = UserMediator;
})(window);