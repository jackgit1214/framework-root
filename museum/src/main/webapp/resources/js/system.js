if (typeof jQuery === 'undefined') {
	throw new Error('system requires jQuery');
};


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

function escapeJquery(srcString)  
{  
    // 转义之后的结果  
    var escapseResult = srcString;  
    // javascript正则表达式中的特殊字符  
    var jsSpecialChars = ["\\", "^", "$", "*", "?", ".", "+", "(", ")", "[",  
            "]", "|", "{", "}"];  
    // jquery中的特殊字符,不是正则表达式中的特殊字符  
    var jquerySpecialChars = ["~", "`", "@", "#", "%", "&", "=", "'", "\"",  
            ":", ";", "<", ">", ",", "/"];  
    for (var i = 0; i < jsSpecialChars.length; i++) {  
        escapseResult = escapseResult.replace(new RegExp("\\"  
                                + jsSpecialChars[i], "g"), "\\"  
                        + jsSpecialChars[i]);  
    }  
    for (var i = 0; i < jquerySpecialChars.length; i++) {  
        escapseResult = escapseResult.replace(new RegExp(jquerySpecialChars[i],  
                        "g"), "\\" + jquerySpecialChars[i]);  
    }  
    return escapseResult;  
} ; 
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
	    
	    $('body').on('click', '.template-skins > a', function(e){
	    	e.preventDefault();
	    	var skin = $(this).data('skin');
	    	
	    			$('body').attr('id', skin);
	    			$('#changeSkin').modal('hide');
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
		$('input:checkbox:not([data-toggle="buttons"] input, .make-switch input), input:radio:not([data-toggle="buttons"] input)')
				.iCheck({
					checkboxClass : 'icheckbox_minimal',
					radioClass : 'iradio_minimal',
					increaseArea : '20%' // optional
				});

		$('.make-switch:not(.has-switch)').bootstrapSwitch();

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

	};

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
		
		//**调试信息
//		var kkk = $(".selectpicker");
//		kkk.each(function(obj,i){
//			var _this = $(this);
//			console.log(_this.data('codetype'));
//		});
		
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
	};
	
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
	};

	if($('.overflow')[0]) {
		var overflowRegular, overflowInvisible = false;
		overflowRegular = $('.overflow').niceScroll();
	};
		   
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
	};

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
		};
		// 创建翻页滚动
		var _prePage = $("<li>").data("pageAct", "prev").append(
				$("<a class='btn btn-default' id='page_prev' href='#'>").html(
						"<i class='fa fa-angle-left'></i>"));
		_this.pagination.append(_prePage);



		var _startpage = _this.curPage;
		if (_startpage == showPageNum)
			_startpage = _startpage - 1; // 修正当总页数与工具条显示页数正好一样的问题
		_startpage = Math.floor(_startpage / showPageNum) * showPageNum + 1; // 取倍数处理

		var _endpage = showPageNum + _startpage;

		_endpage = Math.floor(_endpage / showPageNum) * showPageNum;

		if (_endpage > _this.totalPageNum)
			_endpage = _this.totalPageNum;
		
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
	del : function(urllink, formobject, paramData) {
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
		if (paramData!=undefined)
			$.extend(data,paramData);
		$.post($.SystemApp.contextPath + urllink, data, function(data) {
			if (data.successRows > 0) {
				$.SystemApp.pagetoolbar.refreshData();
				alert("数据删除成功！");
			}
		});
	},
	save : function(urllink, formobject,paramData,successCall) {
		var $this = this;
		var $formObject = $("#" + formobject);
		var validateResult = $formObject.validationEngine('validate');
		if (!validateResult)
			return false;

		var data = $formObject.serializeArray();
		if (paramData!=undefined)
			$.merge(data,paramData);
		$.post($.SystemApp.contextPath + urllink, data, function(data) {
			if (data.successRows > 0) {
				alert("数据保存成功！");
				$this.dialogObj.modal("hide");
				$.SystemApp.pagetoolbar.refreshData();

				if (successCall!=undefined && $.isFunction(successCall))
					successCall.apply(this,data);
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

$.SystemApp.deptJscript = {
		departmentTree:null,
		showPerson:false,
		checkFlag:false,
		reloadFlag:false,
		setting:function(){
			var tmpsetting = $.SystemApp.treesetting;
			 $.extend(tmpsetting,{
				 check:{
						enable: true
					},
			 	async: {
					enable: true,
					url:$.SystemApp.deptJscript.getAsyncUrl,
					autoParam:["id","pId"]
				},
				callback: {
					onCheck: function(event, treeId, treeNode){
						
						$.SystemApp.deptJscript.checkChanged();
					},
					beforeCheck:function(treeId, treeNode){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						if (!treeNode.children) {
							$.SystemApp.deptJscript.reloadFlag = true;
							$.SystemApp.deptJscript.checkFlag = true;
							zTree.reAsyncChildNodes(treeNode, "refresh");
						}
					},
					beforeExpand:function(treeId, treeNode){
						$.SystemApp.deptJscript.reloadFlag=false;
					},
					onNodeCreated:function(event, treeId, treeNode){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						if ($.SystemApp.deptJscript.reloadFlag) {
							if ($.SystemApp.deptJscript.checkFlag) {
								zTree.checkNode(treeNode, true, true);
							}
							if (!treeNode.children) {
								zTree.reAsyncChildNodes(treeNode, "refresh");
							}
						}
					}
				}
			 });
			return tmpsetting;
		},
		checkChanged:function(){
			var nodes = this.departmentTree.getCheckedNodes(true);
			var userids = "";
			var usernames  ="";
			for (var i=0, l=nodes.length; i < l; i++) {
				if (nodes[i].username==undefined)
					continue;
				userids = userids+nodes[i].id+",";
				usernames = usernames+nodes[i].name+",";
			}
			$("#toUserId").val(userids);
			$("#toUserName").val(usernames);
		},
		getAsyncUrl:function(treeId, treeNode){
			if ($.SystemApp.deptJscript.showPerson && treeNode.endflag==1){
				return $.SystemApp.contextPath + "/system/user/getUserByDepartment?deptId="+treeNode.id;
			}
			
			return $.SystemApp.contextPath + "/system/department/getDepotdata";
		},
		init_tree : function(treeData,isShowPerson) {
			if (isShowPerson!=undefined)
				this.showPerson = isShowPerson;
			this.departmentTree = $.fn.zTree.init($("#departmenttree"), this.setting(), treeData);
			var nodes = this.departmentTree.getNodes();
				
			if (nodes.length >0)
				this.departmentTree.expandNode(nodes[0]);
			return this.departmentTree;
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
	apply:function(){
		var selectValue = $.SystemApp.checkbox("#tableHover_collInfo");
		if (selectValue == null || selectValue == '' || selectValue.length <= 0) {
			alert("请选择数据后再申请！");
			return;
		};
		var data = {
			ids : selectValue
		};
//		if (paramData!=undefined)
//			$.extend(data,paramData);
		$.post($.SystemApp.contextPath + "/appraisalinfo/apply", data, function(data) {
			if (data.successRows > 0) {
				$.SystemApp.pagetoolbar.refreshData();
				alert("数据删除成功！");
			}
		});
		
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
$.SystemApp.messageJscript = {
		dialogObj : null,
		curType:'accept',
		messageIndex:function(){
			$.SystemApp.divLoad("#main-content","/message/index",undefined,function(){
				
			});
		},
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/message/list", {userId:'',searchContent:'',type:"accept"},
					function() {

					});
		},
		folderFilter:function(obj){
			var $this = $(obj);
			$("a.list-group-item").removeClass("active");
			$this.addClass("active");
			var type =$this.data("type");
			this.curType = type;
			var paramData = {
					userId:'',
					searchContent:'',
					type:type
			};
			
			this._handleHeader($this.data('text'),false);
			$.SystemApp.divLoad("#systemData", "/message/list", paramData,
					function() {

					});
		},
		tagFilter:function(obj){
			var $this = $(obj);
			var type =$this.data("type");
			this.curType = type;
			var paramData = {
					userId:'',
					searchContent:'',
					type:type
			};
			this._handleHeader($this.data('text'),false);
			$.SystemApp.divLoad("#systemData", "/message/list", paramData,
					function() {

					});
		},
		showEdit : function(isEdit) {
			this._handleHeader("创建新消息",true);
			var _this = this;
			//_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/message/showEdit","tableHover_messagebox","中文标题编辑");
			$.SystemApp.divLoad("#systemData", "/message/showMessage", {},
					function() {

					});
		},
		_handleHeader:function(title,isNew){
			$(".box-header","#mainDataArea").find(".box-title").text(title);
			if (isNew)
				$(".box-header").find(".box-tools").hide();
			
		},
		del : function() {

			var paramData = {
					curType:this.curType
			};
			$.SystemApp.commonOper.del("/message/delete","tableHover_messagebox",paramData);
			
		},
		reply:function(dataId){
			//dataId,参数，查看消息单条信息ID时
			var selectValue ; 
			if (!dataId){
				selectValue = $.SystemApp.checkbox("#tableHover_messagebox" );
				if (selectValue == null || selectValue == '' || selectValue.length <= 0) {
					return;
				}
			}else{
				selectValue= [dataId];
			}
			var data = {
				ids : selectValue
			};
			
			this.dialogObj = $.SystemApp.openDialog($.SystemApp.contextPath
					+ "/message/showReply", {
				data : data,
				title : "消息"
			});			
			
		},
		save : function(formObject,isSend,isReply) {
			var $this = this;
			var content = "";
			if (!isReply)
				content =  $('.wysiwye-editor').code();
			else
				content = $("#content1").val();
			
			var paramData = [{name:'content',value:content},{name:'userids',value:$("#toUserId").val()},{name:'isSend',value:isSend}];
			var $formObject = $("#" + formObject);
			var validateResult = $formObject.validationEngine('validate');
			if (!validateResult)
				return false;

			var data = $formObject.serializeArray();
			$.merge(data,paramData);
			$.post($.SystemApp.contextPath + "/message/update", data, function(data) {
				if (data.successRows > 0) {
					if (isSend && !isReply){
						alert("消息发送成功！");
						$this._handleHeader("收件箱",false);
						$this.init_UI();
					}else{
						alert("数据保存成功！");
						$("#msgBoxId").val(data.msgboxid);
					}
				} else {
					alert("数据保存失败！");
				}
			});
		},
		find : function(formobject) {
			var searchValue = $("#searchContent").val();
			var data = {
				searchContent :searchValue,
				type:this.curType
			};
			$.SystemApp.divLoad("#systemData", "/message/list", data,
					function() {

			});
		}
	};
$.SystemApp.appraisalinfoJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/appraisalinfo/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/appraisalinfo/showEdit","tableHover_appraisalinfo","鉴定信息");

		},
		del : function() {
			$.SystemApp.commonOper.del("/appraisalinfo/delete","tableHover_appraisalinfo");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/appraisalinfo/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/appraisalinfo/list", data,
					function() {

					});
		},
		_handleCloneDiv:function(cloneObject){
			var index = parseInt($("#expertNum").val())+1;
			$("#expertNum").val(index);
			cloneObject.find("input[type!=button]").each(function(i,obj){
				var $this = $(this);
				$this.val("");
				var fieldname=$this.data('field');
				//$this.hasClass(".datepicker").
				$this.prop("id","expertIdeas["+index+"]."+fieldname);
				$this.prop("name","expertIdeas["+index+"]."+fieldname);
			});
			
			cloneObject.find("textarea").each(function(i,obj){
				var $this = $(this);
				$this.val("");
				var fieldname=$this.data('field');
				$this.prop("id","expertIdeas["+index+"]."+fieldname);
				$this.prop("name","expertIdeas["+index+"]."+fieldname);
			});
			
			cloneObject.find(".input-group.date").addClass("form_datetime");
			
			cloneObject.find(".form_datetime").datetimepicker({
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
			return cloneObject;
		},
		pageDelExpertIdea:function(expertIdeaId){
			if (expertIdeaId!=undefined && expertIdeaId != ""){
				var delids = $("#delIds").val()+","+expertIdeaId;
				$("#delIds").val(delids);
			};
			var $this = $(event.target);
			var $cloneData = $this.parent().parent().parent().clone();
			$cloneData.find(".btn_add").remove();
			$this.parent().parent().parent().remove();
			if ($(".expertdata").length==0){
				$cloneData = this._handleCloneDiv($cloneData);
				
				$("#expertdatas").append($cloneData);
			};
			
		},
		pageAddExpertInfo:function(){
			var $expertData = $(".expertdata").last();
			
			var $cloneData = $expertData.clone();
			
			$expertData.find(".btn_add").remove();
			$cloneData = this._handleCloneDiv($cloneData);
			
			$expertData.after($cloneData);

		}
	};




$.SystemApp.restorationinfoJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/restorationinfo/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/restorationinfo/showEdit","tableHover_restorationinfo","中文标题编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/restorationinfo/delete","tableHover_restorationinfo");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/restorationinfo/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/restorationinfo/list", data,
					function() {

					});
		}
	};


$.SystemApp.restorationprojectJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/restorationproject/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/restorationproject/showEdit","tableHover_restorationproject","中文标题编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/restorationproject/delete","tableHover_restorationproject");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/restorationproject/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/restorationproject/list", data,
					function() {

					});
		}
	};


