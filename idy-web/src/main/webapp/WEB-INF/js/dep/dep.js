/**
 * 创建部门的js
 */
var basePath = "/auth";

//创建部门
$("#addDepBtn").click(function(){
	var name = $("#name").val();
	var pDepartmentId = $("#pDepartmentId").children('option:selected').val();
	var introduce = $("#introduce").val();
	
	//验证
	if(name == ''){
		alert("请填写部门名称");
		return;
	}
	
	//提交
	$.ajax({
		type : "POST",
		url : basePath + "/dep/create",
		dataType : "json",
		data : {"name" : name,"pDepartmentId" : pDepartmentId,"introduce" : introduce},
		beforeSend : function(){
			$.messager.show({
				title:'请稍后',
				msg:'正在处理，请稍后...',
				timeout:3000,
				showType : 'show'
			});
		},
		success : function(json){
			if(json.err == 0){//
				//弹出提示框
				$.messager.show({
					title:'成功',
					msg:'部门添加成功！可以添加职位',
					timeout:3000,
					showType : 'show'
				});
				//设置本页中部门id
				$("#depId").val(json.data.id);
			}
			
		}
	});
	
});

//添加职位弹窗--创建按钮
$("#addPosBtn").click(function(){
	var depId = $("#depId").val();
	var posName = $("#posName").val();
	var posIntroduce = $("#posIntroduce").val();
	var posUsers = "";//这句没用
	var posRoleIds = addRoleIds;
	
	//验证
	if(posName == ''){
		alert("请填写职位名称");
		return;
	}
	
	//提交
	$.ajax({
		type : "POST",
		url : basePath + "/position/create",
		dataType : "json",
		data : {"depId":depId, "posName" : posName, "posIntroduce":posIntroduce, "posUsers":posUsers, "posRoleIds":posRoleIds},
		beforeSend : function(){
			$.messager.show({
				title:'请稍后',
				msg:'正在处理，请稍后...',
				timeout:3000,
				showType : 'show'
			});
		},
		success : function(json){
			if(json.err == 0){
				//调用方法，更新表格内容
				updateTable(depId);
				//关闭创建职位的弹窗
				$("#addPosition").dialog("close");

				//清空表单中所填内容,方便下次填写
				$("#posName").val("");
				$("#posIntroduce").val("");
				
				//弹出提示框
				$.messager.show({
					title:'成功',
					msg:'职位添加成功！',
					timeout:3000,
					showType : 'show'
				});
			}else{
				alert(json.msg);
			}
		}
	});
	
});

//职位列表--删除职位
function delPosition(posId){
	
	var depId = $("#depId").val();
	
	if(!isNaN(posId)){//如果是一个整数
		$.messager.confirm("删除？", "确认删除此职位？", function(r){
			if(r){
				$.ajax({
					type:"post",
					url:basePath+"/position/delById",
					dataType:"json",
					data:{"positionId":posId},
					beforeSend : function(){
						$.messager.show({
							title:'请稍后',
							msg:'正在处理，请稍后...',
							timeout:3000,
							showType : 'show'
						});
					},
					success:function(json){
						if(json.err == 0){
							updateTable(depId);
							//弹出提示框
							$.messager.show({
								title:'成功',
								msg:'职位删除成功！',
								timeout:3000,
								showType : 'show'
							});
						}else{
							alert("删除失败！");
						}
					}
				});
			}
		});
	}
}

//删除部门
/*function delDepartment(depId){
	if(!isNaN(depId)){//如果是一个整数
		$.ajax({
			type:"post",
			url:basePath+"/dep/delById",
			dataType:"json",
			data:{"positionId":posId},
			success:function(json){
				if(json.err == 0){
					alert("删除成功！");
					updateTable(depId);
				}else{
					alert("删除失败！");
				}
			}
		});
	}
}*/

