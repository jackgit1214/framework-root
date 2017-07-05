if (typeof jQuery === 'undefined') {
	throw new Error('system requires jQuery');
};

/**
 * 基于bootstrap的弹窗定义
 */

(function() {
	window.customModals = window.customModals || {};

	var modalParam = {
		data : {},
		title : "窗口",
		width : 700,
		height : 500,
		tipCon : '',
		container : 'body',// 创建容器
		show : true

	};

	function _createDialog(param, ok, cancel) {
		if (param == null)
			param = modalParam;
		else
			param = $.extend({}, modalParam, param);

		var _curdate = new Date();
		var rando = Math.round(Math.random() * 100 + 5);
		var modalId = "tmp_" + rando + _curdate.getTime();

		var modalAttr = {
			id : modalId,
			tabindex : "-1",
			"aria-hidden" : "true",
			role : "dialog"
		};
		var $modalDiv = $("<div>").addClass("modal fade").attr(modalAttr);

		var $mdDialog = $("<div id='" + modalId + "_drag" + "'>").addClass(
				"modal-dialog");
		var $mdCon = $("<div>").addClass("modal-content");

		var $mdFooter = $("<div>").addClass("modal-footer m-t-0").html(
				"<div class='text-center'></div>");
		var $mdBody = $("<div>").addClass("modal-body");
		var $mdHeader = $("<div>")
				.addClass("modal-header")
				.html(
						"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>	<h4 class='modal-title'>"
								+ param.title + "</h4>");

		$mdCon.append($mdHeader).append($mdBody).append($mdFooter);
		$mdDialog.append($mdCon);
		$modalDiv.append($mdDialog);
		var container = param.container || 'body'; // 初始化容器
		$(container).append($modalDiv);
		// 窗口关闭后加载
		var $modalDialog = $("#" + modalId);
		// **弹出窗口类型
		if (ok || cancel || param.has_footer) {
			// $mdBody.text(param.text||'')
			var $con = param.tipCon;
			$con.find("span").last().html(param.text || '');
			$mdBody.append($con);
			$mdDialog.addClass("modal-sm");
			if (cancel) {
				var btn_close = $('<BUTTON>');
				btn_close.addClass('btn btn-default btn-sm').attr("type",
						"button").attr('data-dismiss', 'modal').text(
						param.cancel_label).bind('click', function() {
					if (param.callback && param.callback.cancel_call) {
						param.callback.cancel_call.call(this);
					}
				});

				$mdFooter.find(".text-center").append(btn_close);
			}

			if (ok) {
				var btn_ok = $('<button>');
				btn_ok.addClass("btn btn-primary btn-sm")
						.attr("type", "button").text(param.ok_label).bind(
								"click",
								function() {
									$modalDialog.modal('hide');
									if (param.callback
											&& param.callback.ok_call) {
										param.callback.ok_call.call(this);
									}
								});
				$mdFooter.find(".text-center").append(btn_ok);
			}
		} else {
			$mdDialog.css({
				"width" : param.width,
				"height" : param.height
			});
		}

		$modalDialog.on("hidden.bs.modal", function() {
			$modalDialog.parent().remove();
		});

		// /窗口打开后加载
		$modalDialog.on("shown.bs.modal", function() {
			$("#" + modalId + "_drag" + "").draggable({
				handle : ".modal-header" // 只能点击头部拖动
			});
			$("#" + modalId).css("overflow", "hidden");
			// 初始化组件
		});

		$modalDialog.modal({
			show : true,
			keyboard : false,
			backdrop : 'static'
		});

		return $modalDialog;
	}
	;
	/**
	 * 目前阶段涉及参数说明 { title: 'modal标题', text: '提示内容', ok_label: '确定按钮的内容',
	 * cancel_label: '取消按钮的内容', callback: '确定按钮的回调函数', cancel_call: '取消按钮的回调函数',
	 * large: 'modal大小模式' }
	 */
	var _CONFIG = {
		ok_label : '确定',
		cancel_label : '关闭',
		large : false
	};

	function modal_params(text, callback) {
		return $.extend({}, _CONFIG, (typeof text != 'object') ? {
			text : text,
			callback : callback
		} : text);
	}
	;

	/**
	 * 普通调试弹窗的基本窗口
	 */
	var _TITLE = {
		info : '提示',
		correct : '成功',
		warn : '警告',
		error : '错误',
		confirm : '确认？'
	};
	var _tipClass = {
		info : $('<div><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <span  class="text">test</span></div>'),
		warn : $('<div><span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> <span  class="text">test</span></div>'),
		error : $('<div><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> <span  class="text">test</span></div>'),
		correct : $('<div><span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span> <span  class="text"></span></div>'),
		confirm : $('<div><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> <span class="text"></span></div>')
	};

	function _alert(args, type) {
		var cfg = modal_params.apply(this, args);
		cfg.title = cfg.title || _TITLE[type];
		modalParam.tipCon = _tipClass[type];
		return _createDialog(cfg, false, true);
	}
	;

	/**
	 * 用法: 1.customModals.info('...'); 2.customModals.info({ ... });
	 * 
	 * correct、warn、error可不要。为统一规范尽量选择相应函数
	 * 
	 * @returns {*}
	 */
	customModals.info = function() {
		return _alert.call(this, arguments, 'info');
	};

	customModals.correct = function() {
		return _alert.call(this, arguments, 'correct');
	};

	customModals.warn = function() {
		return _alert.call(this, arguments, 'warn');
	};

	customModals.error = function() {
		return _alert.call(this, arguments, 'error');
	};

	customModals.confirm = function() {
		var cfg = modal_params.apply(this, arguments);
		cfg.title = cfg.title || '确认提示';
		cfg.cancel_label = cfg.cancel_label == _CONFIG.cancel_label ? '取消'
				: cfg.cancel_label;
		cfg.tipCon = _tipClass['confirm'];
		return _createDialog(cfg, true, true);

	};

	customModals.popupDialog = function(param, url) {
		if (param == null)
			param = modalParam;
		else
			param = $.extend({}, modalParam, param);

		var _curdate = new Date();
		var rando = Math.round(Math.random() * 100 + 5);
		var modalId = "tmp_" + rando + _curdate.getTime();

		var modalAttr = {
			id : modalId,
			tabindex : "-1",
			"aria-hidden" : "true",
			role : "dialog"
		};
		var $modalDiv = $("<div>").addClass("modal fade").attr(modalAttr);

		var $mdDialog = $("<div id='" + modalId + "_drag" + "'>").addClass(
				"modal-dialog").css({
			"width" : param.width,
			"height" : param.height
		});
		var $mdCon = $("<div>").addClass("modal-content");

		var $mdFooter = $("<div>").addClass("modal-footer m-t-0");
		var $mdBody = $("<div>").addClass("modal-body");
		var $mdHeader = $("<div>")
				.addClass("modal-header")
				.html(
						"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>	<h4 class='modal-title'>"
								+ param.title + "</h4>");
		if (url) {
			var $this = this;
			$.ajax({
				type : "GET",
				url : url,
				data : param.data,
				success : function(data) {
					var $data = $(data);
					var $tmpfooter = $data.find(".modal-footer");
					if ($tmpfooter[0]) {
						$mdFooter.html($data.find(".modal-footer").html());
						$tmpfooter.remove();
					}
					var $tmptitle = $data.find("h4").filter(".modal-title");
					if ($tmptitle[0]) {
						param.title = $tmptitle.text();
						$mdHeader.find("h4").text(param.title);
						$tmptitle.remove();
					}
					$mdBody.append($data);
					if (param.pageCallback) {
						param.pageCallback(this);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$this.error(XMLHttpRequest.readyState + "</br>"
							+ XMLHttpRequest.status);
					return;
				}

			});

		} else {
			this.error("请指定url参数！");
			return;
		}
		$mdCon.append($mdHeader).append($mdBody).append($mdFooter);
		$mdDialog.append($mdCon);
		$modalDiv.append($mdDialog);
		var container = param.container || 'body'; // 初始化容器
		$(container).append($modalDiv);
		// 窗口关闭后加载
		var $modalDialog = $("#" + modalId);
		$modalDialog.on("hidden.bs.modal", function() {
			if (param.show) // 默认为是关闭窗口时删除当前对象
				$modalDialog.remove();
		});

		// /窗口打开后加载
		$modalDialog.on("shown.bs.modal", function() {
			$("#" + modalId + "_drag" + "").draggable({
				handle : ".modal-header" // 只能点击头部拖动
			});
			$("#" + modalId).css("overflow", "hidden");
			// 初始化组件
			if (param.callback) {
				param.callback.call(this);
			}
			;
		});

		$modalDialog.modal({
			show : param.show,
			keyboard : false,
			backdrop : 'static'
		});

		return $modalDialog;
	};
})();

/**
 * 统一ajax处理， 针对session失效时进行处理
 */
// $(document).ajaxStart(function(){
// alert('ajaxstart');
// });
$(document).ajaxComplete(function(event, request, settings) {
	var sessionStatus = request.getResponseHeader('sessionstatus');
	var homepath = request.getResponseHeader('homepage');
	if (sessionStatus == 'timeout') {
		window.location = homepage.sysContextPath;
		return false;
	}
});

;
(function($) {
	$.fn.datetimepicker.dates['zh-CN'] = {
		days : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ],
		daysShort : [ "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日" ],
		daysMin : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
		months : [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12" ],
		monthsShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
				"十月", "十一月", "十二月" ],
		today : "今天",
		suffix : [],
		meridiem : [ "上午", "下午" ]
	};
}(jQuery));
// 公共模态窗口初始化
$().ready(function() {

});
// /系统控制脚本
$.SystemApp = {
	contextPath : '',
	modalParam : {
		data : {},
		title : "窗口",
		width : 700,
		height : 500
	},
	treesetting : {
		check : {
			enable : true
		},
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : 'id',
				pIdKey : 'pId'
			},
			key : {
				url : 'url'
			}
		}
	}
};
$.SystemApp.options = {
	controlSidebarOptions : {
		// 控制sidebar选择器
		toggleBtnSelector : '[data-toggle=\'control-sidebar\']',
		// sidebar选择器
		selector : '.control-sidebar',
		slide : true
	},
	pageToolbarOptions : {
		selector : ".pagetoolbar",
		showMaxPageNum : 5
	}
};
$(document).ready(function() { (function() {

	    $('body').on('click', '.side-menu > li > a, .side-menu > li > ul > li > a',
	    function(e) {
	        e.preventDefault();
	        var $this = $(this);
	        var href = $.SystemApp.contextPath + $this.attr('href');
	        if (href == '') return;
	        
	        $('#main-content').load(href, {},
	        function() {
	        	var dataParm = {name:$this.html(),link:$this.attr('href')};
	        	
	        	$.post($.SystemApp.contextPath+"/putHistory", dataParm, function(data){
	        		var $bodyNav = $("#bodyNav");
	        		$bodyNav.html("");
	        		$.each(data.history,function(i,obj){
	        			//<li><a href='${sessionManager.history["${key}"][1]}' >${sessionManager.history["${key}"][0]}</a></li>
	        			var $lia = $("<a>");
	        			$lia.attr("href","javascript:void(0);").html(obj[0]);
	        			//$lia.attr("href",obj[1]).html(obj[0]);
	        			var $li = $("<li>");
	        			$li.append($lia);
	        			$bodyNav.append($li);
	        		});
	        	});

	        	
	            if ($('[class*=\'form-validation\']')[0]) {
	                $('[class*=\'form-validation\']').validationEngine();
	                $('body').on('click', '.validation-clear',
	                function(e) {
	                    e.preventDefault();
	                    $(this).closest('form').validationEngine('hide');
	                });
	            };
	        });
	    });

	})();
	$.SystemApp.controlSidebar.activate();
	$.SystemApp.initContentArea();

});

