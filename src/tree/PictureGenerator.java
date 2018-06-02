package tree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureGenerator {
	private int left;
	
	public BufferedImage generatePicture(TreeNode root) {
		int[] result = getPicWidthAndHeight(root);
		int height = result[1] - 1, width = result[0];
		System.out.println(width + "," + height);
		
		BufferedImage bi = new BufferedImage(width * 100, height * 100, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		g.setColor(Color.BLACK);
		left = 0;
		drawNodes(root, g, -100);
		g.dispose();
		
		return bi;
	}
	
	private int drawNodes(TreeNode root, Graphics g, int height) {
		if(root.childs.isEmpty()) {
			g.drawRect(left + 25, height + 25, 50, 50);
			if(height > 0)
				g.drawLine(left + 50, height + 25, left + 50, height);
			left += 100;
			return left - 50;
		}
		else {
			int fc = drawNodes(root.childs.get(0), g, height + 100), lc = fc;
			for(int i = 1; i < root.childs.size(); i++)
				lc = drawNodes(root.childs.get(i), g, height + 100);
			int ret = (fc + lc) / 2;
			if(height > 0)
				g.drawLine(ret, height + 25, ret, height);
			g.drawRect(ret - 25, height + 25, 50, 50);
			g.drawLine(fc, height + 100, lc, height + 100);
			g.drawLine(ret, height + 75, ret, height + 100);
			return ret;
		}
	}
	
	private int[] getPicWidthAndHeight(TreeNode root) {
		System.out.println("root.val = " + root.val);
		int[] result = new int [2];
		if(root.childs.isEmpty())
			result[0] = result[1] = 1;
		else {
			result[0] = 0; result[1] = 1;
			for(TreeNode t: root.childs) {
				int[] res = getPicWidthAndHeight(t);
				result[0] += res[0];
				result[1] = Math.max(result[1], 1 + res[1]);
				//System.out.println(result[0]);
			}
		}
		return result;
	}
}
