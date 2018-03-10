(function (window) {
    if (!window.uca) window.uca = {};
    var UCEventType = function () {
        this.OPEN_CHILDREN = "openChildren";
        this.CHANGE_RIGHT = "changeRight";
        this.CREATE_USER_GROUP = "createUserGroup";
        this.DELETE_USER_GROUP = "deleteUserGroup";
        this.UPDATE_USER = "updateUser";
        this.DELETE_USER = "deleteUser";
        this.USER_INFO = "userInfo";
    };
    window.uca.ucEventType = new UCEventType();
})(window);