$.SystemApp.restorationreceiptJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/restorationreceipt/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/restorationreceipt/showEdit","tableHover_restorationreceipt","中文标题编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/restorationreceipt/delete","tableHover_restorationreceipt");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/restorationreceipt/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/restorationreceipt/list", data,
					function() {

					});
		}
	};


$.SystemApp.culturalreliccopyJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/culturalreliccopy/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/culturalreliccopy/showEdit","tableHover_culturalreliccopy","中文标题编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/culturalreliccopy/delete","tableHover_culturalreliccopy");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/culturalreliccopy/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/culturalreliccopy/list", data,
					function() {

					});
		}
	};


$.SystemApp.duplicateinfoinfoJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/duplicateinfoinfo/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/duplicateinfoinfo/showEdit","tableHover_duplicateinfoinfo","中文标题编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/duplicateinfoinfo/delete","tableHover_duplicateinfoinfo");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/duplicateinfoinfo/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/duplicateinfoinfo/list", data,
					function() {

					});
		}
	};




$.SystemApp.culMainCulJscript = {
		
		dialogObj : null,
		indextree:null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/culmaincul/list", '',
					function() {

					});
		},
		setting:function(){
			var tmpsetting = $.SystemApp.treesetting;
			var $that = this;
			 $.extend(tmpsetting,{
				 check:{
						enable: false
					},
			 	async: {
					enable: true,
					url:$.SystemApp.contextPath + "/system/dataindex/getIndexdata",
					autoParam:["id","pId"]
				},
				callback: {
					onClick: function(event, treeId, treeNode){
						$that.filterData(treeNode);
					},
					beforeClick:function(treeId, treeNode){
						
					}
				}
			 });
			return tmpsetting;
		},		
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/culmaincul/showEdit","tableHover_culMainCul","文物信息编辑",810);

		},
		del : function() {
			$.SystemApp.commonOper.del("/culmaincul/delete","tableHover_culMainCul");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/culmaincul/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/culmaincul/list", data,
					function() {

					});
		},
		init_tree : function(treeData) {
			this.indextree = $.fn.zTree.init($("#indextree"), this.setting(), treeData);
			var nodes = this.indextree.getNodes();
			if (nodes.length >0){
				this.indextree.selectNode(nodes[0]);
				this.indextree.expandNode(nodes[0]);
			}
		},
		filterData:function(treeNode){
			var pId= treeNode.id ;
			if (treeNode.level==0) //根节点不作处理
				return ;
			var allObj = $("div[class^='div_']","#editculMainCul");
			var showObj = $("div[class^='div_"+pId+"'] ","#editculMainCul");
			allObj.hide();
			showObj.show();
		}
	};


$.SystemApp.culShowCustomJscript = {
		dialogObj : null,
		init_UI : function() {
				$.SystemApp.divLoad("#systemData", "/culshowcustom/list", '',
					function() {

					});
		},
		showEdit : function(isEdit) {
			var _this = this;
			_this.dialogObj = $.SystemApp.commonOper.showEdit(isEdit,"/culshowcustom/showEdit","tableHover_culShowCustom","文物信息编辑");

		},
		del : function() {
			$.SystemApp.commonOper.del("/culshowcustom/delete","tableHover_culShowCustom");
			
		},
		save : function(formObject) {
			$.SystemApp.commonOper.save("/culshowcustom/update",formObject);
			
		},
		find : function(formobject) {
			var data = $("#"+formobject).serialize();

			$.SystemApp.divLoad("#systemData", "/culshowcustom/list", data,
					function() {

					});
		}
	};


