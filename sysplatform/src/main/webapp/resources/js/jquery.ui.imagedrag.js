(function($,undefined){
	$.widget("ui.imageDrag",{
		version: "1.0",
		widgetEventPrefix: "imageDrag",
		options: {
			wheelOffset:200, //鼠标每次滚动
			moveDistance:50,//移动距离
			clickOffset: 10 //缩放按钮
		},
		_create: function(){
			this.$dragObj = this.element.find(".ui-img");//当前拖动对象
			//获取图片的原始大小，兼容google浏览器
			
			var img = new Image();
			var $that = this;
			img.onload = function(){
				$that.height = img.height;
				$that.width = img.width;
			},
			img.src = this.$dragObj.attr("src");
			
			this._createOperDiv();
			this._initImgDiv();
			this._on(this._events);
		},
		
		_init: function(){
			this.element.bind("contextmenu",function(){
				return false;
			});
			this.$dragObj.draggable();
		},
		_destroy:function(){
			$("div.ui-icon-operator-div").remove();
		},
		//初始化图片操作按钮区域
		_createOperDiv: function(){
			var $oprTable = $("<table  border='0' cellspacing='2' cellpadding ='0' style='width:65px; height:100px'></table>");
			var $oprDiv = $("<div></div>").addClass("ui-icon-operator-div");
			
			var img_up = $("<img prefix='' alt='up' src='css/images/up.gif' title='向上'/>").addClass("ui-operator-icon");
			var img_left = $("<img prefix='' alt='left' src='css/images/left.gif' title='向左'/>").addClass("ui-operator-icon");
			var img_zoom = $("<img prefix='' alt='i_zoom' src='css/images/zoom.gif' title='还原'/>").addClass("ui-operator-icon");
			var img_right = $("<img prefix='' alt='right' src='css/images/right.gif' title='向右'/>").addClass("ui-operator-icon");
			var img_down = $("<img prefix='' alt='down' src='css/images/down.gif' title='向下'/>").addClass("ui-operator-icon");
			var img_in = $("<img prefix='' alt='big' src='css/images/zoom_in.gif' title='放大'/>").addClass("ui-operator-icon");
			var img_out = $("<img prefix='' alt='small' src='css/images/zoom_out.gif' title='缩小'/>").addClass("ui-operator-icon");
			
			var $tr_1 = $("<tr><td></td><td></td><td></td></tr>");
			$tr_1.find("td").eq(1).append(img_up);
			
			var $tr_2 = $("<tr><td></td><td></td><td></td></tr>");
			$tr_2.find("td").eq(0).append(img_left);
			$tr_2.find("td").eq(1).append(img_zoom);
			$tr_2.find("td").eq(2).append(img_right);
			 
			var $tr_3 = $("<tr><td></td><td></td><td></td></tr>");
			$tr_3.find("td").eq(1).append(img_down);
			
			var $tr_4 = $("<tr><td></td><td></td><td></td></tr>");
			$tr_4.find("td").eq(1).append(img_in);
			
			var $tr_5 = $("<tr><td></td><td></td><td></td></tr>");
			$tr_5.find("td").eq(1).append(img_out);
			
			$oprTable.append($tr_1);
			$oprTable.append($tr_2);
			$oprTable.append($tr_3);
			$oprTable.append($tr_4);
			$oprTable.append($tr_5);
			$oprDiv.append($oprTable);
			
			this.element.parent().parent().append($oprDiv);
			
			$this = this;
			
			//为图片按钮绑定相应方法
			$("img[prefix]").bind("click",function(){
				$this._clickMove(this.alt);
			});
			$oprDiv.position({
				of: this.element.parent(),
				my: "left top",
				at: "left+5 top+5",
				collision: "none"
			});	
		},
		_initImgDiv: function(){
			//this.$imgDiv = this.$dragObj.wrap("<div></div>").parent().addClass("ui-img-div");
		},
		
		//图片的移动、缩放方法
		_clickMove: function(clickType){
			
			//获取匹配元素相对父元素的偏移
			
			var $top_ = this.$dragObj.position().top;
			var $left_ = this.$dragObj.position().left;
			//移动按钮：偏移量
			var $moveDistance_ = this.options.moveDistance;
			
			var $currentHeight_ = this.$dragObj.css("height");	
			var $currentwidth_ = this.$dragObj.css("width");
			
			//缩放按钮：偏移量
			var $clickOffset_ = this.options.clickOffset;
			//缩放比例
			var $clickRatio_ ;
			if(this.height > this.width){
				$clickRatio_ = (this.height / this.width) * $clickOffset_;
			}else{
				$clickRatio_ = (this.width / this.height) * $clickOffset_;
			}
			if(clickType == 'up'){
				this.$dragObj.css("top",($top_ - $moveDistance_) + "px");
			}else if(clickType == 'down'){
				this.$dragObj.css("top",($top_ + $moveDistance_) + "px");
			}else if(clickType == 'left'){
				this.$dragObj.css("left",($left_ - $moveDistance_) + "px");
			}else if(clickType == 'right'){
				this.$dragObj.css("left",($left_ + $moveDistance_) + "px");
			}else if(clickType == 'i_zoom'){
				this.$dragObj.height(this.height);
				this.$dragObj.width(this.width);
				this.$dragObj.css("top",0);
				this.$dragObj.css("left",0);
			}else if(clickType == 'small'){
				if(this.height > this.width){
					this.$dragObj.width(parseInt($currentwidth_) - $clickOffset_);
					this.$dragObj.height(parseInt($currentHeight_) - $clickRatio_);
				}else{
					this.$dragObj.width(parseInt($currentwidth_) - $clickRatio_);
					this.$dragObj.height(parseInt($currentHeight_) - $clickOffset_);
				}
			}else if(clickType == 'big'){
				if(this.height > this.width){
					this.$dragObj.width(parseInt($currentwidth_) + $clickOffset_);
					this.$dragObj.height(parseInt($currentHeight_) + $clickRatio_);
				}else{
					this.$dragObj.width(parseInt($currentwidth_) + $clickRatio_);
					this.$dragObj.height(parseInt($currentHeight_) + $clickOffset_);
				}
			}
		},
		
		_events: {
			mousewheel: function(event,delta){
				var $wheelRatio_ ;
				var $wheeloffset_ = this.options.wheelOffset;
				var flag_ = false;
				if(this.height > this.width){
					$wheelRatio_ = (this.height / this.width) * $wheeloffset_;
					flag_ = true;
				}else{
					$wheelRatio_ = (this.width / this.height) * $wheeloffset_;
				}
				//滚动鼠标时，必须是指定对象时，能进行缩放操作
				//var $mouseWheelObj = $("#"+event.currentTarget.id).find("img.ui-img");
				var $mouseWheelObj = $(event.currentTarget).find("img.ui-img");
				//高度大于宽度
				if(flag_){
					$mouseWheelObj.width($mouseWheelObj.width() + delta * $wheeloffset_) ;
					$mouseWheelObj.height($mouseWheelObj.height() + delta * $wheelRatio_) ;
				}else{
					$mouseWheelObj.width($mouseWheelObj.width() + delta * $wheelRatio_) ;
					$mouseWheelObj.height($mouseWheelObj.height() + delta * $wheeloffset_) ;
				}
				
			}
		}
	});
})(jQuery);
