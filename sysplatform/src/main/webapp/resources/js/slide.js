var $slide = function(){
	var pic = $('.pic');
	var $picture = $('#picture');
	var $small_pic = $('.small_pic');
	var $li = $('.small_pic li');
	var $span = $('.small_pic span');
	//设置最初显示的图片以及文字
	pic.eq(0).show().addClass("select");
	//pic.eq(0).find("div").animate({ top: 240 }, 300 );
	$(".small_pic ul").css("width",$li.length*96 + 15);
	//设置图片缩放
	pic.eq(0).show().children("img").addClass("ui-img");
	pic.eq(0).show().imageDrag({wheelOffset:1,clickOffset:10});
	//设置自动切换效果
	var scrollTimer;
	var $this = 0;
	time();
	$picture.hover(function(){
		clearInterval(scrollTimer);
	},function(){	
		time();
	}).trigger("mouseover");		//绑定mouseover方法，确保鼠标放在图片上时图片不再定时切换
	
	$small_pic.hover(function(){
		clearInterval(scrollTimer);
	},function(){	
		time();
	}).trigger("mouseover");
	
	$('.next').hover(function(){
		clearInterval(scrollTimer);
	},function(){
		time();
	}).trigger("mouseover");
	
	$('.prev').hover(function(){
		clearInterval(scrollTimer);
	},function(){
		time();
	}).trigger("mouseover");
	
	$('.ui-operator-icon').hover(function(){
		clearInterval(scrollTimer);
	},function(){
		time();
	}).trigger("mouseover");
	
	//调整时间间隔图片切换的方法
	function time() {
		scrollTimer = setInterval(function(){
			//获取当前select类所在位置以确定小图片是否被点击
			var $select = $(".select").index();
			//小图片被点击时执行该if语句
			if ( $select != $this ) {
				$this = $select;
			}
			$this++;
			if( $this == $li.length ) {
				$this = 0;
			}
			//正常执行图片按时切换
			slide( $this );
			
		}, 5000 );
	}
	
	//设置向前按钮
	$('.next').click(function() {
		var num = $('.select').index();
		//因为有8张图片
		if(num == $li.length - 1){
			num = -1;
		}
		slide( num + 1 );
	});
	
	//设置向后按钮
	$('.prev').click(function() {
		var num = $('.select').index();
		if(num == 0){
			num = $li.length ;
		} 
		slide(num - 1);
	});
	
	//初始化边框的位置
	$li.eq(0).addClass("select_pic");
	//给小图片定位
	for(i = 0; i < $li.length; i++)
	{
		var left = i * 96;
		$li.eq(i).css("left", left);
	}
	//小图片被点击后触发的事件
	$li.click(function() {
		var pic_index = $(this).index();
		slide( pic_index );		
	});
	
	//图片切换效果
	function slide(index) {
		
		//记录上次显示图片
		var preindex = pic.eq(index).siblings(".select").index();
		//设置图片缩放效果
		pic.eq( preindex ).imageDrag("destroy");
		pic.eq( index ).show().find("img").not(".ui-operator-icon").addClass("ui-img");
		pic.eq( index ).show().imageDrag({wheelOffset:1,clickOffset:10});
		pic.eq( index ).fadeIn(600).siblings(".pic").find(".ui-img").removeClass("ui-img ui-draggable");
		//设置大图片的效果
		pic.eq( index ).fadeIn(600).addClass("select").siblings().hide().removeClass("select");
		
		//设置文字的动态效果
		//pic.eq( index ).find("div").animate({ top: 240 }, 300 ).parent().siblings().find("div").css("top", "320px");
		//设置小图片的边框效果及位置
		$li.eq( index ).addClass("select_pic").siblings().removeClass("select_pic");
		$span.css("left", (index)*96+43 );
		
	}
	// //如果页面加载完成，隐藏wait类
	// if( document.readyState = "completed" ) {
		// $('.wait').hide();
	// }
};