(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.uca.notificationExt;
    var userProxy = window.uca.userProxy;
    var userGroupProxy = window.uca.userGroupProxy;
    var UserNode = window.uca.UserNode;
    var ucEventType = window.uca.ucEventType;
    var UserListMediator = function () {
        this.pageSize = 10;
        this.currentPage = 1;
        this.isInit = false;
        this.userGroupId = null;
        this.initView = function (view, userGroupId) {
            this.isInit = true;
            this.addClick(this, this.onOpenCreateUser);
            this.addClick1(this, this.onOpenUserInfo);
            this.addClick2(this, this.onEditUserInfo);
            $(".userdelete").on("click", this.onDeleteUser);
            if (userGroupId !== null && userGroupId !== undefined) {
                this.showRightUserGroupInfo(userGroupId);
                userGroupProxy.getUserGroupRecursion(userGroupId);
                this.userGroupId = userGroupId;
            }
        };
        this.addClick = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#createUser").on("click", callFunc);
        };
        this.addClick1 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $(".user_info_zy").on("click", callFunc);
        };
        this.addClick2 = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $(".useredit").on("click", callFunc);
        };
        this.onOpenCreateUser = function () {
            this.notifyObservers(this.getNotification(notificationExt.OPEN_BOX, {
                name: "createBox",
                data: this.userGroupId
            }));
        };
        this.onOpenUserInfo = function () {
            this.notifyObservers(this.getNotification(notificationExt.OPEN_BOX, {name: "userInfoBox", data: null}));
        };
        this.onEditUserInfo = function () {
            this.notifyObservers(this.getNotification(notificationExt.OPEN_BOX, {name: "editBox", data: null}));
        };
        this.onDeleteUser = function () {
            layer.confirm('<span class="fontRed">是否删除该用户</span>', {
                title: "提示",
                btn: ['确定', '关闭'],
                skin: 'mySkin',
                area: ["540px", "300px"],
                btn1: function (index, layero) {
                    //确定按钮

                    //关闭弹窗
                    layer.close(index);
                },
                btn2: function () {
                    //取消按钮
                }
            })
        };
        this.listNotificationInterests = [notificationExt.SHOW_RIGHT_USER_GROUP_INFO, notificationExt.GET_USER_LIST_SUCCESS, notificationExt.GET_USER_GROUP_RECURSION_SUCCESS, notificationExt.CREATE_USER_SUCCESS, notificationExt.UPDATE_USER_SUCCESS];
        // 关心的消息处理
        this.handleNotification = function (data) {
            if (this.isInit === false) {
                return;
            }
            switch (data.name) {

                case notificationExt.SHOW_RIGHT_USER_GROUP_INFO:
                    this.showRightUserGroupInfo(data.body);
                    break;
                case notificationExt.GET_USER_LIST_SUCCESS:
                    this.getUserListSuccess(data.body);
                    break;
                case notificationExt.GET_USER_GROUP_RECURSION_SUCCESS:
                    this.getUserGroupRecursionSuccess(data.body);
                    break;
                case notificationExt.CREATE_USER_SUCCESS:
                case notificationExt.UPDATE_USER_SUCCESS:
                    this.refreshUserList();
                    break;

            }
        };
        this.refreshUserList = function () {
            userProxy.getUserList(this.userGroupId, null, true, null, null, null, null, null, null, null, this.currentPage, this.pageSize);
        };
        this.showRightUserGroupInfo = function (userGroupId) {
            this.userGroupId = userGroupId;
            userProxy.getUserList(userGroupId, null, true, null, null, null, null, null, null, null, this.currentPage, this.pageSize);
        };
        this.getUserListSuccess = function (data) {
            var parent = $("#userListBody");
            parent.empty();
            if (data.user === null || data.user === undefined) {
                return;
            }
            for (var i = 0; i < data.user.length; i++) {
                var user = data.user[i];
                var userNode = new UserNode(user, parent);
                userNode.addEventListener(ucEventType.UPDATE_USER, this.onUpdateUser, this);
                userNode.addEventListener(ucEventType.DELETE_USER, this.onDeleteUser, this);
                userNode.addEventListener(ucEventType.USER_INFO, this.onUserInfo, this);
            }
            var self = this;
            this.currentPage = data.currentPage;
            //分页
            var time = 0;
            $("#Pagination").pagination(data.totalPage, {
                num_edge_entries: 2,
                num_display_entries: 4,
                current_page: data.currentPage - 1,
                items_per_page: 1,
                prev_text: "上一页",
                next_text: "下一页",
                callback: function (a) {
                    time++;
                    if (time === 1) {
                        return;
                    }
                    var page = a + 1;
                    if (self.currentPage !== page) {
                        userProxy.getUserList(self.userGroupId, null, true, null, null, null, null, null, null, null, page, self.pageSize);
                    }
                }
            });
        };
        this.onUpdateUser = function (event) {
            var user = event.mData;
            this.notifyObservers(this.getNotification(notificationExt.OPEN_BOX, {
                name: "editBox",
                data: user
            }));
        };
        this.onUserInfo = function (event) {
            var user = event.mData;
            this.notifyObservers(this.getNotification(notificationExt.OPEN_BOX, {
                name: "userInfoBox",
                data: user
            }));
        };
        this.onDeleteUser = function (event) {
            var user = event.mData;
            userProxy.updateUser(user.userId, null, null, null, 3)
        };
        this.getUserGroupRecursionSuccess = function (data) {
            if (data.recursionUserGroupList.length > 1) {
                var userGroup = data.recursionUserGroupList[0];
                var parentUserGroup = data.recursionUserGroupList[1];
                var topUserGroup = data.recursionUserGroupList[data.recursionUserGroupList.length - 1];
                $("#topUserGroup").text(topUserGroup.userGroupName);
                $("#parentUserGroup").text(parentUserGroup.userGroupName);
                $("#userGroupCreateTime").text(userGroup.userGroupCreateTime);
                $("#userGroupUpdateTime").text(userGroup.userGroupUpdateTime);
            } else {
                var userGroup = data.recursionUserGroupList[0];
                $("#topUserGroup").text("无");
                $("#parentUserGroup").text("无");
                $("#userGroupCreateTime").text(userGroup.userGroupCreateTime);
                $("#userGroupUpdateTime").text(userGroup.userGroupUpdateTime);
            }
        };
        Mediator.apply(this);
    };
    window.uca.UserListMediator = UserListMediator;
})(window);