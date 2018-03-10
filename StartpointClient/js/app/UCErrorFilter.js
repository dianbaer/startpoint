(function (window) {
    if (!window.uca) window.uca = {};
    var ucErrorMsg = window.uca.ucErrorMsg;
    var UCErrorFilter = function () {
        this.check = function (result) {
            if (result.hOpCode === "0") {
                alert(ucErrorMsg.errorMap[result.errorCode]);
                return false;
            } else {
                return true;
            }
        }
    };
    window.uca.UCErrorFilter = UCErrorFilter;
})(window);