
/**
 * 增加jquery的serializeObject方法生成form的json数据
 * @returns
 */
jQuery.prototype.serializeObject=function(){  
    var hasOwnProperty=Object.prototype.hasOwnProperty;  
    return this.serializeArray().reduce(function(data,pair){  
        if(!hasOwnProperty.call(data,pair.name)){  
            data[pair.name]=pair.value;  
        }  
        return data;  
    },{});  
};  

/**
 * 针对jquery进行扩展，使对象适应父对象宽度，高度
 *
 *
 */

(function($) {

	$.fn.extend({
		/**
		 * 针对div设定适应父对象的宽度
		 */
		adaptParentWidth: function(_width) {
			$this = $(this);
			$parent = $this.parent();
			var borderWidth = $this.outerWidth() - $this.innerWidth(); //边框宽
			var paddingWidth = $this.innerWidth() - $this.width(); //补白宽度
			var marginWidth = 0;
			if (!isNaN(parseInt($this.css("margin-left"))))
				marginWidth = parseInt($this.css("margin-left"));
			if 	(!isNaN(parseInt($this.css("margin-right"))))
				marginWidth = marginWidth +parseInt($this.css("margin-right"));

			var parentWidth = _width;
			if (parentWidth==undefined) {
				if ($parent[0].tagName == "BODY")
					parentWidth = $(window).width();
				else
					parentWidth = $parent.width();
			}else if ( parentWidth < 0 ){
				parentWidth = $parent.width()+parentWidth;
			}
			$this.width(parentWidth - borderWidth - paddingWidth - marginWidth);
			 
		},
		getParentWidth:function(){
			var  $this = $(this);
			var $parent = $this.parent();
			var parentWidth =0;
			if ($parent[0].tagName == "BODY")
				parentWidth = $(window).width();
			else
				parentWidth = $parent.width();	
			
			return parentWidth;
		},
		getParentHeight:function(){
			var  $this = $(this);
			var $parent = $this.parent();
			var parentHeight =0;
			if ($parent[0].tagName == "BODY")
				parentHeight = $(window).height();
			else
				parentHeight = $parent.height();

			return parentHeight;
		},
		/**
		 * 针对div设定适应父对象的高度
		 */
		adaptParentHeight: function(_height) {
			$this = $(this);
			$parent = $this.parent();

			var borderHeight = $this.outerHeight() - $this.innerHeight(); //边框宽
			var paddingHeight = $this.innerHeight() - $this.height(); //补白宽度
			var marginHeight = 0;
			if (!isNaN(parseInt($this.css("margin-top"))))
				marginHeight = parseInt($this.css("margin-top"));
			if 	(!isNaN(parseInt($this.css("margin-bottom"))))
				marginHeight = marginHeight +parseInt($this.css("margin-bottom"));
			var parentHeight = _height;
			if (parentHeight == undefined ) {
				if ($parent[0].tagName == "BODY")
					parentHeight = $(window).height();
				else
					parentHeight = $parent.height();
				
			}else if ( parentHeight < 0 ){
				parentHeight = $parent.height()+parentHeight;
			}
					
			$this.height(parentHeight - borderHeight - paddingHeight - marginHeight);
		},
		/**
		 * 针对div设定适应父对象的尺寸，高度宽度
		 */
		adaptParentSize: function(_width,_height) {
			this.adaptParentWidth(_width);
			this.adaptParentHeight(_height);
		}
	});

	$.extend({
		init_UI: function($object) {
			//初始化表格样式
			//$(".queryarea",$object).detail({inputreadonly:false});
			//初始化按钮样式
			$(".frameleft").addClass("ui-corner-all  ui-widget-contentshadow");
			$(".frameright").addClass("ui-corner-all ui-widget-contentshadow");
			$(".framecon").addClass(" ui-widget-contentshadow");
			$.loadDivInit_UI();

		},
		
		loadDivInit_UI:function($object){
			
			var scrollPageBarHeight = 0;
			var toolBarHeight = 0;
			var $gridtable = $(".divgridtable",$object);
			
			if ($gridtable.data("turnpage")=="true" || $gridtable.data("turnpage")==undefined ){
				scrollPageBarHeight = $(".toolbar").height()+6;
				
			}
				
			if ($gridtable.data("toolbar")=="true" || $gridtable.data("toolbar")==undefined){
				toolBarHeight = $(".toolbar").height()+6;
				
			}			
			
			$gridtable.gridtable({
				nowrap:0,
				lineHeight:30,
				titleHeight:30,
				isAdaptWidth:false,
				isAdaptHeight:false,
				scrollPageBar:scrollPageBarHeight,
				toolbar:toolBarHeight
			});
			
			$(".scrollPageBar",$object).toolbar({isCorner:false});
			$(".toolbar",$object).toolbar({isCorner:false});
			
			$(".tabs",$object).tabs();
			
			 $( "input[type=submit],  button" ).button();
		},
		
		initMenu: function(menu,urlroot) {
			
			jQuery.ajax({
				type:'GET',
				url:menu,
				dataType:'json',
				timeout: 50000,
				cache: false,
				error: function(xhr) {
					alert('Error json data' + "\nHttp status: " + xhr.status + " " + xhr.statusText);
				},
				success: function(data) {
					var $ulcontent = loadJson(data);
					$(".framemenu").append($ulcontent)
								 .addClass("ui-accordion-header")
								 .addClass("ui-state-default ui-corner-all ui-accordion-icons");
					$("#menubar1").menubar({
						selecticons:{select:"ui-icon-triangle-1-s"}
					});
					//$("#menubar1").removeClass("ui-widget-header");
					$("#menu1").trigger("click");
				}
			});

			function loadJson(data,parentid){
				if (parentid==undefined)
					parentid = "";
				var $ul = $("<ul></ul>");
				if (parentid =="") {
					$ul.attr("id","menubar1");
					$ul.addClass("menubar");
				}
				$.each(data,function(i,module){
					var $this = $(this);
					var $li = $("<li></li>");
					var $a = $("<a id='"+module.funcid+"' href='#' data-param="+module.funcid+"></a>");
					$a.text(module.funcname);
					if (module.targetDiv!=undefined) {
						var target = module.targetDiv;
						var link = module.urllink;
						var urlParam = "{id:"+module.funcid+"}";
						$a.attr("href","javascript:$.divLoad('#"+target+"','"+urlroot+link+"',"+urlParam+")");
//						$a.bind("click",function(){
//							$.divLoad('#'+target,link);
//						});
					}
					$li.append($a);
					$ul.append($li);
				});
				return $ul;
			};
			function loadXml($menu,parentid) {
				if (parentid==undefined)
					parentid = "";
				var $ul = $("<ul></ul>");
				if (parentid =="") {
					$ul.attr("id","menubar1");
					$ul.addClass("menubar");
				}
				$menus.find("menu[parentid="+parentid+"]").each( function() {
					var $this = $(this);
					var $li = $("<li></li>");
					
					var $a = $("<a id='"+$this.attr("id")+"' href='#'></a>");
					$a.text($this.attr("name"));
					if ($this.attr("target")!=undefined) {
						var target = $this.attr("target");
						//$a.attr("href","javascript:$.divLoad('#"+target+"','"+$this.attr("link")+"')");
						$a.bind("click",function(){
							$.divLoad('#'+target,$this.attr("link"));
						});
					}
					$li.append($a);
					if ($this.find("menu").length> 0 ) {
						$tmpul = loadXml($this,$this.attr("id"));
						$li.append($tmpul);
					}
					$ul.append($li);
				});
				return $ul;
			};

		},
		divLoad: function (sourcediv,url,data,callback) {
			if (url==undefined || url=="" || url=="#" )
				return ;
			if (data==undefined)
				data = {};
			
			
			$(sourcediv).load(url,data, function(data) {
				$.init_UI($(sourcediv));
				if (callback!=undefined && $.isFunction(callback))
					callback(data);
			});
		}
	});
})(jQuery);