$.SystemApp.initContentArea = function() {

	var $docH = $(document).height();
	var $conmainH = $docH - 140;
	var $tableConH = $conmainH - 80;
	$("body").on("DOMNodeInserted", "#main-content", function(e) {
		$(".con-main").height($conmainH);
	});
	$("#main-content").on("DOMNodeInserted", ".table-con", function(e) {
		var $tar = $(e.target);
		$(".table-con").height($tableConH);
		$(".table-data").height($tableConH - 50);
	});

};
$.SystemApp.initComponents = function() {

	// check box
	if ($('input:checkbox, input:radio')[0]) {

		// Checkbox + Radio skin
		$(
				'input:checkbox:not([data-toggle="buttons"] input, .make-switch input), input:radio:not([data-toggle="buttons"] input)')
				.iCheck({
					checkboxClass : 'icheckbox_minimal',
					radioClass : 'iradio_minimal',
					increaseArea : '20%' // optional
				});

		$('.make-switch').bootstrapSwitch();

		// Checkbox listing
		var parentCheck = $('.list-parent-check');
		var listCheck = $('.list-check');
		parentCheck.on('ifChecked', function() {
			$(this).closest('.list-container').find('.list-check').iCheck(
					'check');
		});

		parentCheck.on('ifClicked', function() {
			$(this).closest('.list-container').find('.list-check').iCheck(
					'uncheck');
		});

		listCheck.on('ifChecked', function() {
			var parent = $(this).closest('.list-container').find(
					'.list-parent-check');
			var thisCheck = $(this).closest('.list-container').find(
					'.list-check');
			var thisChecked = $(this).closest('.list-container').find(
					'.list-check:checked');

			if (thisCheck.length == thisChecked.length) {
				parent.iCheck('check');
			}
		});

		listCheck.on('ifUnchecked', function() {
			var parent = $(this).closest('.list-container').find(
					'.list-parent-check');
			parent.iCheck('uncheck');
		});

		listCheck.on('ifChanged', function() {
			var thisChecked = $(this).closest('.list-container').find(
					'.list-check:checked');
			var showon = $(this).closest('.list-container').find('.show-on');
			if (thisChecked.length > 0) {
				showon.show();
			} else {
				showon.hide();
			}
		});

	}
	;

	/** form components */
	if ($("[class*='form-validation']")[0]) {
		$("[class*='form-validation']").validationEngine();

		// Clear Prompt
		$('body').on('click', '.validation-clear', function(e) {
			e.preventDefault();
			$(this).closest('form').validationEngine('hide');
		});
	}

	/*
	 * -------------------------------------------------------- Components
	 * -----------------------------------------------------------
	 */
	/* Textarea */
	if ($('.auto-size')[0]) {
		$('.auto-size').autosize();
	}

	// Select
	if ($('.selectpicker')[0]) {
		$('.selectpicker').selectpicker({
			style : 'form-control'
		});

		$('.selectpicker').on('loaded.bs.select', function(e) {
			var _this = $(this);

			_this.selectpicker('val', _this.data("value"));
		});
	}

	// Sortable
	if ($('.sortable')[0]) {
		$('.sortable').sortable();
	}

	// Tag Select
	if ($('.tag-select')[0]) {
		$('.tag-select').chosen();
	}

	/* Tab */
	if ($('.tab')[0]) {
		$('.tab a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
		});
	}

	/* Collapse */
	if ($('.collapse')[0]) {
		$('.collapse').collapse();
	}

	/* Accordion */
	$('.panel-collapse').on('shown.bs.collapse', function() {
		$(this).prev().find('.panel-title a').removeClass('active');
	});

	$('.panel-collapse').on('hidden.bs.collapse', function() {
		$(this).prev().find('.panel-title a').addClass('active');
	});

	// Popover
	if ($('.pover')[0]) {
		$('.pover').popover();
	}
	;
	if ($(".form_datetime")[0]) {
		$(".form_datetime").datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			format : "yyyy-MM-dd",
			autoclose : 1,
			startDate : new Date(),
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-right"
		});
	}
	;
	if ($('.pirobox_gall')[0]) {
		// Fix IE
		jQuery.browser = {};
		(function() {
			jQuery.browser.msie = false;
			jQuery.browser.version = 0;
			if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
				jQuery.browser.msie = true;
				jQuery.browser.version = RegExp.$1;
			}
		})();

		// Lightbox
		$().piroBox_ext({
			piro_speed : 700,
			bg_alpha : 0.5,
			piro_scroll : true
		// pirobox always positioned at the center of the page
		});
	};
	
	$('a.viewDetail').each(function(rev) {
		 var urllink =$.SystemApp.contextPath +  $(this).data("url");
        var title = $(this).data("title");
        $(this).off('click');
        $(this).on("click",function (event) {
       	 // event.preventDefault();  
       	 var _params = {};
       	 $.SystemApp.openDialog(urllink, {
    			data : _params,
    			title : title
    		});
          
        });
	});
	
};

