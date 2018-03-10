(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var moduleManager = window.juggle.moduleManager;
    var UserListMediator = window.uca.UserListMediator;
    var CreateGroup1Mediator = window.uca.CreateGroup1Mediator;
    var CreateGroupMediator = window.uca.CreateGroupMediator;
    var notificationExt = window.uca.notificationExt;
    var userGroupProxy = window.uca.userGroupProxy;
    var RightMediator = function () {
        this.nowState = "userList";
        this.initView = function (view) {
            moduleManager.loadModule("html/userList.html", document.getElementById("right_content"), "right_content", new UserListMediator());
            this.nowState = "userList";
        };
        this.listNotificationInterests = [notificationExt.CHANGE_RIGHT_CONTENT, notificationExt.GET_USER_GROUP_RECURSION_SUCCESS, notificationExt.SHOW_RIGHT_USER_GROUP_INFO];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.CHANGE_RIGHT_CONTENT:
                    if (data.body.name === "userList") {
                        moduleManager.loadModule("html/userList.html", document.getElementById("right_content"), "right_content", new UserListMediator(), data.body.data);
                        this.nowState = "userList";
                    } else if (data.body.name === "createGroup") {
                        moduleManager.loadModule("html/createGroup.html", document.getElementById("right_content"), "right_content", new CreateGroupMediator(), data.body.data);
                        this.nowState = "createGroup";
                    } else if (data.body.name === "createGroup1") {
                        moduleManager.loadModule("html/createGroup1.html", document.getElementById("right_content"), "right_content", new CreateGroup1Mediator(), data.body.data);
                        this.nowState = "createGroup1";
                    }
                    break;
                case notificationExt.GET_USER_GROUP_RECURSION_SUCCESS:
                    this.getUserGroupRecursion(data.body);
                    break;
                case notificationExt.SHOW_RIGHT_USER_GROUP_INFO:
                    this.showRightUserGroupInfo(data.body);
                    if (this.nowState !== "userList") {
                        moduleManager.loadModule("html/userList.html", document.getElementById("right_content"), "right_content", new UserListMediator(), data.body);
                        this.nowState = "userList";
                    }
                    break;
            }
        };
        this.showRightUserGroupInfo = function (userGroupId) {
            userGroupProxy.getUserGroupRecursion(userGroupId);
        };
        this.getUserGroupRecursion = function (data) {
            $("#right_recursion").empty();
            for (var i = data.recursionUserGroupList.length - 1; i >= 0; i--) {
                var userGroup = data.recursionUserGroupList[i];
                var html = "";
                if (i !== 0) {
                    html += '<a href="javascript:;">' + userGroup.userGroupName + '</a>';
                } else {
                    html += '<span href="javascript:;" class="crumb_cur">' + userGroup.userGroupName + '</span>';
                }
                $("#right_recursion").append($(html));
            }
        };
        Mediator.apply(this);
    };
    window.uca.RightMediator = RightMediator;
})(window);