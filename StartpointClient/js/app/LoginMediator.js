(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.uca.notificationExt;
    var tokenProxy = window.uca.tokenProxy;
    var cookieName = window.uca.cookieName;
    var cookieParam = window.uca.cookieParam;
    var ucErrorMsg = window.uca.ucErrorMsg;
    var LoginMediator = function () {
        this.initView = function (view) {
            winSize();
            $(".login_inp_box span").on("click", function () {
                $(this).siblings("input").focus()
            });
            $(".login_inp_box input").focus(function () {
                $(this).siblings('span').hide();
            });
            $(".login_inp_box input").blur(function () {
                var $val = $(this).val();
                if ($val.length == 0 || $val == "") {
                    $(this).siblings('span').show();
                }
            });
            $(window).resize(function () {
                winSize()
            });
            function winSize() {
                var $height = $(window).height();
                $(".login_wrapper,.login_wrapper img").css({
                    "height": $height
                });
                $(".login_main").css("height", $height - 50);
                var img_H = $(".login_main>img").height();
                $(".login_main>img").css("top", ($height - img_H - 50) / 2 + 10);
                $(".login_box").css("top", ($height - 392) / 2);
            }

            $(".login_inp_box input").each(function () {
                var $val = $(this).val();
                if ($val != "") {
                    $(this).siblings('span').hide();
                }
            });
            $("#loginerror").hide();
            $("#loginBtn").on("click", this.onLogin);
        };

        this.onLogin = function (event) {
            $("#loginerror").hide();
            var username = $("#username").val();
            var password = $("#password").val();
            tokenProxy.getToken(username, password);
        };
        this.listNotificationInterests = [notificationExt.LOGIN_SUCCESS, notificationExt.LOGIN_FAIL];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.LOGIN_SUCCESS:
                    cookieParam.setCookieParam(cookieName.TOKEN, data.body.tokenId);
                    window.location.href = "index.html";
                    break;
                case notificationExt.LOGIN_FAIL:
                    $("#loginerror").html('<p>' + ucErrorMsg.errorMap[data.body.errorCode] + '</p>');
                    $("#loginerror").show();
                    break;
            }
        };
        Mediator.apply(this);
    };
    window.uca.LoginMediator = LoginMediator;
})(window);