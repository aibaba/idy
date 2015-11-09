var basePath = "/auth";

//选择部门后的动作
$('#departmentSelect').change(function(){
	/* 	重置“部门职位”候选项，
		重置“所属团队”候选项，
		“团队身份”“所属二级团队”“二级团队身份”置为未选择！
		重置角色权限表为空！
	 */
	//获取部门职位
	var departmentId = $(this).children('option:selected').val();
	if(departmentId != 0){
		$.ajax({
			type : "POST",
			url : basePath+"/position/getByDepartmentId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"departmentId":departmentId},
			success : function(res) {
				res = eval(res);
				if(res.err==0){
					$("#positionSelect").empty();
					$("#positionSelect").append("<option value='0'>未选择</option>");
					$.each(res.data, function(index, content)
				    {
						$("#positionSelect").append("<option value='"+content.id+"'>"+content.name+"</option>");
				    });
				}else{
					alert("获取职位失败！");
				}
			}
		});
		
		//获取部门团队
		$.ajax({
			type : "POST",
			url : basePath+"/team/getByDepartmentId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"departmentId":$(this).children('option:selected').val()},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					$("#teamSelect").empty();
					$("#teamSelect").append("<option value='0'>未选择</option>");
					$.each(res.data, function(index, content)
				    {
						$("#teamSelect").append("<option value='"+content.id+"'>"+content.name+"</option>");
				    });
				}else{
					alert("获取团队失败！");
				}
			}
		});
		
		$("#identitySelect").empty();
		$("#identitySelect").append("<option value='0'>未选择</option>");
		$("#subTeamSelect").empty();
		$("#subTeamSelect").append("<option value='0'>未选择</option>");
		$("#subIdentitySelect").empty();
		$("#subIdentitySelect").append("<option value='0'>未选择</option>");
		
		//重置角色权限表为空
		//设置当前项为“部门角色”
//		setTable(1,null);
		
	}else{
		//departmentId == 0,重置所有下拉选项
		$("#positionSelect").empty();
		$("#positionSelect").append("<option value='0'>未选择</option>");
		$("#teamSelect").empty();
		$("#teamSelect").append("<option value='0'>未选择</option>");
		$("#identitySelect").empty();
		$("#identitySelect").append("<option value='0'>未选择</option>");
		$("#subTeamSelect").empty();
		$("#subTeamSelect").append("<option value='0'>未选择</option>");
		$("#subIdentitySelect").empty();
		$("#subIdentitySelect").append("<option value='0'>未选择</option>");
		
		//表格为空
		setTable(0,null);
	}
	
});

//选择职位后的动作
$("#positionSelect").change(function(){
	changePosition();
});
function changePosition(){
	/*在角色权限表中，7处为“部门角色”，列表中显示此职位对应的角色权限！*/
	var positionId = $("#positionSelect").children('option:selected').val();

	if(positionId != 0){
		//获取职位的角色列表
		$.ajax({
			type : "POST",
			url : basePath+"/role/getByPositionId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"positionId":positionId},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					//设置当前项为“部门角色”
					setTable(1,res.data);
				}else{
					alert("获取角色失败！");
				}
			}
		});	
	}else{
		//未选择职位，显示角色权限表为空
		//设置当前项为“部门角色”
		setTable(0,null);
	}
}


