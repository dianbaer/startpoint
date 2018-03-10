(function (window) {
    if (!window.uca) window.uca = {};
    var Mediator = window.juggle.Mediator;
    var notificationExt = window.uca.notificationExt;
    var userGroupProxy = window.uca.userGroupProxy;
    var ucEventType = window.uca.ucEventType;
    var TreeNode = window.uca.TreeNode;
    var LeftMediator = function () {
        this.treeMap = [];
        this.nowTree = null;
        this.nowGroup = null;
        this.initView = function (view) {
            $(".left").css("minHeight", $(window).height() - 50)
            $(".left").css("height", $(document).height() - 50)
            var body = $("body");
            //搜索模块结束
            //导航开始
            var bodyH = $(window).height() - 50;
            $(".left").css("minHeight", bodyH);


            body.on("click", ".navList_P dd a i", function (event) {
                event.stopPropagation();
                $(this).parent().toggleClass("open");
                $(this).parents("dd").siblings().find('a').removeClass("open");
                $(this).parent().next("dl").stop().slideToggle();
                $(this).parents("dd").siblings().find('dl').slideUp()
            });
            //搜索模块开始
            $(".searchIcon_P").on("click", function () {
                $(this).addClass('hide');
                $(this).parent().animate({
                    width: 0
                }, 200)
            });
            $(".searchIon2_P").on("click", function () {
                $(".searchAdd_P").animate({
                    width: '100%'
                }, 300, function () {
                    $(".searchIcon_P").removeClass("hide")
                })
            });
            this.addClick(this, this.onClickAddGroup);
            userGroupProxy.getUserGroupTree();
        };

        this.addClick = function (mediator, call) {
            var callFunc = function (event) {
                call.call(mediator, event);
            };
            $("#left_addGroup").on("click", callFunc);
        };
        this.onClickAddGroup = function () {
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_RIGHT_CONTENT, {name: "createGroup"}));
        };
        this.listNotificationInterests = [notificationExt.GET_USER_GROUP_TREE_SUCCESS, notificationExt.GET_USER_GROUP_TREE_NODE_SUCCESS, notificationExt.CREATE_USER_GROUP_SUCCESS, notificationExt.DELETE_USER_GROUP_SUCCESS];
        // 关心的消息处理
        this.handleNotification = function (data) {
            switch (data.name) {
                case notificationExt.GET_USER_GROUP_TREE_SUCCESS:
                    this.initForest(data.body);
                    break;
                case notificationExt.GET_USER_GROUP_TREE_NODE_SUCCESS:
                    this.treeMap[data.body.treeId.treeId].addChildren(data.body.result.userGroup, data.body.treeId.nodeId);
                    this.resetTree();
                    break;
                case notificationExt.CREATE_USER_GROUP_SUCCESS:
                    this.addTreeNode(data.body.userGroup);
                    break;
                case notificationExt.DELETE_USER_GROUP_SUCCESS:
                    this.removeTreeNode(data.body.userGroup);
                    break;
            }
        };
        this.removeTreeNode = function (userGroup) {
            if (userGroup.userGroupTopId === undefined || userGroup.userGroupTopId === null) {
                //var treeNode = this.treeMap[userGroup.userGroupId];
                //delete this.treeMap[userGroup.userGroupId];
                //treeNode.remove();
            } else {
                this.treeMap[userGroup.userGroupTopId].removeChild(userGroup);
                this.resetTree();
            }
        };
        this.addTreeNode = function (userGroup) {
            this.notifyObservers(this.getNotification(notificationExt.SHOW_RIGHT_USER_GROUP_INFO, this.nowGroup.data.userGroupId));
            if (userGroup.userGroupTopId === undefined || userGroup.userGroupTopId === null) {
                var parent = $("#left_tree");
                var treeNode = new TreeNode(userGroup, null, parent);
                treeNode.isDisplayObject = false;
                treeNode.addEventListener(ucEventType.OPEN_CHILDREN, this.onOpenChildren, this);
                treeNode.addEventListener(ucEventType.CHANGE_RIGHT, this.onChangeRight, this);
                treeNode.addEventListener(ucEventType.CREATE_USER_GROUP, this.onCreateUserGroup, this);
                treeNode.addEventListener(ucEventType.DELETE_USER_GROUP, this.onDeleteUserGroup, this);
                this.treeMap[userGroup.userGroupId] = treeNode;
                this.resetTree();
            } else {
                this.treeMap[userGroup.userGroupTopId].appendChild(userGroup);
                this.resetTree();
            }
        };
        this.initForest = function (data) {
            var parent = $("#left_tree");
            parent.empty();
            this.treeMap = [];
            this.nowTree = null;
            this.nowGroup = null;
            for (var i = 0; i < data.userGroup.length; i++) {
                var userGroup = data.userGroup[i];
                var treeNode = new TreeNode(userGroup, null, parent);
                treeNode.isDisplayObject = false;
                treeNode.addEventListener(ucEventType.OPEN_CHILDREN, this.onOpenChildren, this);
                treeNode.addEventListener(ucEventType.CHANGE_RIGHT, this.onChangeRight, this);
                treeNode.addEventListener(ucEventType.CREATE_USER_GROUP, this.onCreateUserGroup, this);
                treeNode.addEventListener(ucEventType.DELETE_USER_GROUP, this.onDeleteUserGroup, this);
                this.treeMap[userGroup.userGroupId] = treeNode;
                if (i === 0) {
                    this.nowGroup = treeNode;
                }
            }
            this.notifyObservers(this.getNotification(notificationExt.SHOW_RIGHT_USER_GROUP_INFO, this.nowGroup.data.userGroupId));
            this.resetTree();
        };
        this.onOpenChildren = function (event) {
            var tree = event.mCurrentTarget;
            if (this.nowTree === null) {
                this.nowTree = tree;
            } else {
                if (tree !== this.nowTree) {
                    this.nowTree.close();
                }
                this.nowTree = tree;
            }
            userGroupProxy.getUserGroupTreeNode(event.mData, {"treeId": tree.data.userGroupId, "nodeId": event.mData});
        };
        this.onChangeRight = function (event) {
            var userGroupId = event.mData;
            this.notifyObservers(this.getNotification(notificationExt.SHOW_RIGHT_USER_GROUP_INFO, userGroupId));
        };
        this.onCreateUserGroup = function (event) {
            var userGroup = event.mData;
            this.notifyObservers(this.getNotification(notificationExt.CHANGE_RIGHT_CONTENT, {
                name: "createGroup1",
                data: userGroup
            }));
        };
        this.onDeleteUserGroup = function (event) {
            var userGroup = event.mData;
            userGroupProxy.deleteUserGroup(userGroup.userGroupId);
        };
        this.resetTree = function () {
            //"x"删除操作结束
            navInit();
            //导航结束
            //导航初始化方法
            function navInit() {
                $(".navList_P").find("dd").each(function () {
                    var self = $(this);
                    var padding = 10;
                    var i = 1;
                    while (self.parents("dd").length) {
                        i++;
                        self = $(self).parents("dd");
                    }
                    var total = padding * i;
                    $(this).children('a').css("padding-left", total + "px")
                });
                $(".navList_P a").each(function () {

                    var num = $(this).next('dl').length;
                    if (num > 0) {
                        $(this).find('i').addClass('listIcon_P')
                    } else {
                        $(this).removeClass("open")
                    }
                    var pad = parseFloat($(this).css("paddingLeft"));
                    var width = 200;
                    var realW = width - pad;
                    $(this).children("span").width(realW);
                    if (num == 0) {
                        $(this).find("i").removeClass('listIcon_P')
                    }
                });

            }
        };
        Mediator.apply(this);
    };
    window.uca.LeftMediator = LeftMediator;
})(window);