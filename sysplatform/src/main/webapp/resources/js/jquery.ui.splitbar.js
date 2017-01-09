/*!
 * jQuery UI splitbar 1.10.3
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/draggable/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 *  jquery.ui.draggable
 */
(function( $, undefined ) {

	$.widget("ui.splitbar", {
		version: "1.10.3",
		widgetEventPrefix: "drag",
		options: {
			first:"", //第一个元素.selector
			sec:"",  //第二个元素.selector
			minsize:20, //first element minimum width/height in PX of  div.
			maxsize:0, //first element maximum width/height  in PX of  div.
			direction:"Y", //Horizontal Y(水平分隔条)  Vertical X（垂直分隔条）
			workingClass: 'working',
			invertClass: 'invert',
			uidefaultstate:"ui-state-default",
			uistateactive:"ui-state-active",
			splitbarsize:8,
			dragStop:null
			//isClose:false, 未定义，定义时数值取0到100的百分比，1表示第一个元素隐藏，100表示第二个元素
		},

		_create: function() {
			this._initSpliterWidget();
			//this.element.draggable(this.options);
		},
		_initSpliterWidget: function() {
			$.extend(this.options, {
				Y: {
					moving:"top",
					sizing: "height",
					eventPos: "pageY",
					splitbarClass:"ui-spliter-hbar",
					buttonClass: "ui-spliter-hbt",
					cursor: "n-resize",
					firbordersize:$(this.options.first).outerHeight() - $(this.options.first).innerHeight(),
					firpaddingsize:$(this.options.first).innerHeight() - $(this.options.first).height(),
					secbordersize:$(this.options.sec).outerHeight() - $(this.options.sec).innerHeight(),
					secpaddingsize:$(this.options.sec).innerHeight() - $(this.options.sec).height(),
					bordersize:$(this.options.first).outerHeight() - $(this.options.first).innerHeight() +	
						$(this.options.sec).outerHeight() - $(this.options.sec).innerHeight(),
					paddingsize:$(this.options.first).innerHeight() - $(this.options.first).height() +	
						$(this.options.sec).innerHeight() - $(this.options.sec).height()
				},
				X: {
					moving:"left",
					sizing: "width",
					eventPos: "pageX",
					splitbarClass:"ui-spliter-vbar",
					buttonClass: "ui-spliter-vbt",
					cursor: "e-resize",
					firbordersize:$(this.options.first).outerWidth() - $(this.options.first).innerWidth(),
					firpaddingsize:$(this.options.first).innerWidth() - $(this.options.first).width(),
					secbordersize:$(this.options.sec).outerWidth() - $(this.options.sec).innerWidth(),
					secpaddingsize:$(this.options.sec).innerWidth() - $(this.options.sec).width(),
					bordersize:$(this.options.first).outerWidth() - $(this.options.first).innerWidth() +	
						$(this.options.sec).outerWidth() - $(this.options.sec).innerWidth(),
					paddingsize:$(this.options.first).innerWidth() - $(this.options.first).width() +	
						$(this.options.sec).innerWidth() - $(this.options.sec).width()
				}
			}[this.options.direction]);

			var o = this.options;
			var conSize = this.element[o.sizing]();
			this.firstElement  =  new this.changeElement($(o.first),o);
			this.secondElement = new this.changeElement($(o.sec),o,conSize);
			this.splitBar = this._createSplitBar();
			//this._hoverable(this.splitBar);
			this.splitBarPos = (this.splitBar.position()[this.options.moving]/conSize * 100).toFixed(4);
		},
		_createSplitBar: function() {
			var spliter = this;
			var $splitbar=$('<div><span></span></div>');
			var firstEle = this.firstElement.element;
			if (this.options.isClose !=undefined ) {
				var $closeB = this._createCloseButton();
				$splitbar.append($closeB);
			}
			$splitbar.addClass(this.options.splitbarClass);
			
			$splitbar.hover( function() {
				$(this).addClass(spliter.options.uistateactive);
			}, function() {
				$(this).removeClass(spliter.options.uistateactive);
			});
			if (!this.options.disabled)
					$splitbar.css("cursor",this.options.cursor);
			var eleSize = firstEle[this.options.sizing](); //调整第一个元素的尺寸
			
			firstEle[this.options.sizing](eleSize - this.options.splitbarsize - 2); //2为边框尺寸
			firstEle.after($splitbar);
			$.extend(this.options, {
				axis:this.options.direction.toLowerCase(),
				containment:spliter.element,
				revert:false,
				helper:"clone",
				revertDuration:0,
				opacity:0.8,
				scroll: false,
				start: function(event,ui) {
					spliter._start(event,ui);
				},
				stop: function(event,ui) {
					spliter._stop(event,ui);
				},
				drag: function(event,ui) {
					spliter._drag(event,ui);
				}
			});
			$splitbar.draggable(this.options);
			return $splitbar;
		},
		_createCloseButton: function() {
			var spliter = this;
			var $closeB = $("<div></div>").css("cursor",'pointer');;
			$closeB.attr({
				"class": this.options.buttonClass,
				closeable: true
			});
			$closeB.hover( function() {
				$(this).addClass(spliter.options.workingClass);
			}, function() {
				$(this).removeClass(spliter.options.workingClass);
			});
			$closeB.mouseup( function(e) {
				if(e.target != this)
					return ;
				spliter._closeButtonClick(e,$closeB);
			});
			return $closeB;
		},
		_closeButtonClick: function(event,ele) {
			var elesize = 0,opts = this.options;
			var conSize = this.element[opts.sizing]() - opts.splitbarsize - opts.bordersize - opts.paddingsize;
			if (ele.attr("closeable")=="true") {
				ele.data("sizing",this.firstElement.element[opts.sizing]());
				elesize = ( conSize * opts.isClose /100).toFixed(0);
				ele.attr("closeable",false);
			} else {
				ele.attr("closeable",true);
				elesize = ele.data("sizing");
			}
			this.firstElement.setEnableSize(elesize);
			this.secondElement.setEnableSize(conSize - elesize);
			this._exeSplit();
		},
		_drag: function(event, ui) {
			this.Pos[1] = event[this.options.eventPos];
			this.Pos[2] = this.Pos[1] - this.Pos[0];
			this.firstElement.setMovSize(this.Pos[2],this.options);
			this.secondElement.setMovSize(this.Pos[2],this.options,true);
		},
		_destroy: function(event, ui) {

		},
		_start: function(event, ui) {
			//$("#btn_test").val(ui.helper.parent().attr("class"));
			this.Pos[0] = event[this.options.eventPos];
		},
		_stop: function(event, ui) {
			this._exeSplit();
			this.options.dragStop();
		},
		_exeSplit: function() {
			var $spliter = this;
			var anob1 = this.firstElement.getAnimate(this.options);
			var anob2 = this.secondElement.getAnimate(this.options);
			this.secondElement.element.animate(anob2, 200 );
			this.firstElement.element.animate(anob1, 200,"swing", function() {
				var conSize = $spliter.element[$spliter.options.sizing]();
				$spliter.splitBarPos = ($spliter.splitBar.position()[$spliter.options.moving]/conSize * 100).toFixed(4);
			} );
		},
		_init: function() {
			this.Pos = [0,0,0];
			var spliter = this;
			$(window).resize( function() {
				spliter._initLayout();
			});
			var opts = this.options;
			var conSize = this.element[opts.sizing]();
			if (opts.maxsize == 0 ) {
				this.firstElement.setMaxSize(conSize - opts.splitbarsize - opts.bordersize - opts.paddingsize);
				this.secondElement.setMaxSize(conSize - opts.splitbarsize - opts.bordersize - opts.paddingsize);
			}
			if (opts.minsize == 0) {
				this.firstElement.setMinSize(0);
				this.secondElement.setMinSize(0);
			}
		},
		_initLayout: function() {
			var opts = this.options;
			var conSize = this.element[opts.sizing]();
			var bordersize = opts.bordersize;
			var paddingsize = opts.paddingsize;
			this.firstElement.element[opts.sizing]((conSize  * this.splitBarPos/100).toFixed(0) -  opts.firbordersize - opts.firpaddingsize);
			var elesizing= conSize - this.firstElement.element[opts.sizing]() - opts.splitbarsize -paddingsize - bordersize - 2;
			this.secondElement.element[opts.sizing](elesizing);
		},
		changeElement: function(e,opts,conSize) {
			this.element = e;
			if (conSize) {
				this.maxsize = conSize - opts.minsize - opts.splitbarsize - opts.bordersize - opts.paddingsize   ;
				this.minsize = conSize - opts.maxsize - opts.splitbarsize - opts.bordersize  - opts.paddingsize ;
			} else {
				this.maxsize= opts.maxsize;
				this.minsize= opts.minsize;
			};
			this.setMaxSize= function(maxSize) {
				this.maxsize = maxSize;
			};
			this.setMinSize= function(minSize) { 
				this.minsize = minSize;
			};
			this.setMovSize= function(movsize,opts,order) { //order表明元素顺序
				var eleSize = this.element[opts.sizing]();
				if (order)
					this.enableSize = eleSize - movsize;
				else
					this.enableSize = eleSize + movsize;

				if (movsize >= 0) {
					if (order)
						this.enableSize = Math.max(this.minsize,this.enableSize);
					else
						this.enableSize = Math.min(this.maxsize,this.enableSize);
				} else {
					if (order){

						this.enableSize = Math.min(this.maxsize,this.enableSize);
						
					}
					else
						this.enableSize = Math.max(this.minsize,this.enableSize);
				}
				return this.enableSize;
			};
			this.setEnableSize = function(size) {
				this.enableSize = size;
			};
			this.getAnimate= function(opts) {
				var animate = {};
				animate[opts.sizing] = this.enableSize;
				return animate;
			};
		}
	});

})(jQuery);