//选择团队后的动作
$("#teamSelect").change(function(){
	var teamId = $(this).children('option:selected').val();
	
	if(teamId != 0){
		//获取团队的身份列表
		$.ajax({
			type : "POST",
			url : basePath+"/identity/getByTeamId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"teamId":teamId},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					//设置“团队身份”下拉框
					$("#identitySelect").empty();
					$("#identitySelect").append("<option value='0'>未选择</option>");
					$.each(res.data, function(index, content)
				    {
						$("#identitySelect").append("<option value='"+content.id+"'>"+content.name+"</option>");
				    });
				}else{
					alert("获取身份失败！");
				}
			}
		});	
		
		//获取团队的二级团队列表
		$.ajax({
			type : "POST",
			url : basePath+"/team/getSubByTeamId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"teamId":teamId},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					//设置“二级团队”下拉框
					$("#subTeamSelect").empty();
					$("#subTeamSelect").append("<option value='0'>未选择</option>");
					$.each(res.data, function(index, content)
				    {
						$("#subTeamSelect").append("<option value='"+content.id+"'>"+content.name+"</option>");
				    });
				}else{
					alert("获取二级团队失败！");
				}
			}
		});	
	}else{
		//团队置为未选择，设置此三项为空
		$("#identitySelect").empty();
		$("#identitySelect").append("<option value='0'>未选择</option>");
		$("#subTeamSelect").empty();
		$("#subTeamSelect").append("<option value='0'>未选择</option>");
		$("#subIdentitySelect").empty();
		$("#subIdentitySelect").append("<option value='0'>未选择</option>");
		
		//设置表格为显示部门角色（注意，此时要让setTable自己获取要显示的部门）
		setTable(1,null);
	}
});

//选择身份后的动作
$("#identitySelect").change(function(){
	changeIdentity();
});
function changeIdentity(){
	var identityId = $("#identitySelect").children('option:selected').val();

	if(identityId != 0){
		//获取身份的角色列表
		$.ajax({
			type : "POST",
			url : basePath+"/role/getByIdentityId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"identityId":identityId},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					//设置当前项为“团队角色”
					setTable(2,res.data);
				}else{
					alert("获取角色失败！");
				}
			}
		});	
	}else{
		//未选择职位，显示角色权限表为空
		//设置当前项为“部门角色”
		setTable(1,null);
	}
}

//选择二级团队后的动作
$("#subTeamSelect").change(function(){
	var subTeamId = $(this).children('option:selected').val();
	
	if(subTeamId != null && subTeamId != 0){
		//获取团队的身份列表
		$.ajax({
			type : "POST",
			url : basePath+"/identity/getByTeamId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"teamId":subTeamId},
			success : function(res) {
				res = eval(res);
				if(res.err==1){
					//设置“团队身份”下拉框
					$("#subIdentitySelect").empty();
					$("#subIdentitySelect").append("<option value='0'>未选择</option>");
					$.each(res.data, function(index, content)
				    {
						$("#subIdentitySelect").append("<option value='"+content.id+"'>"+content.name+"</option>");
				    });
				}else{
					alert("获取身份失败！");
				}
			}
		});	
		
	}else{
		//团队置为未选择，设置此三项为空
		$("#subIdentitySelect").empty();
		$("#subIdentitySelect").append("<option value='0'>未选择</option>");
		
		//设置表格为显示部门角色（注意，此时要让setTable自己获取要显示的部门）
//		setTable(1,null);
	}
});

$("#subIdentitySelect").change(function(){
	changeSubIdentity();
});
function changeSubIdentity(){
	//使表格显示一、二级团队的角色
	var subIdentityId = $("#subIdentitySelect").children('option:selected').val();
	
	if(subIdentityId != 0){
		setTable(2,null);
	}else{
		//未选择职位，显示角色权限表为空
		//设置当前项为“部门角色”
		setTable(1,null);
	}
}



//重置role时的动作
$("#role").change(function(){
	var id = $(this).children('option:selected').val();
	if(id != null){
		setTable(id,null);
	}
});


