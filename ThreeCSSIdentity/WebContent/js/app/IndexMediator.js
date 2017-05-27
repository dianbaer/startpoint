function IndexMediator() {

    this.init = function (view) {

        // 模块
        $T.moduleManager.loadModule("html/top.html", document.getElementById("index_top"), null, $T.topMediator);
        $T.moduleManager.loadModule("html/left.html", document.getElementById("index_left"), null, $T.leftMediator);
        $T.moduleManager.loadModule("html/body.html", document.getElementById("index_body"), null, $T.bodyMediator);
        $T.moduleManager.loadModule("html/bottom.html", document.getElementById("index_bottom"), null, $T.bottomMediator);

    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.advanceTime = function (passedTime) {

    }
}
$T.indexMediator = new IndexMediator();