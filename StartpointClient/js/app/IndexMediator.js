(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.uca.notificationExt;
    var moduleManager = window.juggle.moduleManager;
    var HeadMediator = window.uca.HeadMediator;
    var LeftMediator = window.uca.LeftMediator;
    var RightMediator = window.uca.RightMediator;
    var EditBoxMediator = window.uca.EditBoxMediator;
    var CreateBoxMediator = window.uca.CreateBoxMediator;
    var UserInfoBoxMediator = window.uca.UserInfoBoxMediator;
    var IndexMediator = function () {
        this.initView = function (view) {


            $(".detail li:last-child").addClass('nobr');
            $(".left").css("minHeight", $(window).height() - 50);
            $(".left").css("height", $(document).height() - 50);

            moduleManager.loadModule("html/head.html", document.getElementById("index_head"), null, new HeadMediator());
            moduleManager.loadModule("html/left.html", document.getElementById("index_left"), null, new LeftMediator());
            moduleManager.loadModule("html/right.html", document.getElementById("index_right"), null, new RightMediator());
        };

        this.listNotificationInterests = [notificationExt.OPEN_BOX];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.OPEN_BOX:
                    if (data.body.name === "editBox") {
                        moduleManager.loadModule("html/editBox.html", document.getElementById("index_editbox"), null, new EditBoxMediator(), data.body.data);
                    } else if (data.body.name === "createBox") {
                        moduleManager.loadModule("html/createBox.html", document.getElementById("index_createbox"), null, new CreateBoxMediator(), data.body.data);
                    } else if (data.body.name === "userInfoBox") {
                        moduleManager.loadModule("html/userInfoBox.html", document.getElementById("index_userinfobox"), null, new UserInfoBoxMediator(), data.body.data);
                    }
                    break;
            }
        };
        Mediator.apply(this);
    };
    window.uca.IndexMediator = IndexMediator;
})(window);