$.SystemApp.openDialog = function(url, param) {

	if (!param.callback)
		param = $.extend({}, param, {
			callback : function() {
				$.SystemApp.initComponents();
			}
		});

	return customModals.popupDialog(param, url);
};

$.SystemApp.initMenu = function(menuUrl) {

	var _menulink = $.SystemApp.contextPath + menuUrl;

	jQuery.ajax({
		type : 'GET',
		url : _menulink,
		dataType : 'json',
		timeout : 50000,
		cache : false,
		error : function(xhr) {
			alert('Error json data' + "\nHttp status: " + xhr.status + " "
					+ xhr.statusText);
		},
		success : function(data) {
			var $ulcontent = loadJson(data);
			$("#sidebar").append($ulcontent);

			$('.side-menu > li > a').one(
					'mouseover touchstart',
					function(e) {
						var $this = $(this);
						var superid = $this.attr("id");
						var dataParam = {
							parentId : superid
						};
						$.getJSON(
								$.SystemApp.contextPath + "/func/getFunction",
								dataParam, function(data) {
									if (data.length > 0) {

										$this.parent().addClass("dropdown");
										var $$ulcontent = loadJsonSub(data);
										$this.after($$ulcontent);
									}

								});
					});
		}
	});

	function loadJsonSub(data) {
		var $ul = $("<ul>");
		$ul.addClass("list-unstyled menu-item");
		$.each(data, function(i, module) {

			var $this = $(this);
			var $li = $("<li>");

			var $a = $("<a id='" + module.funcid + "' data-param="
					+ module.funcid + ">");
			if (module.targetDiv != undefined) {
				var target = module.targetDiv;
				var link = module.urllink;
				var urlParam = "{id:" + module.funcid + "}";
				$a.attr("href", link);
			}
			$a.text(module.funcname);
			$li.append($a);

			$ul.append($li);
		});
		return $ul;
	}
	;
	function loadJson(data, parentid) {

		if (parentid == undefined)
			parentid = "";
		var $ul = $("<ul>");
		if (parentid == "") {
			$ul.addClass("list-unstyled side-menu");
		}
		$.each(data,
				function(i, module) {
					var $this = $(this);
					var $li = $("<li>");
					if (i == 0)
						$li.addClass("active");

					var $a = $("<a id='" + module.funcid + "'  data-param="
							+ module.funcid + ">");
					var $i = $("<i>").addClass("fa fa-2x").addClass(
							module.funicon);
					var $span = $("<span>").addClass("menu-item").text(
							module.funcname);
					$a.append($i).append($span);

					if (module.targetDiv != undefined) {
						var target = module.targetDiv;
						var link = module.urllink;

						var urlParam = "{id:" + module.funcid + "}";
						$a.attr("href", link);

					}
					$li.append($a);
					$ul.append($li);
				});
		return $ul;
	}
	;

};

