// JavaScript Document

$(function(){
	
	//评论消息 系统消息
	$('.commentActive :checkbox').click(function(){
		$(this).prop('checked') ? $(this).parents('.commentActive').siblings('.msgList').find(':checkbox').prop('checked',true) : $(this).parents('.commentActive').siblings('.msgList').find(':checkbox').prop('checked',false);
	});
	
	
});