//重置role下拉框选项，并设置默认值
function setTable(num, data){//data为json列表
	//设置选项
	//num=1表示部门角色，num=2表示团队角色，num=3表示用户角色
	$("#role").empty();
	var options = "<option value='1'>部门角色</option>";
	if($("#identitySelect").children('option:selected').val() != 0){
		options += "<option value='2'>团队角色</option>";
	}
	options += "<option value='3'>用户角色</option>";
	$("#role").append(options);
	//设置当前值
	if(num == 2){
		$("#role").get(0).selectedIndex = 1;
	}else if(num == 3){
		if($("#identitySelect").children('option:selected').val() != 0){
			$("#role").get(0).selectedIndex = 2;
		}else{
			$("#role").get(0).selectedIndex = 1;
		}
	}else{
		$("#role").get(0).selectedIndex = 0;
	}
	
	//清空表内容
	$('#roleTable tr:not(:eq(0))').remove();
	
	//更新表内容
	var departmentName = $("#departmentSelect").children('option:selected').text();
	var teamName = $("#teamSelect").children('option:selected').text();
	var subTeamName = $("#subTeamSelect").children('option:selected').text();
	
	if(num == 0){//表示要清空表格
		$("#role").empty();
		$("#role").append("<option value='1'>部门角色</option><option value='3'>用户角色</option>");
		$('#roleTable tr:not(:eq(0))').remove();
	}else if(num == 1){
		if(data != null){
			$.each(data, function(index, content){
				var newRow = "<tr><td>"+content.name+"</td><td>部门角色("+departmentName+")</td>" +
						"<td><a class=\"color-red\" href=\"\">删除</a></td>" +
						"</tr>";
				$("#roleTable tr:last").after(newRow);
		    });
		}else{
			//自己去根据部门id查询
			changePosition();
		}
	}else if(num == 2){
		//自己根据父子团队id查询显示
		var identityId = $("#identitySelect").children('option:selected').val();
		var subIdentityId = $("#subIdentitySelect").children('option:selected').val();
		if(identityId != 0){
			$.ajax({
				type : "POST",
				url : basePath+"/role/getByIdentityId",
				dataType:"json",//表明返回参数的类型，这样才能解析好
				data:{"identityId":identityId},
				success : function(res) {
					res = eval(res);
					if(res.err==1){
						$.each(res.data, function(index, content){
							var newRow = "<tr><td>"+content.name+"</td><td>团队角色("+teamName+")</td>" +
									"<td><a class=\"color-blue\" href=\"\">查看</a><span class=\"space color-blue\">|</span><a class=\"color-red\" href=\"\">删除</a></td>" +
									"</tr>";
							$("#roleTable tr:last").after(newRow);
					    });
					}else{
						alert("获取团队身份失败！");
					}
				}
			});	
		}
		
		if(subIdentityId != 0){
			$.ajax({
				type : "POST",
				url : basePath+"/role/getByIdentityId",
				dataType:"json",//表明返回参数的类型，这样才能解析好
				data:{"identityId":subIdentityId},
				success : function(res) {
					res = eval(res);
					if(res.err==1){
						$.each(res.data, function(index, content){
							var newRow = "<tr><td>"+content.name+"</td><td>二级团队角色("+subTeamName+")</td>" +
									"<td><a class=\"color-blue\" href=\"\">查看</a><span class=\"space color-blue\">|</span><a class=\"color-red\" href=\"\">删除</a></td>" +
									"</tr>";
							$("#roleTable tr:last").after(newRow);
					    });
					}else{
						alert("获取团队身份失败！");
					}
				}
			});	
		}
	}else if(num == 3){
		if(data != null){
			$.each(data, function(index, content){
				var newRow = "<tr><td>"+content.name+"</td><td>用户角色("+departmentName+")</td>" +
						"<td><a class=\"color-blue\" href=\"\">查看</a><span class=\"space color-blue\">|</span><a class=\"color-red\" href=\"javascript:void(0)\" onclick=\"delUserRole("+content.id+")\">删除</a></td>" +
						"</tr>";
				$("#roleTable tr:last").after(newRow);
		    });
		}else{
			addUserAndRole();
		}
	}
}

//删除用户--角色
function delUserRole(id){
	if(id != null){
		//从选择角色的弹窗中取消勾选
		$('input[name="userRoleName"]:checked').each(function(){    
			if($(this).attr("id") == ("rid-"+id)){
				$(this).removeAttr("checked");
			}
		});
		setTable(3,null);
	}
}