$.SystemApp.checkbox = function(element) {

	var listCheck = $('.list-check', $(element));
	var selectArray = [];
	listCheck.each(function(i, obj) {
		var $obj = $(obj);
		if ($obj.prop("checked") == true) {
			selectArray.push($obj.val());
		}
		;
	});

	return selectArray;

};

$.SystemApp.loadSubMenu = function(source) {
	// <ul class="list-unstyled menu-item">
	// <li><a href="form/form-elements.html">Basic Form Elements</a></li>
	// <li><a href="form/form-components.html">Form Components</a></li>
	// <li><a href="form/form-examples.html">Form Examples</a></li>
	// <li><a href="form/form-validation.html">Form Validation</a></li>
	// </ul>
};

$.SystemApp.divLoad = function(sourcediv, url, data, callback) {

	if (url == undefined || url == "" || url == "#")
		return;
	if (data == undefined)
		data = {};

	$(sourcediv).load($.SystemApp.contextPath + url, data, function(data) {
		$.SystemApp.initComponents();

		$.SystemApp.pagetoolbar.toolbarInit();
		if (callback != undefined && $.isFunction(callback))
			callback(data);
	});
};

/*
 * ControlSidebar ============== Adds functionality to the right sidebar
 * 
 * @type Object @usage $.AdminLTE.controlSidebar.activate(options)
 */
