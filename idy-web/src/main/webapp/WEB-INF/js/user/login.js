/**
 * for user 
 */
$(function(){
	
});

function loginBtnClick(url){
	//非空  长度 合法性。。支持域账号登陆
	if($('#userName').val() == '') {
		$('.error').text("用户名不能空！");
		return;
	}
	if($('#userName').val().length > 32) {
		$('.error').text("用户名长度在32字符以内");
		return;
	}
	if($('#password').val() == '') {
		$('.error').text("密码不能空！");
		return;
	}
	if($('#password').val().length < 6) {
		$('.error').text("密码长度至少为6位字符");
		return;
	}
	if($('#password').val().length > 32) {
		$('.error').text("密码长度在32字符以内");
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "/auth/doLogin",
		data:{userName:$('#userName').val(),password:$('#password').val()},
		success : function(json) {
			if(json == "0"){
				//$('.error').text('');
				window.location.href = url;//
			}else{
				//alert(json);
				$('.error').text('' + json);
				return;
			}
		},
	    error:function(r){
	    	console.info("加载数据失败！！");
	    }
	});
}

