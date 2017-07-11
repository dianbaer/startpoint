(function($) {
	$.FileUtil = {
		ajaxFileUpload : function(sendParam) {
			var upfileObj = this.getAjax();

			var packet = encodeURI(JSON.stringify(sendParam.data));
			var url = sendParam.url + "?" + $T.httpConfig.HOPCODE + "=" + sendParam.data[$T.httpConfig.HOPCODE] + "&token=" + sendParam.token + "&sendType=" + $T.httpConfig.SEND_TYPE_FILE_SAVE_SESSION + "&receiveType=" + $T.httpConfig.RECEIVE_TYPE_JSON + "&packet=" + packet;
			if (upfileObj == null) {
				alert("您的浏览器不支持AJAX！");
				return;
			}

			$.ajaxFileUpload({
				url : url,// 用于文件上传的服务器端请求地址
				secureuri : false, // 一般设置为false
				fileElementId : sendParam.fileArray, // 文件上传空间的id属性
				data : sendParam.data,
				dataType : 'json',// 返回值类型 一般设置为json
				success : function(data, status) // 服务器成功响应处理函数
				{
					var isSuccess = $T.CheckError.check(data);
					if (isSuccess) {
						if (options.successHandle) {
							// data = eval('(' + data + ')');
							options.successHandle(data, upfileObj);
						}
					} else {
						if (options.failHandle) {
							options.failHandle(data, upfileObj);
						}
					}
				}
			});
		},
		getAjax : function() {
			var xmlHttp;
			try {
				xmlHttp = new XMLHttpRequest();
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					try {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					} catch (e) {
						return null;
					}
				}
			}
			return xmlHttp;
		}
	};
})(jQuery);