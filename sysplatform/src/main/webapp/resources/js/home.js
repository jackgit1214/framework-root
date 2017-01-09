/**
 * 统一ajax处理， 针对session失效时进行处理
 */

// $(document).ajaxStart(function(){
// alert('ajaxstart');
// });
$(document).ajaxComplete(function(event, request, settings) {
	var sessionStatus = request.getResponseHeader('sessionstatus');
	var homepath = request.getResponseHeader('homepage');
	if (sessionStatus == "timeout") {
		window.location = homepage.sysContextPath;
		return false;
	}
});

// 公共模态窗口初始化
$().ready(function() {

});

// 首页脚本
var homepage = {
	sysContextPath : '',
	treesetting:{
		check:{
			enable: true
		},
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId"
			},
			key : {
				url : "url"
			}
		}
	}
};
