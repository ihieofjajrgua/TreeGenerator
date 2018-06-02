package tree;

import java.util.ArrayList;

public class TreeNode {
	String val;
	ArrayList<TreeNode> childs;
	
	public TreeNode(String val) {
		this.val = val;
		childs = new ArrayList<TreeNode>();
	}
}