$.SystemApp.controlSidebar = {
	// instantiate the object
	activate : function() {
		// Get the object
		var _this = this;
		// Update options
		var o = $.SystemApp.options.controlSidebarOptions;
		// Get the sidebar
		var sidebar = $(o.selector);
		// The toggle button
		var btn = $(o.toggleBtnSelector);
		// Listen to the click event
		btn.on('click', function(e) {
			e.preventDefault();
			// If the sidebar is not open
			if (!sidebar.hasClass('control-sidebar-open')
					&& !$('body').hasClass('control-sidebar-open')) {
				// Open the sidebar
				_this.open(sidebar, o.slide);
			} else {
				_this.close(sidebar, o.slide);
			}
		});
		// If the body has a boxed layout, fix the sidebar bg position
		var bg = $('.control-sidebar-bg');
		_this._fix(bg);
		// If the body has a fixed layout, make the control sidebar fixed
		if ($('body').hasClass('fixed')) {
			_this._fixForFixed(sidebar);
		} else {
			// If the content height is less than the sidebar's height, force
			// max height
			if ($('.content-wrapper, .right-side').height() < sidebar.height()) {
				_this._fixForContent(sidebar);
			}
		}
	},
	// Open the control sidebar
	open : function(sidebar, slide) {
		// Slide over content
		if (slide) {
			sidebar.addClass('control-sidebar-open');
		} else {
			// Push the content by adding the open class to the body instead
			// of the sidebar itself
			$('body').addClass('control-sidebar-open');
		}
	},
	// Close the control sidebar
	close : function(sidebar, slide) {
		if (slide) {
			sidebar.removeClass('control-sidebar-open');
		} else {
			$('body').removeClass('control-sidebar-open');
		}
	},
	_fix : function(sidebar) {
		var _this = this;
		if ($('body').hasClass('layout-boxed')) {
			sidebar.css('position', 'absolute');
			sidebar.height($('.wrapper').height());
			if (_this.hasBindedResize) {
				return;
			}
			$(window).resize(function() {
				_this._fix(sidebar);
			});
			_this.hasBindedResize = true;
		} else {
			sidebar.css({
				'position' : 'fixed',
				'height' : 'auto'
			});
		}
	},
	_fixForFixed : function(sidebar) {
		sidebar.css({
			'position' : 'fixed',
			'max-height' : '100%',
			'overflow' : 'auto',
			'padding-bottom' : '50px'
		});
	},
	_fixForContent : function(sidebar) {
		$('.content-wrapper, .right-side').css('min-height', sidebar.height());
	}
};

