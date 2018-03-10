(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var userProxy = window.uca.userProxy;
    var moduleManager = window.juggle.moduleManager;
    var EditBoxMediator = function () {
        this.user = null;
        this.initView = function (view, user) {
            $('input').iCheck({
                radioClass: 'iradio_square-blue'
            });
            $("select").selectOrDie({
                customClass: "m_select",
            });
            //错误提示
            $(".error_msg").each(function () {
                if (!$(this).prev().hasClass('error_inp')) {
                    $(this).prev().addClass('error_inp')
                }
            });
            this.user = user;
            $("#userPhoto")[0].src = this.user.userImgUrl + "&t=" + new Date().getTime();
            $("#userName").val(this.user.userName);
            if (this.user.userRole === 1) {
                $("#userRole").find("option[value='1']").attr("selected", 'selected')
            } else {
                $("#userRole").find("option[value='2']").attr("selected", 'selected')
            }
            $("select").selectOrDie('update')
            $("#userAge").val(this.user.userAge);
            $("#userRealName").val(this.user.userRealName);
            $("#userPhone").val(this.user.userPhone);
            $("#userEmail").val(this.user.userEmail);
            if (this.user.userSex === 1) {
                $("#female").attr("checked", "checked")
            } else {
                $("#male").attr("checked", "checked")
            }
            $('input').iCheck('update')

            layer.open({
                type: 1,
                content: $('.editBox'),
                title: "用户修改",
                area: ["870px", "550px"],
                skin: 'mySkin',
                btn: ["保存", "取消"],
                btn1: function (index, layero) {
                    //保存按钮

                    //关闭弹窗
                    layer.close(index);
                    var userImg = $("#userImg")[0].files;
                    var userRole = $("#userRole").val();
                    var userPassword = $("#userPassword").val();
                    var userAge = $("#userAge").val();
                    var userRealName = $("#userRealName").val();
                    var userPhone = $("#userPhone").val();
                    var userEmail = $("#userEmail").val();
                    var userSex = $("input[name='sex2']:checked").val();
                    if (userPassword === null || userPassword === undefined) {
                        userPassword = null;
                    }
                    userProxy.updateUser(user.userId, userPassword, userPhone, userEmail, 0, 0, null, userRealName, userSex, userAge, userRole, userImg);
                    moduleManager.unLoadModule("html/editBox.html");
                },
                btn2: function () {
                    //取消按钮
                    moduleManager.unLoadModule("html/editBox.html");
                },
                cancel: function () {
                    moduleManager.unLoadModule("html/editBox.html");
                }
            });
        };
        Mediator.apply(this);
    };
    window.uca.EditBoxMediator = EditBoxMediator;
})(window);