/*
 * 实现图片预览效果
 * 1、图片可自动切换，自动切换时间参数
 * 2、图片可手动切换,手动切换类型，两种类型，数字与左右方向 1为数字，2为左右方向
 * 3、图片打开事件
 * 4、图片大小控制参数
 * 5、支持鼠标停留事件，图片停止切换
 * 、基本使用方式<div><img></img></div>
 */
(function( $, undefined ) {
	$.widget("ui.viewPicture", {
		version: "1.0",
		widgetEventPrefix: "viewPicture",
		options:{
			picWidth:200,
			picHeight:200,
			scrollTimer:2000,
			manualType:1,
			isAdaptSize:true,
			borderWidth:20,
			disabled:false //是否可切换
		},
		_create: function() {
			this._setAdaptSize();
			this.element.addClass("ui-pic-root");
			this._initDivImg();
		},
		_initDivImg:function(){
			var $this = this;
			this.images = this.element.find("img");
			this.picTotalCount = this.images.length;
			this.picindex = 0;
			
			this.divImages = this.images.wrap("<div></div>").parent();
			//距中设置
			//计算边距
			this.options.borderWidth = this.element.parent().width() - this.options.picWidth ;
			this.images.css({width:this.options.picWidth,
							 height:this.options.picHeight,
							 paddingLeft :this.options.borderWidth/2,
							 paddingRight :this.options.borderWidth/2}) ;
			///
			this.divImages.addClass("ui-pic")
				.css({width:this.options.picWidth,height:this.options.picHeight});
			
			if (!this.options.disabled){
				this.divImages.bind("mouseover",function(){
					$this._picMouseOver();
				});
				this.divImages.bind("mouseout",function(){
					$this._picMouseOut();
				});
			}
			if (this.options.manualType == 1) {
				this._createPinNum();
				this.options.csstype = ".ui-picnum";				
			}
			else if(this.options.manualType == 2){
				this._createControlBtn();
				this.options.csstype = ".ui-picbtn";				
			}
		},
		_init:function(){
			//var $this = this;
			this.divImages.eq(this.picindex).show().addClass("picselect");
			if (!this.options.disabled){
				this.scrollTimer = window.setInterval(function(){$this._picChanged();},this.options.scrollTimer);
			}
		},
		_destroy:function(){
			
		},
		_setOption: function(key, value ) {
			this._super( key, value );
		},
		_picslide:function(index) {
			this.divImages.eq(index) 
				.fadeIn(600)
				.addClass("picselect")
				.siblings(":not("+this.options.csstype+")")
				.hide()
				.removeClass("picselect");
			$(".ui-picnum").removeClass("numselected")
				.addClass("numunselected")
				.eq(index).addClass("numselected").removeClass("numunselected");
			this.picindex = index; //点击事件会更改索引号，重新赋值	
		},
		_picChanged:function(){
			if (this.options.disabled)
				return ;
			if (this.picindex + 1 > this.picTotalCount ) 
				this.picindex=0;
			this._picslide(this.picindex);
			this.picindex++;
		},
		_picMouseOver:function(){
			window.clearInterval(this.scrollTimer);
		},
		_picMouseOut:function(){
			var $this = this;
			this.scrollTimer = window.setInterval(function(){$this._picChanged();},this.options.scrollTimer);
		},
		_createPinNum:function(){
			var $this = this;
			for(var i=0; i < this.picTotalCount ; i++ ){
				var $divnum = $("<div></div>").addClass("ui-picnum")
							  .addClass("numunselected")
							  .wrapInner("<a id='pica"+i+"' href='#'>"+(i+1)+"</a>")
							  .css("right",15 * (this.picTotalCount - i) + this.options.borderWidth/2 -  i * 5 );
				if (i == this.picindex){
					$divnum.removeClass("numunselected")
						   .addClass("numselected");
				}
				$divnum.find("a").bind("click",function(){
					var curindex = $(this).text() - 1; 
					$this._picslide(curindex);
				});			  
				this.element.append($divnum);	
										  
			}
		},
		_createControlBtn:function(){
			var $this = this;
			var $nextBtn = $("<div></div>")
					.addClass("ui-picbtn ui-picnextbtn");
			var $prevBtn = $("<div></div>")
					.addClass("ui-picbtn ui-picprevbtn");
			$nextBtn.bind("click",function(){
				$this.picindex++;
				if ($this.picindex >= $this.picTotalCount ) 
					$this.picindex=0;
				$this._picslide($this.picindex);
			});
			$prevBtn.bind("click",function(){
				$this.picindex -- ;
				if ($this.picindex < 0 ) 
					$this.picindex= ($this.picTotalCount - 1) ;
				$this._picslide($this.picindex);
			});			
			this.element.append($nextBtn);
			this.element.append($prevBtn);
		},
		_setAdaptSize:function(){
			if (!this.options.isAdaptSize)
				return;
			var $elementParent = this.element.parent();	
			this.options.picWidth = $elementParent.width() - this.options.borderWidth;
			//this.options.picHeight = $elementParent.height();	
			
		}
	});
})(jQuery);




