/* --------------------------------------------------------
    Login + Sign up
   -----------------------------------------------------------*/
(function() {
	$('body').on('click touchstart', '.box-switcher', function(e) {
		e.preventDefault();
		var box = $(this).attr('data-switch');
		$(this).closest('.box').toggleClass('active');
		$('#' + box).closest('.box').addClass('active');
	});

	$("#test_val").bind("click",function(){
		alert();

	});
	
	
	$('.center-block').on('click touchstart', '.btn', function(e) {
		e.preventDefault();
		var btn = $(this).attr('data-loginbtn');
		switch (btn) {
		case 'login':
			var $data = $("#box-login").serializeArray();
			var validateResult  = $('#box-login').validationEngine('validate');
			if (validateResult){
				$.ajax({
					type : "POST",
					url : $.SystemApp.contextPath+"/checkUserPassword",
					data : $data,
					success : function(msg) {
						if (msg.isSuccess == false) {
							alert("用户名或密码不正确！")
						} else {
							window.location = $.SystemApp.contextPath+"/loginSuccess";
						}
					}
				});
			}
			break;
		case 'register':

			break;
		case 'resetpwd':

			break;
		}
	});
	
	$.SystemApp.initComponents();	
})();