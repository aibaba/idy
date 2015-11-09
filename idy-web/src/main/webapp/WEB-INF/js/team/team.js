/**
 * 创建团队的js
 */
var basePath = "/auth";
//创建团队--创建按钮√
$("#submitForm").click(function(){
	$.ajax({
		type : "POST",
		url : basePath+"/team/create",
		dataType:"json",
		data:{"teamName":$('#teamName').val(),"departmentOwner":$('#departmentOwner').val(),"pTeamId":$('#pTeamId').val(),"teamAppend":$('#teamAppend').val(),"pTeamId":$('#pTeamId').val()},
		success : function(json) {
			if(json.err==0){
				alert(json.msg);
				$("#teamId").val(json.data.id);					
			}
			else {
				alert(json.msg);
			}
		}
		
	});
});


//添加身份弹窗--创建按钮√
$("#createForm").click(function(){
	var teamId = $("#teamId").val();
	var idenName = $("#idenName").val();
	var idenDiscribe = $("#idenDiscribe").val();
	var roleOfIden = addRoleIds;
	
	//提交
	$.ajax({
		type : "POST",
		url : basePath+"/team/createIden",
		dataType : "json",
		data:{"teamId":$('#teamId').val(),"idenName":idenName,"idenDiscribe":idenDiscribe,"roleOfIden":roleOfIden},
		success : function(json){
			if(json.err == 0){
				alert("添加身份成功！");
				//更新表格内容
				updateTable(teamId);
				//关闭创建职位的弹窗
				$("#addIdentity").dialog("close");
				
				//清空表单中所填内容,方便下次填写
				$("#idenName").val("");
				$("#idenDiscribe").val("");
				
				
			}else{
				alert(json.msg);
			}
			
		}
	});
	
});

//删除身份√
function delIdentity(idenId){
	
	var teamId = $("#teamId").val();
	
	if(!isNaN(idenId)){//如果是一个整数
		$.messager.confirm("删除？", "确认删除此身份？", function(r){
			if(r){
				$.ajax({
					type:"post",
					url:basePath+"/identity/delById",
					dataType:"json",
					data:{"identityId":idenId},
					
					success:function(json){
						if(json.err == 0){
							alert("删除成功！");
							updateTable(teamId);
							//弹出提示框
							
						}else{
							alert("删除失败！");
						}
					}
				});
			}
		});
	}
}

