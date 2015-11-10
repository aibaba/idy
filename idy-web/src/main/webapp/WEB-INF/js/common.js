/**
 * Common library of doogua web. Attention: depending on jquery and jeasyui,
 * make sure loaded after them.
 */

var $rdm;
var baseUrl;

function fixWidth(percent)  
{  
    return document.body.clientWidth * percent ; //这里你可以自己做调整  
} 

(function() {
	
	var RedisManage = function() {
		this.debug = shouldDebug();
	};

	var shouldDebug = function(){
		var search = window.location.search;
		if (search && search.match(/_debug/)) {
			return true;
		}
		return false;
	};
	
	/*Initialize navigation tree*/
	RedisManage.prototype.initNavigationTree = function() {
		baseUrl = this._baseUrl;
		$("#nav_tree").tree({
			method : 'get',
			url : 'nav/tree.json',
			onClick : function(node) {
				var oriUrl = node.attributes['url'];
				if(oriUrl == '' || oriUrl == null) return ;
				var targetUrl ;
				if(oriUrl.charAt(0)=='/'){
					targetUrl = baseUrl + oriUrl;
					window.location.href = targetUrl;
				}
				else{
					window.open(oriUrl);
				}
			}
		});
	};
	
	$rdm = $rdm || new RedisManage();
	
	$rdm.logout = function() {
		window.location.href = $rdm.baseUrl() + "/logout";
	};
	
	/*Initialize bread crumb title of main container*/
	RedisManage.prototype.initBreadCrumb = function() {
		$('#main_container').siblings('.panel-header').children('.panel-title').html($('.breadcrumb'));
	};
	
	RedisManage.prototype.init = function(baseUrl) {
		this._baseUrl = baseUrl;
		this.initNavigationTree();
		this.initBreadCrumb();
	};
	
	RedisManage.prototype.baseUrl = function() {
		return this._baseUrl;
	};

	RedisManage.prototype.parseDateTime = function(date){
		 var month_ = date.getMonth()+1;
     	 var date_ = date.getDate();
     	 var hours_ = date.getHours();
     	 var minutes_ = date.getMinutes();
     	 var seconds_ = date.getSeconds();
    	 if(month_ < 10){
    	 	month_ = '0' + month_;
    	 }
    	 if(date_ < 10){
    	 	date_ = '0' + date_;
    	 }
    	 if(hours_ < 10){
    	 	hours_ = '0' + hours_;
    	 }
    	 if(minutes_ < 10){
    	 	minutes_ = '0' + minutes_;
    	 }
    	 if(seconds_ < 10){
    	 	seconds_ = '0' + seconds_;
    	 }
    	 return date.getFullYear() + '-' + month_ + '-' + date_ + " " + hours_ + ':' + minutes_ + ':' + seconds_;
	};
	RedisManage.prototype.parseDate = function(date){
		 var month_ = date.getMonth()+1;
    	 var date_ = date.getDate();
   	 if(month_ < 10){
   	 	month_ = '0' + month_;
   	 }
   	 if(date_ < 10){
   	 	date_ = '0' + date_;
   	 }
   	 return date.getFullYear() + '-' + month_ + '-' + date_ ;
	};
	
	/*$rdm.topics = {};
	$rdm.topics.doAudit = function(id, onSuccess) {
		$.post($rdm.baseUrl() + '/topics/' + id + '/doAudit.json', onSuccess, "json");
	};
	$rdm.topics.refuseAudit = function(id, onSuccess) {
		$.post($rdm.baseUrl() + '/topics/' + id + '/doRefuse.json', onSuccess, "json");
	};
	$rdm.topics.onClickRemoveBtn = function(topicsRemoveBtn) {
		var id = $(topicsRemoveBtn).attr('rdm-topics-id');
		var name = $(topicsRemoveBtn).attr('rdm-topics-name');
		$.messager.confirm('删除确认','你确定要删除' + name + '话题吗？',function(remove){  
		    if (remove){
		    	$rdm.topics.remove(id, function(rt){
		    		if(rt.msg != "") {
		    			$.messager.alert('Error',rt.msg,'error');
		    		} else {
		    			window.location.reload();
		    		}
		    	});
		    }
		});  
	};*/
})();	