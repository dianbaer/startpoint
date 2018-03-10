(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var userProxy = window.uca.userProxy;
    var moduleManager = window.juggle.moduleManager;
    var CreateBoxMediator = function () {
        this.userGroupId = null;
        this.initView = function (view, userGroupId) {
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
            this.userGroupId = userGroupId;
            layer.open({
                type: 1,
                content: $('.createBox'),
                title: "创建用户",
                area: ["870px", "550px"],
                skin: 'mySkin',
                btn: ["保存", "取消"],
                btn1: function (index, layero) {
                    //保存按钮
                    //关闭弹窗
                    layer.close(index);
                    var userImg = $("#create_userImg")[0].files;
                    var userName = $("#create_userName").val();
                    var userRole = $("#create_userRole").val();
                    var userPassword = $("#create_userPassword").val();
                    var userAge = $("#create_userAge").val();
                    var userRealName = $("#create_userRealName").val();
                    var userPhone = $("#create_userPhone").val();
                    var userEmail = $("#create_userEmail").val();
                    var userSex = $("input[name='sex']:checked").val();
                    userProxy.createUser(userName, userPassword, userPhone, userEmail, userGroupId, userRealName, userSex, userAge, userRole, userImg);
                    moduleManager.unLoadModule("html/createBox.html");
                },
                btn2: function () {
                    //取消按钮
                    moduleManager.unLoadModule("html/createBox.html");
                },
                cancel: function () {
                    moduleManager.unLoadModule("html/createBox.html");
                }
            });

        };
        Mediator.apply(this);
    };
    window.uca.CreateBoxMediator = CreateBoxMediator;
})(window);