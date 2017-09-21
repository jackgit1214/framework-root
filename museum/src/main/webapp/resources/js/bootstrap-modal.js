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