/*
 * 
 */
$.SystemApp.pagetoolbar = {
	totalPageNum : 0,
	curPage : 0,
	target : null,
	linkhref : null,
	pagination : null,
	recordNum : 0,
	retrieveParam : null,
	endPage : 0,
	// 翻页工具条显示的结束页
	startPage : 0,
	// 翻页工具条显示的开始页
	toolbarInit : function() {
		var _this = this;
		var o = $.SystemApp.options.pageToolbarOptions;
		var $pagebar = $(o.selector);
		_this._getDataParam($pagebar);
		var showPageNum = o.showMaxPageNum;
		_this.endPage = showPageNum + _this.curPage;
		if (_this.totalPageNum < showPageNum) {
			showPageNum = _this.totalPageNum;
		}
		;
		// 创建翻页滚动
		var _prePage = $("<li>").data("pageAct", "prev").append(
				$("<a class='btn btn-default' id='page_prev' href='#'>").html(
						"<i class='fa fa-angle-left'></i>"));
		_this.pagination.append(_prePage);

		var _endpage = showPageNum + _this.curPage;

		_endpage = Math.floor(_endpage / showPageNum) * showPageNum;

		if (_endpage > _this.totalPageNum)
			_endpage = _this.totalPageNum;

		var _startpage = _this.curPage;
		if (_startpage == showPageNum)
			_startpage = _startpage - 1; // 修正当总页数与工具条显示页数正好一样的问题
		_startpage = Math.floor(_startpage / showPageNum) * showPageNum + 1; // 取倍数处理
		for ( var i = _startpage; i <= _endpage; i++) {
			var _pagenum = $("<li>").append(
					$("<a class='btn btn-default' href='#'>").html(i));
			_pagenum.data("pageAct", i);
			_this.pagination.append(_pagenum);
		}

		var _lastPage = $("<li>").data('pageAct', "next").append(
				$("<a class='btn btn-default' id='page_next' href='#'>").html(
						"<i class='fa fa-angle-right'></i>"));
		_this.pagination.append(_lastPage);
		_this._bindClickEvent($pagebar);

	},
	_getDataParam : function(pagebar) {
		var _this = this;
		_this.totalPageNum = pagebar.data("totalpage");
		_this.curPage = pagebar.data("curpage");
		_this.target = pagebar.data("linktarget");
		_this.linkhref = pagebar.data("pagelink");
		_this.recordNum = pagebar.data("recordcount");
		_this.retrieveParam = pagebar.data("param");
		_this.pagination = $(".pagination", pagebar);
	},
	_bindClickEvent : function(pagebar) {
		var _this = this;

		var $pageination = $(".pagination", pagebar);
		$pageination.find("li").each(function(i, obj) {
			var _obj = $(obj);
			var _goPage = _obj.data("pageAct");
			if (_goPage === "next") {
				_obj.find("a").bind("click", function() {
					_this.refreshData(_this.endPage + 1);
				});
			} else if (_goPage === "prev") {
				_obj.find("a").bind("click", function() {
					var page = _this.startPage - 1;
					if (page < 0)
						page = 1;
					_this.refreshData(page);
				});
			} else {

				if (_goPage == _this.curPage)
					return;
				_obj.find("a").bind("click", function() {
					_this.refreshData(_goPage);
				});
			}
			;
		});
		_this._pageNumChangeMonitor(_this.curPage);
	},
	_pageNumChangeMonitor : function(turnPage) {

		var _this = this;
		var o = $.SystemApp.options.pageToolbarOptions;
		if (_this.totalPageNum < o.showMaxPageNum) {
			$("#page_next").addClass("disabled");
			$("#page_prev").addClass("disabled");
			return;
		}

		// 最后一页时，控制按钮
		if (_this.totalPageNum == turnPage) {
			$("#page_next").addClass("disabled");

		} else {
			$("#page_next").removeClass("disabled");
		}
		// 第一页时，控制按钮
		if (1 == turnPage) {
			$("#page_prev").addClass("disabled");
		} else {
			$("#page_prev").removeClass("disabled");
		}
	},
	refreshData : function(turnPage) {

		var _this = this;
		if (turnPage == undefined)
			turnPage = _this.curPage;

		if (_this.retrieveParam == undefined || _this.retrieveParam == ""
				|| _this.retrieveParam == null)
			_this.retrieveParam = {};

		var isJson = $.isPlainObject(_this.retrieveParam);

		var tmpdata = {};

		if (isJson == false) {
			tmpdata = $.parseJSON(_this.retrieveParam);
		} else
			tmpdata = _this.retrieveParam;

		tmpdata.pageNo = turnPage;
		$.SystemApp.divLoad(_this.target, _this.linkhref, tmpdata, function() {
		});
	}
};

