package tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Stack;

public class FileParser {
	public TreeNode parse(File filename) {
		TreeNode root = new TreeNode("");
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			while(line != null) {
				int counter = 0;
				while(line.charAt(counter) == '\t')
					counter++;
				
				while(s.size() - 2 > counter)
					s.pop();
				
				//System.out.println(line.trim());
				TreeNode t = new TreeNode(line.trim());
				if(s.size() - 2 == counter)
					s.pop();
				s.peek().childs.add(t);
				s.push(t);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
}