/*
 * 实现数据区域滚动条
 * 
 * 1、按钮暂时不自动创建
 * 2、滚动条引用位置位于数据区域下方的DIV内，引用方法<div class="scrollPageBar"></div>
 * 3、定位方法：相对于滚动条父对象的区域的最底部
 * 4、参数说明
 * 	barHeight:滚动条高度
 * 5、依赖于ui的button,position
 */
(function( $, undefined ) {
	$.widget("ui.toolbar", {
		version: "1.0",
		widgetEventPrefix: "toolbar",
		options:{
			barHeight:27,
			isShowText:true
		},
		buttonArray:[
			{id:"pageFirst",label:"首页",icon:"ui-icon-seek-start",compute:function(){return 1;}},
			{id:"pagePrev",label:"上页",icon:"ui-icon-seek-prev",compute:function(){
						this.options.curPage = this.options.curPage - 1;
						if (this.options.curPage <=0 )
							this.options.curPage= 1;
						return this.options.curPage;
					}
				},
			{id:"pageNext",label:"下页",icon:"ui-icon-seek-next",compute:function(){
					this.options.curPage = this.options.curPage+1;
					if (this.options.curPage > this.options.totalPage)
						this.options.curPage = this.options.totalPage;
					return this.options.curPage;	
				}},
			{id:"pageLast",label:"末页",icon:"ui-icon-seek-end",compute:function(){return this.options.totalPage;}}
		],
		_create: function() {
			if (this.options.isCorner)
				this.element.addClass("ui-corner-all");
			if (this.element.hasClass("scrollPageBar")){
				this.isScrollBar = true;
				this.element.addClass("toolbar");
			}
			 
			this._createArea();
			if (this.isScrollBar){
				this._initWidgetScrollBarData();
				this._createPageGo();
				this._createTurnPageTool();
			}else{
				this._initWidgetToolbarData();
				this.element.append(this.rightArea)
						.append(this.leftArea);	
			}
		},

		_initWidgetScrollBarData:function(){
			//初始化当前页参数
			this.options.curPage= parseInt(this.element.data("curpage")); //当前页
			this.options.totalPage = parseInt(this.element.data("totalpage"));
			this.options.recordCount = parseInt(this.element.data("recordcount"));
			this.options.param = this.element.data("param");
			this.options.pageLink = this.element.data("pagelink"); //页面跳转link
			this.options.linkTarget = $(this.element.data("linktarget"));//页面目标，当没有时为父对象
			this.options.turnPageMethod = this.element.data("turnPageMethod");
			if (this.options.linktarget==undefined)
				this.options.linktarget = this.element.parent();			
		},
		//初始化toolbar中内容，只是按钮
		//按钮属性
		// icons:是否有图标
		// input 取value值，button取
		// 事件click , 取属性id,
		_initWidgetToolbarData:function(){
			//var $that = this;
			this.element.find("input[type=button],button").each(function(i,domE){
				var $objB = $(domE);
				$objB.button({
					text:true,
					icons:{
						primary:$objB.data("icon")
					}
				}).css("border","0px");
				//$that.rightArea.append($objB);
			});
		},
		_destroy:function(){
			
		},
		//创建左右显示区域
		_createArea:function(){
			
			this.leftArea = this.element.find(".leftarea");
			this.rightArea = this.element.find(".rightarea");
			
			if (this.leftArea.length==0)
				this.leftArea = $("<div></div>").addClass("leftarea");
			if (this.rightArea.length==0)
				this.rightArea = $("<div></div>").addClass("rightarea");
			
			this.leftArea.css("float","left");
			this.rightArea.css("float","right");
		},
		//创建工具条
		_createTurnPageTool:function(){
			var $that = this;
			$.each(this.buttonArray, function(i,objattr){
				var $tmpbutton = $that._createButton()
							  .attr("id",objattr.id)
							  .button({label:objattr.label,text:$that.options.isShowText,icons: {primary: objattr.icon}})
							  .css("border","0px");
				$tmpbutton.click(function(event){
					var tmppage = objattr.compute.call($that);
					$that._buttonClick(event,tmppage);
				});  
				
				$that.rightArea.append($tmpbutton);
			});
			this.rightArea.append(this.pageGo);
			this.leftArea.append(this._createPageInfo());
			this.element.append(this.rightArea)
						.append(this.leftArea);	
			
			//$("button",this.element);					  
		},
		_createButton:function(){
			var $button = $("<button>");
			return $button;
		},
		//创建跳转以及跳转输入框
		_createPageGo:function(){
			var $that = this;
			this.pageGo = $("<div style='float:right'><input/><button></button></div>");
			this.pageGo.find("input").attr("id","pageInput")
										  .css("width","20")
										  .css("margin-top","2px")
										  .css("margin-right","2px");
			this.pageGo.find("button").attr("id","pageGo")
							  .button({
							  	text:false,
							  	label:"转",
							  	icons:{
							  		primary:"ui-icon-arrowreturnthick-1-w"
							  	}
							  }).css("border","0px")
							  .css("margin-top","-3px")
							  .click(function(event){
							  		var $input = $that.pageGo.find("input");
							  		var tmppage = $input.val();
							  		if (tmppage == undefined || tmppage==0 || tmppage=="")
							  			return ;
							  		if (tmppage > $that.options.totalPage){ //大于最大页时，取最大页
							  			tmppage = $that.options.totalPage;
							  			$input.val(tmppage);
							  		}
							  		$that.options.curPage = tmppage;
									$that._buttonClick(event,tmppage);
							  });
		},
		//页面汇总信息
		//每页显示X条     共X页  
		_createPageInfo:function(){
			var $info = this._createButton()
						.button({
							text:true,
							label:"每页显示"+this.options.recordCount+"条    共"+this.options.totalPage+"页  当前第"+
							this.options.curPage+"页"
						}).unbind()
						.css("border","0px");
			return $info;
		},				
		_buttonClick:function(event,turnpage){
			
			var tmplinktarget =this.options.linktarget; 
			if (tmplinktarget==undefined || tmplinktarget =="")
				alert("转到"+turnpage+"页！");
			else{
				this.refresh(turnpage);
			}
		},
		refresh:function(turnPage){
			var $that = this;
			if (turnPage==undefined)
				turnPage = this.options.curPage
			var tmplinktarget =this.options.linktarget; 
			var isJson = $.isPlainObject(this.options.param);
			
			var tmpdata = {};
			if (isJson == false){
				tmpdata = $.parseJSON(this.options.param);
			}else
				tmpdata = this.options.param;
			
			tmpdata.pageNo = turnPage;
			$.divLoad(this.options.linktarget,this.options.pageLink,tmpdata,function(data){
				$that._pageNumChangeMonitor(turnPage);
			});
		},
		_setOption: function(key, value ) {
			this._super( key, value );
		},
		_init:function(){
			//return ;
			var $that = this;
			this.element.addClass("ui-widget-header")
						.css("height",this.options.barHeight);
			var $neightborElemnet = this.element.next();
			if (this.isScrollBar){
				$neightborElemnet = this.element.prev();
			}			
			this.element.adaptParentWidth($neightborElemnet.width());
			bar_initPosition(this,$neightborElemnet);
			function bar_initPosition(obj,relativeObj){
				if (obj.isScrollBar){
					obj.element.position({
						of: relativeObj,
						my: "left top",
						at: "left bottom+3",
						collision: "none"
					});
				}else {
					obj.element.position({
						of: relativeObj,
						my: "left bottom",
						at: "left top",
						collision: "none"
					});
				}
			}
			
			$(window).resize( function() {
				$that.element.adaptParentWidth($neightborElemnet.width());
				bar_initPosition($that,$neightborElemnet);
			});
			$that._pageNumChangeMonitor(this.options.curPage);
		},
		_pageNumChangeMonitor:function(turnPage){
			//最后一页时，控制按钮
			if (this.options.totalPage == turnPage){
				$("#pageNext").button("disable");
				$("#pageLast").button("disable");
			}else{
				$("#pageNext").button("enable");
				$("#pageLast").button("enable");
			}
			//第一页时，控制按钮
			if (1 == turnPage){
				$("#pagePrev").button("disable");
				$("#pageFirst").button("disable");
			}else{
				$("#pagePrev").button("enable");
				$("#pageFirst").button("enable");
			}
		}
	});
})(jQuery);