/** **功能部分js**** */
$.SystemApp.commonOper = {
	dialogObj : null,
	showEdit : function(isEdit, urllink, formobject, title, width, height) {
		var _params = {};
		if (isEdit) {
			var selectValue = $.SystemApp.checkbox("#" + formobject);
			if (selectValue == null || selectValue == ''
					|| selectValue.length <= 0) {
				alert("请选择要编辑的数据");
				return;
			}
			_params = {
				'id' : selectValue[0]
			};

		}
		if (width != undefined)
			_params.width = width;
		if (width != undefined)
			_params.height = height;

		this.dialogObj = $.SystemApp.openDialog($.SystemApp.contextPath
				+ urllink, {
			data : _params,
			height : _params.height,
			width : _params.width,
			title : title
		});

		// return dialogObj;
	},
	del : function(urllink, formobject, title) {
		var selectValue = $.SystemApp.checkbox("#" + formobject);

		if (selectValue == null || selectValue == '' || selectValue.length <= 0) {
			alert("请选择要删除的数据");
			return;
		}
		if (!confirm("您确定删除这些数据吗？"))
			return;
		var data = {
			ids : selectValue
		};
		$.post($.SystemApp.contextPath + urllink, data, function(data) {
			if (data.successRows > 0) {
				$.SystemApp.pagetoolbar.refreshData();
				alert("数据删除成功！");
			}
		});
	},
	save : function(urllink, formobject) {
		var $this = this;
		var $formObject = $("#" + formobject);
		var validateResult = $formObject.validationEngine('validate');
		if (!validateResult)
			return false;

		var data = $formObject.serializeArray();
		$.post($.SystemApp.contextPath + urllink, data, function(data) {
			if (data.successRows > 0) {
				alert("数据保存成功！");
				$this.dialogObj.modal("hide");
				$.SystemApp.pagetoolbar.refreshData();
			} else {
				alert("数据保存失败！");
			}

		});
	},
	loadCarouselImage : function(dataurl, busitype, imageurl, indiObject,
			imageObject) {
		$.getJSON(dataurl + '/' + busitype, {}, function(data) {
			var attachments = data.attachments;

			$(imageObject).empty();
			$(indiObject).empty();
			$.each(attachments, function(i, attachment) {

				if (attachment.attatype != "1") // /这里只显示图片
					return true;
				var $divitem = $("<div>");
				if (i == 0) {
					$divitem.addClass("item active");
				} else {
					$divitem.addClass("item");
				}
				var $img = $("<img>");
				$img.attr("src",
						imageurl + "/" + attachment.attaid + "?permission=2")
						.attr("alt", "Slide-" + i).addClass(
								"img-responsive center-block"); // 图片距中及响应式图片
				$divitem.append($img);
				// var $divcaption = $("<div>").addClass("carousel-caption
				// hidden-xs")
				// .append("<h3>"+attachment.filename+"</h3>").append("<p>"+attachment.filesize+"</p>");
				// $divitem.append($divcaption);
				var $li = $("<li>").data("target", "#carousel-collimage").data(
						"slide-to", i).attr("data-target",
						"#carousel-collimage").attr("data-slide-to", i);
				$(imageObject).append($divitem);
				$(indiObject).append($li);
			});
		});
	}
};

