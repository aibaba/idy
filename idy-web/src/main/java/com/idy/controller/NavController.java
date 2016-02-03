package com.idy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idy.exception.resolver.StackTrace;


/**
 * @Description 首页节点的请求处理控制器
 * @author gaopengbd
 * @date 2014年9月2日 下午3:58:16 
 * @version V1.0
 */
@Controller
@RequestMapping("/nav")
public class NavController {
	
	Map<String, TreeNode> TreeNodeMap = new LinkedHashMap<String, NavController.TreeNode>();
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NavController.class);
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<TreeNode> navTreeData(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		return getTreeNodesAccordingToUserRoles(request,response);
	}
	
	private List<TreeNode> getTreeNodesAccordingToUserRoles(
			HttpServletRequest request, HttpServletResponse response) {
	    List<TreeNode> nodeList = new ArrayList<NavController.TreeNode>();
	    TreeNodeMap.clear();
    	buildAuthNodes(request, response);
		nodeList.addAll(TreeNodeMap.values());
		return nodeList ;
	}
	
	/**
	 * 依赖权限系统的获取节点资源
	 * @param userId
	 */
	public void buildAuthNodes(HttpServletRequest request, HttpServletResponse response) {
		try {
			putTreeNode("servingStaff", newTreeNode("在职员工", "/roster/serving", ""));
			putTreeNode("entryStaff", newTreeNode("入职员工", "/roster/entry", ""));
			putTreeNode("leaveStaff", newTreeNode("离职员工", "/roster/leave", ""));
			putTreeNode("callInStaff", newTreeNode("调入员工", "/roster/callin", ""));
			putTreeNode("callOutStaff", newTreeNode("调出员工", "/roster/callout", ""));
			putTreeNode("toFormalStaff", newTreeNode("转正员工", "/roster/toformal", ""));
			putTreeNode("secOfferStaff", newTreeNode("二次入职", "/roster/secoffer", ""));
			putTreeNode("logRoster", newTreeNode("操作日志", "/log/roster", ""));
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		}
	}
	
	protected static TreeNode newTreeNode(String text, String url, String iconCls){
		TreeNode treeNode = new TreeNode();
		treeNode.text = text;
		treeNode.iconCls = iconCls;
		treeNode.attributes.put("url", url);
		return treeNode;
	}
	
	protected void putTreeNode(String key, TreeNode treeNode) {
	    if(!TreeNodeMap.containsKey(key)) {
	        TreeNodeMap.put(key, treeNode);
	    }
	}
	
	/**
	 * 处理匹配不到url的情况
	 */
	@RequestMapping(value = "*")
    public String error(
            HttpServletRequest request, HttpServletResponse response,Model model) {
		
		return "error/404";
    }
	
	/**
	 * JEasyui Tree Node class 
	 * 
	 * id: node id, which is important to load remote dat
	 * text: node text to show
	 * state: node state, 'open' or 'closed', default is 'open'. When set to 'closed', the node have children nodes and will load them from remote site
	 * checked: Indicate whether the node is checked selected.
	 * attributes: custom attributes can be added to a node
	 * children: an array nodes defines some children nodes
	 *
	 */
	public @Data static class TreeNode {
		String id;
		String iconCls;
		String text;
		boolean checked;
		List<TreeNode> children;
		Map<String, String> attributes = new HashMap<String, String>();
		String state = "open";
	}
	
	public @Data class AuthNode {
		private String id ;
		private String name ;
		private String url ;
	}
}
