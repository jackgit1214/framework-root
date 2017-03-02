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
// 公共模态窗口初始化
$().ready(function() {

});
// /系统控制脚本
$.SystemApp = {
    contextPath: '',
    modalParam: {
        data: {},
        title: "窗口",
        width: 700,
        height: 500
    },
    treesetting: {
        check: {
            enable: true
        },
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: 'id',
                pIdKey: 'pId'
            },
            key: {
                url: 'url'
            }
        }
    }
};
$.SystemApp.options = {
    controlSidebarOptions: {
        // 控制sidebar选择器
        toggleBtnSelector: '[data-toggle=\'control-sidebar\']',
        // sidebar选择器
        selector: '.control-sidebar',
        slide: true
    },
    pageToolbarOptions: {
        selector: ".pagetoolbar",
        showMaxPageNum: 5
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
    $("body").on("DOMNodeInserted", ".con-main,.table-con,.table-data",
    function(e) {
        $(".con-main").height($conmainH);
        $(".table-con").height($tableConH);
        $(".table-data").height($tableConH - 50);
        // $.SystemApp.initComponents();
    });

};
$.SystemApp.initComponents = function() {

    // check box
    if ($('input:checkbox, input:radio')[0]) {

        // Checkbox + Radio skin
        $('input:checkbox:not([data-toggle="buttons"] input, .make-switch input), input:radio:not([data-toggle="buttons"] input)').iCheck({
            checkboxClass: 'icheckbox_minimal',
            radioClass: 'iradio_minimal',
            increaseArea: '20%' // optional
        });

        $('.make-switch').bootstrapSwitch();
        // Checkbox listing
        var parentCheck = $('.list-parent-check');
        var listCheck = $('.list-check');
        parentCheck.on('ifChecked',
        function() {
            $(this).closest('.list-container').find('.list-check').iCheck('check');
        });

        parentCheck.on('ifClicked',
        function() {
            $(this).closest('.list-container').find('.list-check').iCheck('uncheck');
        });

        listCheck.on('ifChecked',
        function() {
            var parent = $(this).closest('.list-container').find('.list-parent-check');
            var thisCheck = $(this).closest('.list-container').find('.list-check');
            var thisChecked = $(this).closest('.list-container').find('.list-check:checked');

            if (thisCheck.length == thisChecked.length) {
                parent.iCheck('check');
            }
        });

        listCheck.on('ifUnchecked',
        function() {
            var parent = $(this).closest('.list-container').find('.list-parent-check');
            parent.iCheck('uncheck');
        });

        listCheck.on('ifChanged',
        function() {
            var thisChecked = $(this).closest('.list-container').find('.list-check:checked');
            var showon = $(this).closest('.list-container').find('.show-on');
            if (thisChecked.length > 0) {
                showon.show();
            } else {
                showon.hide();
            }
        });

    }

    /** form components */
    if ($("[class*='form-validation']")[0]) {
        $("[class*='form-validation']").validationEngine();

        // Clear Prompt
        $('body').on('click', '.validation-clear',
        function(e) {
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
            style: 'form-control'
        });

        $('.selectpicker').on('loaded.bs.select',
        function(e) {
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
    $('.panel-collapse').on('shown.bs.collapse',
    function() {
        $(this).prev().find('.panel-title a').removeClass('active');
    });

    $('.panel-collapse').on('hidden.bs.collapse',
    function() {
        $(this).prev().find('.panel-title a').addClass('active');
    });

    // Popover
    if ($('.pover')[0]) {
        $('.pover').popover();
    }

};

$.SystemApp.openDialog = function(url, param) {

    if (param == null) param = $.SystemApp.modalParam;

    var _curdate = new Date();
    var rando = Math.round(Math.random() * 100 + 5);
    var modalId = "tmp_" + rando + _curdate.getTime();

    var modalAttr = {
        id: modalId,
        tabindex: "-1",
        "aria-hidden": "true",
        role: "dialog"
    };
    var $modalDiv = $("<div>").addClass("modal fade").attr(modalAttr);

    var $mdDialog = $("<div id='" + modalId + "_drag" + "'>").addClass("modal-dialog");
    var $mdCon = $("<div>").addClass("modal-content");
    var $mdHeader = $("<div>").addClass("modal-header").html("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>	<h4 class='modal-title'>" + param.title + "</h4>");

    var $mdFooter = $("<div>").addClass("modal-footer m-t-0");
    var $mdBody = $("<div>").addClass("modal-body");

    $.get(url, param.data,
    function(data) {

        var $data = $(data);
        if ($data.find(".modal-footer")) {
            var $footer = $data.find(".modal-footer");
            $mdFooter.html($footer.html());
            $footer.remove();
        }
        $mdBody.append($data);
    });

    $mdCon.append($mdHeader).append($mdBody).append($mdFooter);
    $mdDialog.append($mdCon);
    $modalDiv.append($mdDialog);
    $("body").append($modalDiv);
    // 窗口关闭后加载
    var $modalDialog = $("#" + modalId);
    $modalDialog.on("hidden.bs.modal",
    function() {
        $modalDialog.remove();
    });

    // /窗口打开后加载
    $modalDialog.on("shown.bs.modal",
    function() {
        $("#" + modalId + "_drag" + "").draggable({
            handle: ".modal-header" // 只能点击头部拖动
        });
        $("#" + modalId).css("overflow", "hidden");
        // 初始化组件
        $.SystemApp.initComponents();
    });

    $modalDialog.modal({
        show: true,
        keyboard: false,
        backdrop: 'static'
    });

    return $modalDialog;
};

$.SystemApp.initMenu = function(menuUrl) {

    var _menulink = $.SystemApp.contextPath + menuUrl;

    jQuery.ajax({
        type: 'GET',
        url: _menulink,
        dataType: 'json',
        timeout: 50000,
        cache: false,
        error: function(xhr) {
            alert('Error json data' + "\nHttp status: " + xhr.status + " " + xhr.statusText);
        },
        success: function(data) {
            var $ulcontent = loadJson(data);
            $("#sidebar").append($ulcontent);

            $('.side-menu > li > a').one('mouseover touchstart',
            function(e) {
                var $this = $(this);
                var superid = $this.attr("id");
                var dataParam = {
                    parentId: superid
                };
                $.getJSON($.SystemApp.contextPath + "/func/getFunction", dataParam,
                function(data) {
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
        $.each(data,
        function(i, module) {

            var $this = $(this);
            var $li = $("<li>");

            var $a = $("<a id='" + module.funcid + "' data-param=" + module.funcid + ">");
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
    };
    function loadJson(data, parentid) {

        if (parentid == undefined) parentid = "";
        var $ul = $("<ul>");
        if (parentid == "") {
            $ul.addClass("list-unstyled side-menu");
        }
        $.each(data,
        function(i, module) {
            var $this = $(this);
            var $li = $("<li>");
            if (i == 0) $li.addClass("active");

            var $a = $("<a id='" + module.funcid + "'  data-param=" + module.funcid + ">");
            var $i = $("<i>").addClass("fa fa-2x").addClass(module.funicon);
            var $span = $("<span>").addClass("menu-item").text(module.funcname);
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
        };
    });

    return selectArray;

};

$.SystemApp.loadSubMenu = function(source){
    // <ul class="list-unstyled menu-item">
    // <li><a href="form/form-elements.html">Basic Form Elements</a></li>
    // <li><a href="form/form-components.html">Form Components</a></li>
    // <li><a href="form/form-examples.html">Form Examples</a></li>
    // <li><a href="form/form-validation.html">Form Validation</a></li>
    // </ul>
};

$.SystemApp.divLoad = function(sourcediv, url, data, callback) {

    if (url == undefined || url == "" || url == "#") return;
    if (data == undefined) data = {};

    $(sourcediv).load($.SystemApp.contextPath + url, data,
    function(data) {

        if (callback != undefined && $.isFunction(callback)) callback(data);
    });
}

/*
 * ControlSidebar ============== Adds functionality to the right sidebar
 * 
 * @type Object @usage $.AdminLTE.controlSidebar.activate(options)
 */
$.SystemApp.controlSidebar = {
    // instantiate the object
    activate: function() {
        // Get the object
        var _this = this;
        // Update options
        var o = $.SystemApp.options.controlSidebarOptions;
        // Get the sidebar
        var sidebar = $(o.selector);
        // The toggle button
        var btn = $(o.toggleBtnSelector);
        // Listen to the click event
        btn.on('click',
        function(e) {
            e.preventDefault();
            // If the sidebar is not open
            if (!sidebar.hasClass('control-sidebar-open') && !$('body').hasClass('control-sidebar-open')) {
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
    open: function(sidebar, slide) {
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
    close: function(sidebar, slide) {
        if (slide) {
            sidebar.removeClass('control-sidebar-open');
        } else {
            $('body').removeClass('control-sidebar-open');
        }
    },
    _fix: function(sidebar) {
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
                'position': 'fixed',
                'height': 'auto'
            });
        }
    },
    _fixForFixed: function(sidebar) {
        sidebar.css({
            'position': 'fixed',
            'max-height': '100%',
            'overflow': 'auto',
            'padding-bottom': '50px'
        });
    },
    _fixForContent: function(sidebar) {
        $('.content-wrapper, .right-side').css('min-height', sidebar.height());
    }
};

$.SystemApp.pagetoolbar = {
    totalPageNum: 0,
    curPage: 0,
    target: null,
    linkhref: null,
    pagination: null,
    recordNum: 0,
    retrieveParam: null,
    endPage: 0,
    // 翻页工具条显示的结束页
    startPage: 0,
    // 翻页工具条显示的开始页
    toolbarInit: function() {
        var _this = this;
        var o = $.SystemApp.options.pageToolbarOptions;
        var $pagebar = $(o.selector);
        _this._getDataParam($pagebar);
        var showPageNum = o.showMaxPageNum;
        _this.endPage = showPageNum + _this.curPage;
        if (_this.totalPageNum < showPageNum) {
            showPageNum = _this.totalPageNum;
        }
        // 创建翻页滚动
        var _prePage = $("<li>").data("pageAct", "prev").append($("<a class='btn btn-default' id='page_prev' href='#'>").html("<i class='fa fa-angle-left'></i>"));
        _this.pagination.append(_prePage);

        var _endpage = showPageNum + _this.curPage;

        _endpage = Math.floor(_endpage / showPageNum) * showPageNum ;

        if (_endpage > _this.totalPageNum) _endpage = _this.totalPageNum;

        var _startpage = _this.curPage;
        if (_startpage == showPageNum) _startpage = _startpage - 1; // 修正当总页数与工具条显示页数正好一样的问题
        _startpage = Math.floor(_startpage / showPageNum) * showPageNum + 1; // 取倍数处理
        for (var i = _startpage; i <= _endpage; i++) {
            var _pagenum = $("<li>").append($("<a class='btn btn-default' href='#'>").html(i));
            _pagenum.data("pageAct", i);
            _this.pagination.append(_pagenum);
        }

        var _lastPage = $("<li>").data('pageAct', "next").append($("<a class='btn btn-default' id='page_next' href='#'>").html("<i class='fa fa-angle-right'></i>"));
        _this.pagination.append(_lastPage);
        _this._bindClickEvent($pagebar);

    },
    _getDataParam: function(pagebar) {
        var _this = this;
        _this.totalPageNum = pagebar.data("totalpage");
        _this.curPage = pagebar.data("curpage");
        _this.target = pagebar.data("linktarget");
        _this.linkhref = pagebar.data("pagelink");
        _this.recordNum = pagebar.data("recordcount");
        _this.retrieveParam = pagebar.data("param");
        _this.pagination = $(".pagination", pagebar);
    },
    _bindClickEvent: function(pagebar) {
        var _this = this;

        var $pageination = $(".pagination", pagebar);
        $pageination.find("li").each(function(i, obj) {
            var _obj = $(obj);
            var _goPage = _obj.data("pageAct");
            if (_goPage === "next") {
                _obj.find("a").bind("click",
                function() {
                    _this.refreshData(_this.endPage + 1);
                });
            } else if (_goPage === "prev") {
                _obj.find("a").bind("click",
                function() {
                    var page = _this.startPage - 1;
                    if (page < 0) page = 1;
                    _this.refreshData(page);
                });
            } else {

                if (_goPage == _this.curPage) return;
                _obj.find("a").bind("click",
                function() {
                    _this.refreshData(_goPage);
                });
            };
        });
        _this._pageNumChangeMonitor(_this.curPage);
    },
    _pageNumChangeMonitor: function(turnPage) {

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
    refreshData: function(turnPage) {

        var _this = this;
        if (turnPage == undefined) turnPage = _this.curPage;

        if (_this.retrieveParam == undefined || _this.retrieveParam == "" || _this.retrieveParam == null) _this.retrieveParam = {};

        var isJson = $.isPlainObject(_this.retrieveParam);

        var tmpdata = {};

        if (isJson == false) {
            tmpdata = $.parseJSON(_this.retrieveParam);
        } else tmpdata = _this.retrieveParam;

        tmpdata.pageNo = turnPage;
        $.SystemApp.divLoad(_this.target, _this.linkhref, tmpdata,
        		function() {});
    }
};