//职位列表--编辑（弹出修改职位弹窗）
function editPosition(posId){
	//获得信息
	if(!isNaN(posId)){
		$.ajax({
			type : "post",
			url : basePath + "/position/getById",
			dataType : "json",
			data : {"positionId":posId},
			success : function(json){
				if(json.err == 0){
					//设置信息
					$("#eposName").val(json.data.name);
					$("#eposIntroduce").val(json.data.introduce);
					$("#eposId").val(json.data.id);//设置当前修改的职位id
					//设置已选角色的名称串
					var names = "";
					$.each(json.data.roles, function(index, content){
						names += content.name+"  ";
					});
					$("#eaddedRoles").html(names);
					//打开
					$("#editPosition").dialog("open");
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

//修改职位弹框--更新按钮（更新职位）
$("#editPosBtn").click(function(){
	var posId = $("#eposId").val();
	var name = $("#eposName").val();
	var introduce = $("#eposIntroduce").val();
	var roleIds = addRoleIds;
	
	$.ajax({
		type : "post",
		url : basePath + "/position/update",
		dataType : "json",
		data : {"positionId":posId, "name":name, "introduce":introduce, "roleIds":roleIds},
		beforeSend : function(){
			$.messager.show({
				title:'请稍后',
				msg:'正在处理，请稍后...',
				timeout:3000,
				showType : 'show'
			});
		},
		success : function(json){
			if(json.err == 0){
				//更新表格
				updateTable(0);//参数为0表示让方法自行获得部门id
				//关闭弹窗	
				$("#editPosition").dialog("close");
				
				//弹出提示框
				$.messager.show({
					title:'成功',
					msg:'职位修改成功！',
					timeout:3000,
					showType : 'show'
				});
			}else{
				alert(json.msg);
			}
		}
	});
});

//刷新职位列表
function updateTable(depId){
	if(depId != 0){
		//获得职位列表
		$.ajax({
			type : "POST",
			url : basePath+"/position/getByDepartmentId",
			dataType:"json",//表明返回参数的类型，这样才能解析好
			data:{"departmentId":depId},
			success : function(res) {
				res = eval(res);
				if(res.err==0){
					$('#posTable tr:not(:eq(0))').remove();
					$.each(res.data, function(index, content){
						var newRow = "<tr><td>"+content.id+"</td><td>"+content.name+"</td><td>" +content.userCount+"</td>"+
						"<td>" +
						"<a class=\"color-blue\" href=\"javascript:void(0)\" onclick=\"editPosition("+content.id+")\">编辑</a>" +
						"<span class=\"space color-blue\">|</span>" +
						"<a class=\"color-red\" href=\"javascript:void(0)\" onclick=\"delPosition("+content.id+")\">删除</a>" +
						"</td>" +
						"</tr>";
						$("#posTable tr:last").after(newRow);
					});
				}else{
					alert("获取职位失败！");
				}
			}
		});
	}else if(depId == 0){
		//自行获得id，看是否能够得到
		depId = $("#depId").val();
		if(depId != 0){
			//获得职位列表
			$.ajax({
				type : "POST",
				url : basePath+"/position/getByDepartmentId",
				dataType:"json",//表明返回参数的类型，这样才能解析好
				data:{"departmentId":depId},
				success : function(res) {
					res = eval(res);
					if(res.err==0){
						$('#posTable tr:not(:eq(0))').remove();
						$.each(res.data, function(index, content){
							var newRow = "<tr><td>"+content.id+"</td><td>"+content.name+"</td><td>" +content.userCount+"</td>"+
							"<td>" +
							"<a class=\"color-blue\" href=\"javascript:void(0)\" onclick=\"editPosition("+content.id+")\">编辑</a>" +
							"<span class=\"space color-blue\">|</span>" +
							"<a class=\"color-red\" href=\"javascript:void(0)\" onclick=\"delPosition("+content.id+")\">删除</a>" +
							"</td>" +
							"</tr>";
							$("#posTable tr:last").after(newRow);
						});
					}else{
						alert("获取职位失败！");
					}
				}
			});
		}else{
			//未获得部门id,清空
			$('#posTable tr:not(:eq(0))').remove();
		}
	}
}

//添加职位弹出框--选择角色按钮（打开“职位关联角色”弹出框）
$("#addPosRole").click(function(){
	$("#roleList").dialog('open');
});

//选择角色弹窗--确定按钮（添加职位相关角色）
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

//修改职位弹出框--选择角色按钮（打开“职位关联角色”弹出框）
$("#editPosRole").click(function openEditRole(){
	//当前编辑的职位id
	var posId = $("#eposId").val();
	
	//应该从后台查询出职位对应的角色列表进行显示
	$.ajax({
		type : "post",
		url : basePath + "/position/getById",
		dataType : "json",
		data : {"positionId":posId},
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

//选择角色弹窗--确定按钮（修改职位相关角色）
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

