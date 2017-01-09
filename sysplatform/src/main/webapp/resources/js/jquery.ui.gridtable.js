/*!
 * jQuery UI gridtable .1
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

(function( $, undefined ) {
	$.widget("ui.gridtable", {
		version: "1.0",
		widgetEventPrefix: "gridtable",
		options: {
			scrollBarWidth:20,
			titleHeight:25,
			lineHeight:25,
			rowColorChange:true,
			isSelectedRow:true,
			sortfield:false,
			pagebar:true,
			titletype:"td",
			isAdaptWidth:false,
			isAdaptHeight:false,
			checkBoxElement:null,
			nowrap:0 //0为文本内容不折行，且超长部分以...结束，1为不折行超长部分剪切。2为折行显示
		},
		_create: function() {
			
			this.styleType = ["align","colspan","style","class"];
			this.thStyles = [];
			this.tdStyles = [];
			
			this._getTableElement();
			this._initTableHead();
			this._initTableData();
			this._createResizeMarkerDiv();
			this._checkBoxHandle();
			this._initTable();
			
		},
		//取当前表元素，
		_getTableElement: function() {
			var $that = this;
			var vtagName = this.element.get(0).tagName;
			if ( vtagName == "TABLE") {
				this.tableelement = this.element;
				this.element.wrap("<div class='grid'></div>");
				this.grid = this.element.parent();
			} else if (vtagName == "DIV") { //只支持一个table
				this.options.checkBoxElement =  this.element.data("checkbox");
				this.tableelement = this.element.find("table");
				//console.info(this.tableelement.html());
				
				this.grid = this.element.addClass("grid");
			}
		},
		_getElementSize: function(sizetype) {
			//return this.element[sizetype]();
			//console.info(this.grid.parent()["width"]());
			//console.info(this.grid.parent().attr("class"));
			return this.grid.parent()[sizetype]();
			
		},
		//初始化table的header区域 //初始化表头信息
		_initTableHead: function() {
			var that = this, options = this.options,$table = this.tableelement;
			var $oldThs = $table.find("thead>tr:last-child").find("th,td");
			if ($oldThs == undefined || $oldThs.size()==0 ) { //当没有thead时
				$oldThs = $table.find("tr:first-child").find("td");
			}
			this.thStyles = this._getTdAttrStyle($oldThs);

			var $thead = $table.find("thead");
			if ($thead.size()!=0)
				$thead = $thead.find("tr").clone();
			else
				$thead = $table.find("tr:first-child").clone();

			//console.info($table.attr("style")+":"+$table.width());
			this.thead = $("<div class='gridHeader' style='width:"+this._getElementSize("width")+
			"'><div class='gridThead'><table data-detail=false style='width:" +($table.width() - this.options.scrollBarWidth)+ "px'></table></div></div>");
			this.thead.find("table").append($thead);
			
			this._on({
				"mousemove .ui-tabledata": function(event) {
					this.resizeMarker.hide();
				}
			} );

			var $tds = this.thead.find("td");
			
			$tds.wrapInner("<div></div>");
			$tds.attr("istitle",true)
			.addClass("ui-state-default").css("line-height",(that.options.titleHeight - 2)+"px")
			.mousemove( function(event) {
				$this = $(this);
				that._tdMouseMoveEvent(event,$(this));
			});
			that._hoverable($tds);
			this._setTdWidth($tds);
		},
		//初始所有table数据区， //初始化数据信息
		_initTableData: function() {
			var that = this, options = this.options,$table = this.tableelement;
			var $oldTds = $table.find("tbody>tr:first-child").find("td");
			var scrollPageBarHeight = 0;
			var toolbarHeight=0;
			if (this.options.scrollPageBar != undefined)
				scrollPageBarHeight = this.options.scrollPageBar;
			if (this.options.toolbar != undefined)
				toolbarHeight = this.options.toolbar;
			if ($oldTds == undefined || $oldTds.size()==0 ) { //当没有tbody时
				$oldTds = $table.find("tr:nth-child(2)").find("td");
			}
			//this.tdStyles = this._getTdAttrStyle($oldTds);;
			var $tbody = $table.find("tbody");
			if ($tbody.size()!=0)
				$tbody = $tbody.find("tr");
			else
				$tbody = $table.find("tr:not(:first-child)");
				
			var tbodyheight = this._getElementSize("height") - this.options.titleHeight - scrollPageBarHeight - toolbarHeight  ;
			var tbodywidth = this._getElementSize("width")
			this.tbody = $("<div class='gridScroller' style='width:"+tbodywidth+	"px;height:"+tbodyheight+"px;'>" +
			"<div class='gridTbody' style='width:"+tbodywidth+	"px;height:"+tbodyheight+"px;'><table data-detail=false style='width:" +
			($table.width() - this.options.scrollBarWidth  )
			+ "px;'></table></div></div>");
			this.tbody.find("table").append($tbody);
			var $tds = this.tbody.find("td").css("line-height",(that.options.lineHeight-2)+"px");
			$tds.wrapInner("<div></div>").addClass("ui-tabledata");
			this._setTdWidth($tds);
			//设置单双行颜色
			if (this.options.rowColorChange) {
				this.tbody.find("tr:nth-child(odd)").addClass("tr_single");
				this.tbody.find("tr:nth-child(even)").addClass("tr_double");
			}

			this.tbody.find("tr").click( function() {
				if (!that.options.isSelectedRow)
					return ;
				var oldRowIndex =that.tbody.data("selectedRowIndex");
				if (oldRowIndex == this.rowIndex)
					return ;
				$(this).addClass("tr_selected");
				if (oldRowIndex!=undefined) {
					that.tbody.find("tr:eq("+oldRowIndex+")").removeClass("tr_selected");
				}
				that.tbody.data("selectedRowIndex",this.rowIndex);
			});
		},
		_initTable: function() {
			var $that = this;
			
			//this.tableelement.after(this.thead);
			//this.thead.after(this.tbody);
			//this.tbody.after(this.resizeMarker);
			this.grid.append(this.thead);
			this.grid.append(this.tbody);
			this.grid.append(this.resizeMarker);
			this.tableelement.hide();
			
			var opts = {
				axis:"x",
				appendTo: "parent",
				containment: "parent",
				cursor: "col-resize",
				alsoDrag:""
			};
			this.resizeMarker.draggable(opts);
			this.tbody.scroll( function(event) {
				$that._divScrollEvent(event);
			});
			if ($that.options.isAdaptWidth) {
				$that._adaptWidth();
			}
			if ($that.options.isAdaptHeight) {
				$that._adaptHeight();
			}
			
			//$that.grid.resize( function() {
			//	$that._adaptWidth();
			//	$that._adaptHeight();
			//})
		},
		_adaptWidth: function () {
			var _width = this.grid.width();
			this.tbody.width(_width);
			this.thead.width(_width);
		},
		_adaptHeight: function () {
			var scrollPageBarHeight = 0;
			if (this.options.scrollPageBar != undefined)
				scrollPageBarHeight = this.options.scrollPageBar
			if (this.options.toolbar != undefined)
				toolbarHeight = this.options.toolbar;	
			var _height = this._getElementSize("height") - this.options.titleHeight - scrollPageBarHeight - toolbarHeight  ;
			//只有列表主题适应窗口高度
			this.tbody.height(_height);
		},
		_divScrollEvent: function(event) {
			var header = this.thead;
			var scroller = this.tbody;
			if(scroller.scrollLeft() > 0) {
				var scroll = scroller.scrollLeft();
				var scrollpos = parseInt(scroller.css("left"));
				if (isNaN(scrollpos))
					scrollpos = 0;
				header.css("left",(scrollpos-scroll)+"px");
				header.width(header.width()+scroll);
			}
			if(scroller.scrollLeft() == 0) {
				header.css("left", "0px");
			}
			return false;
		},
		_setTdWidth: function($tds) {
			var $that = this,tdStyle=null;
			tdStyle = this.thStyles;
			$tds.each( function() {
				var $td = $(this);
				var cellIndex = this.cellIndex;
				$td.attr("cellindex",cellIndex);
				var tdWidth = tdStyle[cellIndex][0];
				
				var $div = $td.find("div");
				$td.css("width",tdWidth+"px");
				$td.attr('title',$td.text());
				if ($that.options.nowrap==0) {
					$div.addClass("divText divTextellipsis");//.width($td.width());
				} else if ($that.options.nowrap==1) {
					$div.addClass("divText divTextclip");//.width($td.width());
				}
			});
		},
		_setTdAttrStyle: function($tds,isTitle) {
			var $that = this,tdStyle=null;
			if (isTitle)
				tdStyle = this.thStyles;
			else
				tdStyle = this.tdStyles;
			$tds.each( function() {
				var $td = $(this);
				var cellIndex = this.cellIndex;
				$td.attr("cellindex",cellIndex);
				//$td.attr("content",$td.text());
				for (var i = 0;i<$that.styleType.length;i++ ) {
					$td.attr($that.styleType[i],tdStyle[cellIndex][i+1]);
				}
			});
		},
		_getTdAttrStyle: function($tds) {
			var tmpstyle = [];
			var $width = this.tableelement.width();//$tds.parent().width();// this._getElementSize("width");
			for(var i = 0, l = $tds.size(); i < l; i++) {
				var $td = $($tds[i]);
				var style = [], width = $td.innerWidth() - (100 * $td.innerWidth() / $width) -2;
				style[0] = parseInt(width);
				for (var j = 0;j<this.styleType.length;j++ ) {
					style[j+1] = $td.attr(this.styleType[j]);
				}
				tmpstyle[tmpstyle.length] = style;
			}
			return tmpstyle;
		},
		//创建拖动对象
		_createResizeMarkerDiv: function() {
			var $that = this;
			this.resizeMarker = $('<div class="resizeMarker" style="height:'+
			(this.tbody.height()) +'px; left:57px;display:none;"><div class="markerBar"></div></div>');
			this.resizeMarker.mouseover( function(event) {
				$(this).css("cursor","col-resize");
			});
			this.resizeMarker.mousedown( function(event) {
				$(".markerBar",$(this)).css("background-color","scrollbar");
				var opt = $(this).data("ui-draggable").options;
				$(this).data("cellindex",opt.alsoDrag);
			});
			$(document).mouseup( function(event) {
				var $dragobj = $that.resizeMarker;
				
				if ($dragobj.data("clicked")==true) {
					var changWidth = $dragobj.data("movewidth");
					var cellsindex = $dragobj.data("cellindex");
					var $ths = $that.thead.find($that.options.titletype+"[cellindex="+cellsindex+"]");
					var $tds = $that.tbody.find("td[cellindex="+cellsindex+"]");
				
					var targetWidth = $tds.width()+changWidth;
					//$("#btn_test1").val(targetWidth);
					$ths.width(targetWidth);
					$tds.width(targetWidth);
					//$ths.find("div.divText").width(targetWidth);
					//$tds.find("div.divText").width(targetWidth);
					var $table = $that.thead.find("table");
					$table.width($table.width()+changWidth);
					$table = $that.tbody.find("table");
					$table.width($table.width()+changWidth);
					$that._clearCacheData();
				};
			});
		},
		_clearCacheData: function() {
			this.resizeMarker.removeData("movewidth");
			//this.resizeMarker.removeData("startpos");
			this.resizeMarker.removeData("clicked");
			this.resizeMarker.hide();
			$(".markerBar",this.resizeMarker).css("background-color","scrollbar");
		},
		_tdMouseMoveEvent: function(event,$obj) {
			if (this.options.disabled) {
				return ;
			}
			var objwidth = $obj.width(),differ = ( objwidth + parseInt($obj.offset().left.toFixed(0)) ) - event.pageX ;
			var that = this;
			
			if (differ <= 3) {
				that.resizeMarker.position({
					of: $obj,
					my: "left top",
					at: "right-2 top "
				}).height(this.grid.height());
				that.resizeMarker.draggable("option","alsoDrag",$obj.attr("cellindex"));
				that.resizeMarker.show();
			} else {
				$obj.css("cursor", "default");
				if (!that.resizeMarker.data("clicked"))
					that.resizeMarker.hide();
			}
		},
		_checkBoxHandle:function(){
			var $that = this;
			
			if ($that.options.checkBoxElement==null||$that.options.checkBoxElement==undefined)
				return ;
			
			
				var $chk = $(this.options.checkBoxElement,$that.thead);
				var subE = $chk.data("subcheck");
				
				var $subElement = $("input[name='"+subE+"']",$that.tbody);
				
				$that.options.selectArray= new Array($subElement.length);
				
				$chk.bind("click",function(){
					
					var subE = $(this).data("subcheck");
					var $subE = $("input[name='"+subE+"']",$that.tbody);
					$subE.prop('checked', this.checked);
					if (this.checked){
						$subE.each(function(i,obj){
							$that.options.selectArray[i]=$(obj).val();
						});
					}else{
						$that.options.selectArray = new Array($subE.length);
					}
				});
				
				//为元素添加id
				
				$subElement.each(function(i,obj){
					var $obj = $(obj);
					$obj.attr("id",$obj.val());
					$obj.attr("data-order",i);
				});
				
				$subElement.bind("click",function(){
					if (!this.checked)
						$chk.prop("checked",false);
					var unselectNum = $("input[name='"+subE+"']:not(:checked)",$that.tbody).length;
					if (unselectNum ==0)
						$chk.prop("checked",true);
				});
				$subElement.bind('change',function(){
					var eOrder = $(this).data("order");
					if (this.checked){
						$that.options.selectArray[eOrder]=$(this).val();
					}else{
						$that.options.selectArray[eOrder]='';
					}
				});
			
		},
		checkValue:function(){
			var $that = this;
			
			$that.options.checkValue = $.grep($that.options.selectArray,function(obj,i){
				return obj !=null && obj != '' ;
			});	
			
			return $that.options.checkValue;
		},
		_init: function() {
			var $that = this;
			$(window).resize( function() {
				//$that.tbody.scrollLeft(0); 
				$that._adaptHeight();
				$that._adaptWidth();
				$that._divScrollEvent();
			});
			var tablerows = this.tbody.find("tr").length;
			
			//通过行高与行数计算div在初始化时是否存在滚动条
			//当不存在滚动条时，宽度自动适应
			
			if (this.element.attr("id")=="vvvvv"){
				
				this.element.addClass("tetClass");
			}
				
			
			var rowsHeight = tablerows * this.options.lineHeight; 
			if (this.tbody.height() > rowsHeight){
				//var targetw = this.grid.width() - 2; //去除边框宽度
				//this.thead.find("table").width(targetw);
				//this.tbody.find("table").width(targetw)
				$that._adaptWidth();
			}else{
				
			}
		},
		_destroy: function(event, ui) {

		},
		_setOption: function(key, value ) {
			if (key == "disabled") {
				this.resizeMarker.draggable("option","disabled",value);
			}
			this._super( key, value );
		},
		_setOptions: function( options ) {
			this._super( options );
		}
	});

	$.ui.plugin.add("draggable", "alsoDrag", {
		start: function(event, ui) {
			$(this).data("startpos",ui.position.left);
			$(this).data("clicked",true);
		},
		stop: function(event,ui) {
			$(this).data("movewidth",ui.position.left - $(this).data("startpos"));
		},
		drag: function(event,ui) {
			$(this).data("movewidth",ui.position.left - $(this).data("startpos"));
		}
	});
})(jQuery);