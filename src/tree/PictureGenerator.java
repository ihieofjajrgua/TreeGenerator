package tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureGenerator {
	private int left, bWidth = 200, bHeight = 100, padding = 10;
	
	private int getBWidth(TreeNode root, Graphics g) {
		if(root == null)
			return 0;
		else {
			int ret = g.getFontMetrics().stringWidth(root.val) + 2 * padding;
			for(TreeNode t: root.childs)
				ret = Math.max(ret, getBWidth(t, g));
			return ret;
		}
	}
	
	public BufferedImage generatePicture(TreeNode root) {
		int[] result = getPicWidthAndHeight(root);
		int tHeight = result[1] - 1, tWidth = result[0];
		//System.out.println(tWidth + "," + tHeight);
		
		BufferedImage bi = new BufferedImage(tWidth * bWidth, tHeight * bHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		bWidth = getBWidth(root, g);
		//System.out.println(bWidth);
		
		g.setColor(Color.BLACK);
		left = 0;
		drawNodes(root, g, -bHeight);
		g.dispose();
		
		return bi;
	}
	
	private int drawNodes(TreeNode root, Graphics g, int height) {
		int strWidth = g.getFontMetrics().stringWidth(root.val);
		int strHeight = g.getFontMetrics().getHeight();
		int strDescent = g.getFontMetrics().getDescent();
		if(root.childs.isEmpty()) {
			g.drawRect(left + bWidth / 4, height + bHeight / 4, bWidth / 2, bHeight / 2);
			g.drawString(root.val, left + bWidth / 2 - strWidth / 2, height + bHeight / 2 + strHeight / 2 - strDescent);
			if(height > 0)
				g.drawLine(left + bWidth / 2, height + bHeight / 4, left + bWidth / 2, height);
			left += bWidth;
			return left - bWidth / 2;
		}
		else {
			int fc = drawNodes(root.childs.get(0), g, height + bHeight), lc = fc;
			for(int i = 1; i < root.childs.size(); i++)
				lc = drawNodes(root.childs.get(i), g, height + bHeight);
			int ret = (fc + lc) / 2;
			if(height > 0)
				g.drawLine(ret, height + bHeight / 4, ret, height);
			g.drawRect(ret - bWidth / 4, height + bHeight / 4, bWidth / 2, bHeight / 2);
			g.drawString(root.val, ret - strWidth / 2, height + bHeight / 2 + strHeight / 2 - strDescent);
			g.drawLine(fc, height + bHeight, lc, height + bHeight);
			g.drawLine(ret, height + bHeight * 3 / 4, ret, height + bHeight);
			return ret;
		}
	}
	
	private int[] getPicWidthAndHeight(TreeNode root) {
		//System.out.println("root.val = " + root.val);
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