/* 征集线索部分 */
$.SystemApp.collClueJscript = {

	init_UI : function() {
		$.SystemApp.divLoad("#systemData", "/collclue/list", '', function() {

		});
	},
	showEdit : function(isEdit) {
		// var _this = this;
		$.SystemApp.commonOper.showEdit(isEdit, "/collclue/showEdit",
				"tableHover_collClue", "文物线索");

	},
	del : function() {
		$.SystemApp.commonOper.del("/collclue/delete", "tableHover_collClue");

	},
	save : function(formObject) {
		$.SystemApp.commonOper.save("/collclue/update", formObject);

	},
	find : function(formobject) {
		var data = $("#" + formobject).serialize();
		$.SystemApp.divLoad("#systemData", "/collclue/list", data, function() {

		});
	}
};

$.SystemApp.collInfoJscript = {
	dialogObj : null,
	init_UI : function() {
		$.SystemApp.divLoad("#systemData", "/collinfo/list", '', function() {

		});
	},
	showEdit : function(isEdit) {
		var _this = this;
		_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,
				"/collinfo/showEdit", "tableHover_collInfo", "征集信息", 800);

	},
	del : function() {
		$.SystemApp.commonOper.del("/collinfo/delete", "tableHover_collInfo");

	},
	save : function(formObject) {
		$.SystemApp.commonOper.save("/collinfo/update", formObject);

	},
	find : function(formobject) {
		var data = $("#" + formobject).serialize();

		$.SystemApp.divLoad("#systemData", "/collinfo/list", data, function() {

		});
	}
};