$('#updateForm').click(function(){
	//验证
	var userId = $('#userId').val();
	var userName = $('#userName').val();
	var password = $('#password').val(); 
	var confirm = $('#confirm').val(); 
	var statusSelect = $('#statusSelect').val(); 
	var entryTime = $('#entryTime').datebox('getValue');
	
	
	//用户角色的ids
	var userRoleIds = "";
	$('input[name="userRoleName"]:checked').each(function(){    
		userRoleIds += $(this).attr("id").replace("rid-","") + ",";
	});
	
	//提交
	$.ajax({
		type : "POST",
		url : basePath+"/user/doUpdate",
		data:{"id":userId,"userName":userName,"password":password,"confirm":confirm,
			"status":statusSelect,"entryTime":entryTime,
			"userRoleIds":userRoleIds},
		success : function(res) {
			if(res==1){
				alert("修改成功");
				window.location.href="http://doogua.dangdang.com/auth/user/list";
			}else{
				if(res == -1){
					alert("创建用户失败！");
				}else if(res == -2){
					alert("创建员工失败！");
				}else if(res == -3){
					alert("为用户添加职位失败");
				}else if(res == -4){
					alert("为用户添加团队身份失败");
				}else if(res == -5){
					alert("为用户添加二级团队身份失败");
				}else{
					alert("创建失败，请重试！");
				}
			}
		}
	});
});
//验证并提交
$('#addForm').click(function(){
	//验证
	var userName = $('#userName').val();
	var password = $('#password').val(); 
	var confirm = $('#confirm').val(); 
	var statusSelect = $('#statusSelect').val(); 
	var entryTime = $('#entryTime').datebox('getValue');
	
	var zhName = $('#zhName').val(); 
	var enName = $('#enName').val(); 
	var zhSpell = $('#zhSpell').val(); 
	var number = $('#number').val(); 
	var email = $('#email').val(); 
	var mobilePhone = $('#mobilePhone').val(); 
	var tel = $('#tel').val(); 
	var departmentSelect = $('#departmentSelect').children('option:selected').val(); 
	var positionSelect = $('#positionSelect').children('option:selected').val(); 
	var teamSelect = $('#teamSelect').children('option:selected').val(); 
	var identitySelect = $('#identitySelect').children('option:selected').val(); 
	var subTeamSelect = $('#subTeamSelect').children('option:selected').val(); 
	var subIdentitySelect = $('#subIdentitySelect').children('option:selected').val(); 
	
	//用户角色的ids
	var userRoleIds = "";
	$('input[name="userRoleName"]:checked').each(function(){    
		userRoleIds += $(this).attr("id").replace("rid-","") + ",";
	});
	
	/**var re = new RegExp("[a-zA-Z]{3,}");
	if(userName == ''){
		alert("请填写用户名");
		return false;
	}else if(!re.test(userName)){
		alert("用户名应为员工姓名全拼");
		return false;
	}
	
	re = new RegExp("[a-zA-Z0-9]{6,18}");
	if(password == ''){
		alert("请填写密码");
		return false;
	}else if(!re.test(password)){
		alert("密码只能为字母，数字，长度6~18位");
		return false;
	}
	
	if(password != confirm){
		alert("两次输入的密码不一致");
		return false;
	}
	
	if(statusSelect < 0){
		alert("请重新选择账户状态");
		return false;
	}
	
	re = new RegExp("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
	if(entryTime == ''){
		alert("请选择入职日期");
		return false;
	}else if(!re.test(entryTime)){
		alert("入职日期有误，请重新选择");
		return false;
	}
	
	re = new RegExp("[\u4e00-\u9fa5|a-zA-Z]{1,}");
	if(!re.test(zhName)){
		alert("用户名只能包括中文和英文");
		return false;
	}
	
	re = new RegExp("^[a-zA-Z]{1,}$");
	if(!re.test(enName)){
		alert("英文名只能包括字母");
		return false;
	}
	
	if(zhSpell != ''){
		re = new RegExp("^[a-zA-Z]{1,}$");
		if(!re.test(zhSpell)){
			alert("只能包含英文字母");
			return false;
		}
	}
	
	if(number != ''){
		re = new RegExp("^[a-zA-Z0-9]{2,}$");
		if(!re.test(number)){
			alert("员工号只能包括数字和字母");
			return false;
		}
	}

	if(!re.test(email)){
		alert("邮箱格式有误");
		return false;
	}
	
	if(mobilePhone != ''){
		re = new RegExp("^[0-9]{11}$");
		if(!re.test(mobilePhone)){
			alert("手机格式有误");
			return false;
		}
	}
	
	if(tel != ''){
		re = new RegExp("^[0-9]{8,}$");
		if(!re.test(tel)){
			alert("电话格式有误");
			return false;
		}
	}
	
	//验证部门职位等的填写是否符合要求
	if(departmentSelect == 0){
		alert("请选择所属部门");
		return false;
	}
	
	if(positionSelect == 0){
		alert("请选择部门职位");
		return false;
	}*/
	
	//提交
	$.ajax({
		type : "POST",
		url : basePath+"/user/add",
		data:{"userName":userName,"password":password,"confirm":confirm,"status":statusSelect,"entryTime":entryTime,
			"zhName":zhName,"enName":enName,"zhSpell":zhSpell,"number":number,"email":email,"mobilePhone":mobilePhone,"tel":tel,
			"departmentSelect":departmentSelect,"positionSelect":positionSelect,
			"teamSelect":teamSelect,"identitySelect":identitySelect,
			"subTeamSelect":subTeamSelect,"subIdentitySelect":subIdentitySelect,
			"userRoleIds":userRoleIds},
		success : function(res) {
			if(res==1){
				alert("创建成功");
				window.location.href="http://doogua.dangdang.com/auth/user/list";
			}else{
				if(res == -1){
					alert("创建用户失败！");
				}else if(res == -2){
					alert("创建员工失败！");
				}else if(res == -3){
					alert("为用户添加职位失败");
				}else if(res == -4){
					alert("为用户添加团队身份失败");
				}else if(res == -5){
					alert("为用户添加二级团队身份失败");
				}else{
					alert("创建失败，请重试！");
				}
			}
		}
	});
});

