(function (window) {
    if (!window.uca) window.uca = {};
    var EventDispatcher = window.juggle.EventDispatcher;
    var ucEventType = window.uca.ucEventType;
    var TreeNode = function (data, parent, parentView) {
        this.child = [];
        this.data = data;
        this.view = null;
        this.parent = parent;
        this.parentView = parentView;
        this.isDisplayObject = true;
        this.isOpen = false;
        EventDispatcher.apply(this);
        this.initView = function () {
            var html = null;
            if (this.parent === null) {
                html = '<dd id="' + this.data.userGroupId + '">' +
                    '<a href="javascript:;" >' +
                    '<i class="listIcon_P" id="' + this.data.userGroupId + '_i"></i><span id="' + this.data.userGroupId + '_span" title="' + this.data.userGroupName + '">' + this.data.userGroupName + '</span>' +
                    '<div class="useBox_P">' +
                    '<span class="addIcon1_P" id="' + this.data.userGroupId + '_add"></span>' +
                    '</div>' +
                    '</a>' +
                    '</dd>';
            } else {
                html = '<dd id="' + this.data.userGroupId + '">' +
                    '<a href="javascript:;" >' +
                    '<i class="listIcon_P" id="' + this.data.userGroupId + '_i"></i><span id="' + this.data.userGroupId + '_span" title="' + this.data.userGroupName + '">' + this.data.userGroupName + '</span>' +
                    '<div class="useBox_P">' +
                    '<span class="addIcon1_P" id="' + this.data.userGroupId + '_add"></span>' +
                    '<span class="delIcon1_P" id="' + this.data.userGroupId + '_del"></span>' +
                    '</div>' +
                    '</a>' +
                    '</dd>';
            }

            this.view = $(html);
            this.parentView.append(this.view);
        };
        this.init = function () {
            this.initView();
            if (this.data.childrenNum === undefined) {
                this.data.childrenNum = 0;
            }
            if (this.data.childrenNum > 0) {
                var contianer = '<dl id="' + this.data.userGroupId + '_dl"></dl>'
                $("#" + this.data.userGroupId).append($(contianer));
                this.addOnOpenChildren(this, this.onOpenChildren);
            }
            this.addOnChangeRight(this, this.onChangeRight);
            this.addOnCreateUserGroup(this, this.onCreateUserGroup);
            this.addOnDeleteUserGroup(this, this.onDeleteUserGroup);
        };
        this.addOnOpenChildren = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userGroupId + "_i").on("click", callFunc);
        };
        this.addOnChangeRight = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userGroupId + "_span").on("click", callFunc);
        };
        this.addOnCreateUserGroup = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userGroupId + "_add").on("click", callFunc);
        };
        this.addOnDeleteUserGroup = function (obj, call) {
            var callFunc = function (event) {
                call.call(obj, event);
            };
            $("#" + this.data.userGroupId + "_del").on("click", callFunc);
        };
        this.onOpenChildren = function (event) {
            if (this.isOpen) {
                this.close();
            } else {
                this.dispatchEventWith(ucEventType.OPEN_CHILDREN, true, this.data.userGroupId);
            }
        };
        this.onChangeRight = function (event) {
            this.dispatchEventWith(ucEventType.CHANGE_RIGHT, true, this.data.userGroupId);
        };
        this.onCreateUserGroup = function (event) {
            this.dispatchEventWith(ucEventType.CREATE_USER_GROUP, true, this.data);
        };
        this.onDeleteUserGroup = function (event) {
            event.stopPropagation();
            var self = this;
            layer.confirm('<span class="fontRed">是否删除该用户组</span>', {
                title: "提示",
                btn: ['确定', '关闭'],
                skin: 'mySkin',
                area: ["540px", "300px;"],
                btn1: function (index, layero) {
                    //关闭弹窗
                    layer.close(index);
                    self.dispatchEventWith(ucEventType.DELETE_USER_GROUP, true, self.data);
                },
                btn2: function () {
                    //取消按钮
                }
            })
        };
        this.close = function () {
            $("#" + this.data.userGroupId + "_dl").empty();
            this.child = [];
            this.isOpen = false;
        };
        this.remove = function () {
            $("#" + this.data.userGroupId).remove();
        };
        this.removeChild = function (userGroup) {
            if (userGroup.userGroupParentId === this.data.userGroupId) {
                this.data.childrenNum--;
                for (var i = 0; i < this.child.length; i++) {
                    var treeNode = this.child[i];
                    if (treeNode.data.userGroupId === userGroup.userGroupId) {
                        this.child.splice(i, 1);
                        i--;
                        treeNode.remove();
                    }
                }
                if (this.data.childrenNum === 0) {
                    $("#" + this.data.userGroupId + "_dl").remove();
                    this.isOpen = false;
                }
                return 1;
            } else {
                var type = 0;
                for (var i = 0; i < this.child.length; i++) {
                    var treeNode = this.child[i];
                    var result = treeNode.removeChild(userGroup);
                    if (result === 1 || result === 2) {
                        type = result;
                        break;
                    }
                }
                if (type === 1 || type === 2) {
                    return 2;
                } else {
                    return 0;
                }
            }
        };
        this.appendChild = function (userGroup) {
            if (userGroup.userGroupParentId === this.data.userGroupId) {
                if (this.isOpen) {
                    this.data.childrenNum++;
                    var treeNode = new TreeNode(userGroup, this, $("#" + this.data.userGroupId + "_dl"));
                    this.child.push(treeNode);
                } else {
                    if (this.data.childrenNum > 0) {
                        this.data.childrenNum++;
                    } else {
                        this.data.childrenNum++;
                        var contianer = '<dl id="' + this.data.userGroupId + '_dl"></dl>'
                        $("#" + this.data.userGroupId).append($(contianer));
                        this.addOnOpenChildren(this, this.onOpenChildren);
                    }
                }
                return 1;
            } else {
                var type = 0;
                for (var i = 0; i < this.child.length; i++) {
                    var treeNode = this.child[i];
                    var result = treeNode.appendChild(userGroup);
                    if (result === 1 || result === 2) {
                        type = result;
                        break;
                    }
                }
                if (type === 1 || type === 2) {
                    return 2;
                } else {
                    return 0;
                }
            }
        };
        this.addChildren = function (children, nodeId) {
            if (nodeId === this.data.userGroupId) {
                $("#" + this.data.userGroupId + "_dl").empty();
                this.child = [];
                for (var i = 0; i < children.length; i++) {
                    var userGroup = children[i];
                    var treeNode = new TreeNode(userGroup, this, $("#" + this.data.userGroupId + "_dl"));
                    this.child.push(treeNode);
                }
                this.isOpen = true;
                return 1;
            } else {
                var type = 0;
                var index = 0;
                for (var i = 0; i < this.child.length; i++) {
                    var treeNode = this.child[i];
                    var result = treeNode.addChildren(children, nodeId);
                    if (result === 1 || result === 2) {
                        type = result;
                        index = i;
                        break;
                    }
                }
                if (type === 1) {
                    for (var i = 0; i < this.child.length; i++) {
                        var treeNode = this.child[i];
                        if (i !== index) {
                            treeNode.close();
                        }
                    }
                }
                if (type === 1 || type === 2) {
                    return 2;
                } else {
                    return 0;
                }
            }
        };
        this.init();
    };
    window.uca.TreeNode = TreeNode;
})(window);
