/*!
* jQuery UI validate .1
* http://jqueryui.com
*
* Copyright 2013 jQuery Foundation and other contributors
* Released under the MIT license.
* http://jquery.org/license
*
*
*
* Depends:
*/
/**
 * 针对打开 窗口的封装
 */
(function($) {
	var openOptions = {
		maxHeight: true,
		maxWidth: true,
		minHeight: 300,
		minWidth: 300,
		closeOnEscape:false,
		isMin:false,
		isMax:false,
		width:600,
		height:400,
		title:"窗口"
	};
	/**
	 * 单独分离，关闭按钮放最后
	 */
	var closeButtonOption = {
		buttons:{关闭:function(){
			$(this ).dialogE( "close" );
		}}
	}
	//初始化最大最小高度、宽度
	openOptions.maxHeight = $(window).height();
	openOptions.maxWidth = $(window).width();

	function _ajaxload(url,options) {

		var _curdate = new Date();
		var _windowid = "tmp_"+Math.random()+_curdate.getTime();
		var dialoghtml = "<div id='"+_windowid+"' title='"+options.title+"'></div>";
		var $dialog = $(dialoghtml);
		var _args = {};
		if (options.args!=undefined)
			_args = options.args;

		$.ajax({
			type:'post',
			url:url,
			dataType:'html',
			data:_args,
			timeout: 50000,
			cache: false,
			error: function(xhr) {
				alert('Error loading document:' + "\nHttp status: " + xhr.status + " " + xhr.statusText);
			},
			success: function(html) {
				$dialog.html(html);
				var callback = options.callback;
				if (callback!=undefined){
					callback.apply(this,$dialog);
				};
			
				$.loadDivInit_UI($dialog);
				
			}
		});
		return $dialog;
	}

	$.extend({
		openDialog: function(url,options) {
			if (options==undefined)
				options= {};
			var curoptions = {};
			$.extend(true,curoptions,openOptions,options,closeButtonOption);
			var $dialog = _ajaxload(url,curoptions);
			var opt = curoptions;
			
			$.extend(opt, {
				close: function() {
					$dialog.remove();
				},
				resizable:false
			});
			$dialog.dialogE(opt);
			return $dialog;
		},
		openDialogModel: function(url,options) {
			$.extend(options, {
				modal:true
			});
			return $.openDialog(url,options);
		},
		closeDialog:function(){
			$(".ui-dialog").remove();
		}
		
	});

})(jQuery);
/*
 * 实现对dialog的扩展，
 * 目的是增加窗口的最小化与最大化按钮
 */
(function( $, undefined ) {
	$.widget("ui.dialogE",$.ui.dialog, {
		version: "1.0",
		widgetEventPrefix: "validate",
		_createTitlebar: function() {
			this._super();
			//this.uiDialogTitlebar.removeClass("ui-widget-header");
		},
		_create:function(){
			this._super();
			this.uiDialog.addClass("ui-dialog-shadow");
			this.uiDialog.addClass("ui-dialog-background");
		},
		_createButtonPane:function(){
			this._super();
			
		},
		_init:function(){
			this._super();
			var buttonPaneWidth = this.uiDialogButtonPane.width();
			var buttonSetWidth = this.uiButtonSet.width();
			this.uiButtonSet.css("margin-left",(buttonPaneWidth - buttonSetWidth )/2);
		}
	});
})(jQuery);