//身份列表--编辑身份（弹出修改身份弹窗）√
function editIdentity(idenId){
	//获得信息
	if(!isNaN(idenId)){
		$.ajax({
			type : "post",
			url : basePath + "/identity/getById",
			dataType : "json",
			data : {"identityId":idenId},
			success : function(json){
				if(json.err == 0){
					//设置信息
					$("#eidenName").val(json.data.name);
					$("#eidenIntroduce").val(json.data.introduce);
					$("#eidenId").val(json.data.id);//设置当前修改的身份id
					//设置已选角色的名称串
					var names = "";
					$.each(json.data.roles, function(index, content){
						names += content.name+"  ";
					});
					$("#eaddedRoles").html(names);
					//打开
					
					$("#editIdentity").dialog("open");
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

//修改身份弹窗--更新身份按钮√
$("#editIdenBtn").click(function(){
	var idenId = $("#eidenId").val();	
	var idenName = $("#eidenName").val();
	var idenDiscribe = $("#eidenDiscribe").val();
	var roleIds = addRoleIds;
	
	$.ajax({
		type : "post",
		url : basePath + "/identity/update",
		dataType : "json",
		data : {"idenId":idenId, "idenName":idenName, "idenDiscribe":idenDiscribe, "roleIds":roleIds},
	
		success : function(json){
			if(json.err == 0){
				alert("更新成功！");
				//更新表格
				updateTable(0);//参数为0表示让方法自行获得部门id
				//关闭弹窗	
				$("#editIdentity").dialog("close");				
				
			}else{
				alert(json.msg);
			}
		}
	});
});
//更新身份列表
function updateTable(teamId){
	//获得身份列表
	if(teamId != 0){
		$.ajax({
			type : "POST",
			url : basePath+"/identity/getByTeamId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"teamId":teamId},
			success : function(res) {
				if(res.err==0){				
					$("#idenList tr:not(:eq(0))").remove();
					$.each(res.data, function(index,content){
						var newRow = "<tr><td>"+content.id+"</td><td>"+content.name+"</td><td>" +content.userCount+"</td>"+
						"<td>" +
						"<a class=\"color-blue\" href=\"javascript:void(0)\" onclick=\"editIdentity("+content.id+")\">编辑</a>" +
						"<span class=\"space color-blue\">|</span>" +
						"<a class=\"color-red\" href=\"javascript:void(0)\" onclick=\"delIdentity("+content.id+")\">删除</a>" +
						"</td>" +
						"</tr>";
						$("#idenList tr:last").after(newRow);
				    });
				}else{
					alert("获取身份失败！");
				}
			}
		});
	}
	else if(teamId == 0){
		//自行获得id，看是否能够得到
		teamId = $("#teamId").val();
		if(teamId != 0){
			//获得职位列表
			$.ajax({
				type : "POST",
				url : basePath+"/identity/getByTeamId",
				dataType:"json",//表明返回参数的类型，这样才能解析好
				data:{"teamId":teamId},
				success : function(res) {
					res = eval(res);
					if(res.err==0){
						$('#idenList tr:not(:eq(0))').remove();
						$.each(res.data, function(index, content){
							var newRow = "<tr><td>"+content.id+"</td><td>"+content.name+"</td><td>" +content.userCount+"</td>"+
							"<td>" +
							"<a class=\"color-blue\" href=\"javascript:void(0)\" onclick=\"editIdentity("+content.id+")\">编辑</a>" +
							"<span class=\"space color-blue\">|</span>" +
							"<a class=\"color-red\" href=\"javascript:void(0)\" onclick=\"delIdentity("+content.id+")\">删除</a>" +
							"</td>" +
							"</tr>";
							$("#idenList tr:last").after(newRow);
						});
					}else{
						alert("获取身份失败！");
					}
				}
			});
		}
		else{
			//未获得部门id
		}
	}
}

//添加身份弹出框--选择角色按钮（打开“职位关联角色”弹出框）√
$("#addPosRole").click(function(){
	$("#roleList").dialog('open');
});

//选择角色弹窗--确定按钮（添加职位相关角色）√
var addRoleIds = "";
var addRoleNames = "";
$("#addRoleBtn").click(function(){
	addRoleIds = "";
	addRoleNames = "";
	
	$('input[name="role"]:checked').each(function(){    
		addRoleIds += $(this).attr("value") + ",";
		addRoleNames += $(this).attr("roleName") + "  ";
	});
	
	//保存这些角色
	$("#addedRoles").html(addRoleNames);
	$("#roleList").dialog('close');
	
	var rids = addRoleIds.split(",");
	$.each(rids, function(index, content){
		$('#role-'+content).removeAttr("checked");
	});
});

//修改身份弹出框--选择角色按钮（打开“身份关联角色”弹出框）√
$("#editIdenRole").click(function openEditRole(){
	//当前编辑的身份id
	var idenId = $("#eidenId").val();
	
	//应该从后台查询出职位对应的角色列表进行显示
	$.ajax({
		type : "post",
		url : basePath + "/identity/getById",
		dataType : "json",
		data : {"identityId":idenId},
		success : function(json){
			if(json.err == 0){
				$.each(json.data.roles, function(index, content){
					$('#erole-'+content.id).attr("checked","checked");
				});
				$("#editRoleList").dialog('open');
			}else{
				alert(json.msg);
			}
		}
	});
});

//选择角色弹窗--确定按钮（修改职位相关角色）√
var editRoleIds = "";
var editRoleNames = "";
$("#editRoleBtn").click(function(){
	editRoleIds = "";  
	editRoleNames = "";
	
	$('input[name="erole"]:checked').each(function(){    
		editRoleIds += $(this).attr("value") + ",";
		editRoleNames += $(this).attr("roleName") + "  ";
	});
	
	//把创建的两个变量也改变
	addRoleIds = editRoleIds;
	addRoleNames = editRoleNames;
	
	//保存这些角色
	$("#eaddedRoles").html(editRoleNames);
	$("#editRoleList").dialog('close');
});