$("#clearAll").click(function(){
	$.messager.confirm('确认清空？','确定清空所有内容，并重新填写？',function(r){
	    if (r){
	    	//清空所有
	    	$('#userName').val("");
	    	$('#password').val("");
	    	$('#confirm').val("");
	    	$('#statusSelect').get(0).selectedIndex = 0;
	    	$('#entryTime').datebox("setValue","");
	    	$('#zhName').val("");
	    	$('#enName').val("");
	    	$('#zhSpell').val("");
	    	$('#number').val("");
	    	$('#email').val("");
	    	$('#mobilePhone').val("");
	    	$('#tel').val("");
	    	$('#departmentSelect').get(0).selectedIndex = 0;
	    	$('#positionSelect').get(0).selectedIndex = 0;
	    	$('#teamSelect').get(0).selectedIndex = 0;
	    	$('#identitySelect').get(0).selectedIndex = 0;
	    	$('#subTeamSelect').get(0).selectedIndex = 0;
	    	$('#subIdentitySelect').get(0).selectedIndex = 0;
	    	$("[name='userRoleName']").removeAttr("checked");
	    	setTable(0,null);
	    }
	});
});

//用户角色弹出框
function addUserRole(){
//	$("#addUserRole").window('open');
	
	var top = $("#addUserRoleBtn").offset().top + 30;
	var left = $("#addUserRoleBtn").offset().left+500;

	$("#addUserRole").window('open').window('resize',{top: top,left:left});
}
$("#addUserRole").dialog({
	title:"添加用户角色",
	width:420,
	closed:true,
	cache:false,
	modal:true
});
//关闭弹窗
$("#cancelUserRoleBtn").click(function(){
	$("#addUserRole").dialog('close');
});

//添加用户角色事件
$("#addUserRoleBtn").click(function(){
	addUserAndRole();
});
function addUserAndRole(){
	var userRoleIds = "";
	$('input[name="userRoleName"]:checked').each(function(){    
		userRoleIds += $(this).attr("id").replace("rid-","") + ",";
	});

	$.ajax({
		type : "POST",
		url : basePath+"/role/getListByIds",
		dataType:"json",//表明返回参数的类型，这样才能解析好
		data:{"ids":userRoleIds},
		success : function(res) {
			res = eval(res);
			if(res.err==1){
				//设置“团队身份”下拉框
				setTable(3,res.data);
				$("#addUserRole").dialog("close");
			}else{
				alert("添加用户角色失败！");
			}
		}
	});
}