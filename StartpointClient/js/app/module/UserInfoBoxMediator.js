(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var moduleManager = window.juggle.moduleManager;
    var UserInfoBoxMediator = function () {
        this.initView = function (view, user) {
            $("#userName_info").text(user.userName);
            $("#userPhone_info").text((user.userPhone === undefined ? "无" : user.userPhone));
            $("#userEmail_info").text((user.userEmail === undefined ? "无" : user.userEmail));
            $("#userRealName_info").text((user.userRealName === undefined ? "无" : user.userRealName));
            $("#userCreateTime_info").text((user.userCreateTime === undefined ? "无" : user.userCreateTime));
            $("#userSex_info").text((user.userSex === 1 ? "女" : "男"));
            $("#userUpdateTime_info").text((user.userUpdateTime === undefined ? "无" : user.userUpdateTime));
            $("#userAge_info").text((user.userAge === undefined ? "无" : user.userAge));
            $("#userRole_info").text((user.userRole === 1 ? "普通用户" : "企业管理员"));
            layer.open({
                type: 1,
                content: $('.user_info_box'),
                title: "用户详情",
                area: ["870px", "400px"],
                skin: 'mySkin mySkin_zy',
                btn: "关闭",
                btn1: function (index, layero) {
                    //关闭按钮

                    //关闭弹窗
                    layer.close(index);
                    moduleManager.unLoadModule("html/userInfoBox.html");
                },
                btn2: function () {
                    //取消按钮
                    moduleManager.unLoadModule("html/userInfoBox.html");
                },
                cancel: function () {
                    moduleManager.unLoadModule("html/userInfoBox.html");
                }
            });
        };

        Mediator.apply(this);
    };
    window.uca.UserInfoBoxMediator = UserInfoBoxMediator;
})(window);