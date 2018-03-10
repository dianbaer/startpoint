(function (window) {
    if (!window.uca) window.uca = {};
    var EventDispatcher = window.juggle.EventDispatcher;
    var ucEventType = window.uca.ucEventType;
    var UserNode = function (data, parentView) {
        this.data = data;
        this.view = null;
        this.parentView = parentView;
        EventDispatcher.apply(this);
        this.initView = function () {
            var html = '<tr>' +
                '<td><img src="' + this.data.userImgUrl + '&t=' + new Date().getTime() + '" alt=""></td>' +
                '<td><a href="javascript:;" class="user_info_zy" id="' + this.data.userId + '_info">' + this.data.userName + '</a></td>' +
                '<td>' + (this.data.userRealName === undefined ? '无' : this.data.userRealName) + '</td>' +
                '<td>' + (this.data.userPhone === undefined ? '无' : this.data.userPhone) + '</td>' +
                '<td>' + (this.data.userRole === 1 ? '普通用户' : '企业管理员') + '</td>' +
                '<td>' +
                '<a href="javascript:;" class="useredit" id="' + this.data.userId + '_update">修改</a>' +
                '<a href="javascript:;" class="userdelete" id="' + this.data.userId + '_del">删除</a>' +
                '</td>' +
                '</tr>';
            this.view = $(html);
            this.parentView.append(this.view);
        };
        this.init = function () {
            this.initView();
            this.addOnUpdateUser(this, this.onUpdateUser);
            this.addOnDeleteUser(this, this.onDeleteUser);
            this.addOnUserInfo(this, this.onUserInfo);
        };
        this.addOnUpdateUser = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userId + "_update").on("click", callFunc);
        };
        this.addOnDeleteUser = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userId + "_del").on("click", callFunc);
        };
        this.addOnUserInfo = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userId + "_info").on("click", callFunc);
        };
        this.onUpdateUser = function (event) {
            this.dispatchEventWith(ucEventType.UPDATE_USER, true, this.data);
        };
        this.onUserInfo = function (event) {
            this.dispatchEventWith(ucEventType.USER_INFO, true, this.data);
        };
        this.onDeleteUser = function (event) {
            event.stopPropagation();
            var self = this;
            layer.confirm('<span class="fontRed">是否删除该用户</span>', {
                title: "提示",
                btn: ['确定', '关闭'],
                skin: 'mySkin',
                area: ["540px", "300px;"],
                btn1: function (index, layero) {
                    //关闭弹窗
                    layer.close(index);
                    self.dispatchEventWith(ucEventType.DELETE_USER, true, self.data);
                },
                btn2: function () {
                    //取消按钮
                }
            })
        };
        this.init();
    };
    window.uca.UserNode = UserNode;
})(window);