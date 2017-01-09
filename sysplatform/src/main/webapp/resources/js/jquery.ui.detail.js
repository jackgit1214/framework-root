/*!
 * jQuery UI detail
 * http://jqueryui.com
 *
 * Copyright 2014 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 * 打开验证时需要jquery validation支持
 * 
 *
 * Depends:
 */
 
 $.extend( $.validator, {
			messages: {
			    required: "必填!",
			    remote: "请修改这个字段.",
			    email: "邮箱非法,请确认",
			    url: "URL地址格式错误，请确认",
			    date: "日期格式错误，请确认",
			    dateISO: "日期格式(ISO)错误，请确认.",
			    number: "请输入数字",
			    digits: "请输入整数",
			    creditcard: "信用卡号码格式错误",
			    equalTo: "两次输入不一致",
					maxlength: $.validator.format("最多输入 {0} 个字符"),
					minlength: $.validator.format("最少输入 {0} 个字符"),
					rangelength: $.validator.format("字符长度介于 {0} 到 {1} 之间"),
					range: $.validator.format("值大小介于 {0} 到 {1}之间"),
					max: $.validator.format("最大值不超过 {0}"),
					min: $.validator.format("最小值不小于 {0}")					
			}
	}
);
 $.validator.addMethod("isMobile", function(value, element) {
		var length = value.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "填写不正确！");
 
 $.validator.addMethod("isZipCode", function(value, element) {   
	    var tel = /^[0-9]{6}$/;
	    return this.optional(element) || (tel.test(value));
}, "填写不正确！");
 
(function( $, undefined ) {
	$.widget("ui.detail", {
		version: "1.0",
		widgetEventPrefix: "detail",
		options: {
			labelElement : "td",
			inputElement : "input,textarea,select",
			inputDefaultClass :"inputField",
			inputFocusC:"field_on",
			inputFocusOutC:"field_off",
			defaultClass:"detail",
			isShowInputTip:true,
			isPopError:true,
			lineHeight:30,
			isValidate:true,
			handleForm:"#roleform",
			focusValidate:false,
			inputReadonly:false
		},
		
		_create: function() {
				
				var $that = this;
				this.element.addClass(this.options.defaultClass);
				$that._handleLabel();
				$that._handleForm();
				$that._handleInput();
				//是否验证
				if ($that.options.isValidate){
						$that._handleValidate();
				};
				
		},
		
		_handleLabel:function(){
				var $that = this;
				var $table = $("table[data-detail!=false]",this.element);
				
				var $labelElement = $(this.options.labelElement,$table);
				$labelElement.each(function(i,obj){
					var $obj = $(obj);
					 var tdindex = $obj.prop("cellIndex");

					 //默认单列为标签文本列
					 if (tdindex%2==0){
					 		var $label = $obj.find("label");
					 		$obj.css("text-align","right");
					 		if ($label.length >0 ){
					 			$label.attr("for",$obj.data("target"));
					 		}else {
					 			var $tmp = $("<label>");
					 			$tmp.append($obj.html());
					 			$tmp.prop("for",$obj.data("target"));
					 			$obj.empty();
					 			$obj.append($tmp);
					 		};
					 };
				});
		},
		
		_handleForm:function(){
			var $that = this;
			var $table = $that.element.find("table[data-detail!=false]");
			//$table.css("width","100%")
			//			.css("height","100%");
			$table.find("tr").css("height",$that.options.lineHeight);
		},
		_handleInput:function(){
			var $that = this;
			var $table = $that.element.find("table[data-detail!=false]");
		  var $inputElement = $(this.options.inputElement,$table);
		  function textFill(obj){ 
			     var originalvalue = obj.val();
			     obj.focus( function(){
			          if( $.trim(obj.val()) == originalvalue ){ obj.val('');}
			     });
			     obj.blur( function(){
			          if( $.trim(obj.val()) == '' ){ 
			        	  	obj.val(originalvalue); 
			          	}
			     });
			};		
		  $inputElement.each(function(i,obj){
					var $obj = $(obj);
					//$obj.val();
					//textFill($obj); html5自带此功能  添加placeholder属性
					if ($that.options.isShowInputTip){
						 if ( $obj.data("rule-required")||$obj.attr("required")=="required"||$obj.prop("required")=="required"  )
						  	$obj.after("<span style='color:red;padding:0px 5px;'>*</span>");
						 
					};
					if ($that.options.inputReadonly){
						$obj.prop("readonly",true);
						$obj.prop("disabled",true);
					}else{
						$that._handleFocus(obj);
					};
		 	 });
		 },
		
		//设置得失焦点 事件
		_handleFocus:function(obj){
				var $that = this;
	   		 $(obj).addClass($that.options.inputDefaultClass)
		   		 			 .addClass($that.options.inputFocusOutC);
		   		 $(obj).bind( "focus" + $that.eventNamespace, function() {
							 $(this).removeClass($that.options.inputFocusOutC)
							 				 .addClass($that.options.inputFocusC);
						})
						.bind( "blur" + $that.eventNamespace, function() {
								$(this).removeClass($that.options.inputFocusC)
							 				 .addClass($that.options.inputFocusOutC);
						});
					
		},
		//处理验证
		_handleValidate:function(){
			
			var $that = this;
			var $form = $($that.options.handleForm);
			//显示提示方式
			if ($that.options.isPopError){
					$.validator.setDefaults({
								showErrors:function(errorMap, errorList){
										var msg = "";  
										var $firstObj = null;
				            $.each( errorList, function(i,v){ 
				            	var $obj = $(v.element);
				            	if (i==0)
				            		$firstObj = $obj;
				            	
				              var msg_label = "";	
				              if ($obj.data('label')!=undefined)	
				            	  msg_label = $obj.data('label');
				            	  msg += (msg_label+v.message+"\n");  
				              
				            	  
				            });  
				            if(msg!="") {
				            	alert(msg);
				            	$firstObj.focus();
				            };  
								},
								onkeyup:false,
								onfocusout:false
						});
			}

		},
		checkValidation:function(){
			var $that = this;
			var $form = $($that.options.handleForm);
			
			return $form.valid();
		},
		
		form_submit:function(url,target){
			
		},
		
		_init: function() {
			var $that = this;

			if ($that.options.focusValidate){
				var $form = $($that.options.handleForm);
				
				$that.options.validator = $form.validate({
						onfocusout: function(element){
							$(element).valid();
				    }
				})
			};
		},
		
		_destroy: function(event, ui) {

		},
		_setOption: function(key, value ) {
			this._super( key, value );
		}
	});
})(jQuery);
