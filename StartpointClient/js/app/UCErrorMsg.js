(function (window) {
    if (!window.uca) window.uca = {};
    var UCErrorMsg = function () {
        this.errorMap = [];
        this.errorMap["ERROR_CODE_0"] = "未知错误";
        this.errorMap["ERROR_CODE_1"] = "该token已过期或不合法";
        this.errorMap["ERROR_CODE_2"] = "该请求token不许为空";
        this.errorMap["ERROR_CODE_3"] = "该电话号码已被注册，添加用户失败";
        this.errorMap["ERROR_CODE_4"] = "不存在这个用户";
        this.errorMap["ERROR_CODE_5"] = "该用户已冻结或删除";
        this.errorMap["ERROR_CODE_6"] = "密码错误";
        this.errorMap["ERROR_CODE_7"] = "创建token失败";
        this.errorMap["ERROR_CODE_8"] = "延长token过期时间失败";
        this.errorMap["ERROR_CODE_9"] = "删除token失败";
        this.errorMap["ERROR_CODE_10"] = "创建用户组失败";
        this.errorMap["ERROR_CODE_11"] = "修改用户组失败";
        this.errorMap["ERROR_CODE_12"] = "获取用户组失败";
        this.errorMap["ERROR_CODE_13"] = "创建用户失败";
        this.errorMap["ERROR_CODE_14"] = "修改用户失败";
        this.errorMap["ERROR_CODE_15"] = "删除用户组失败";
        this.errorMap["ERROR_CODE_16"] = "获取admintoken失败";
        this.errorMap["ERROR_CODE_17"] = "你没有权限";
    };
    window.uca.ucErrorMsg = new UCErrorMsg();
})(window);