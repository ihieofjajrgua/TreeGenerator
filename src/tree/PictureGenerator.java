package tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureGenerator {
	private int left, bWidth, bHeight, padding = 10;
	
	private int getBWidth(TreeNode root, Graphics g) {
		if(root == null)
			return 0;
		else {
			int ret = g.getFontMetrics().stringWidth(root.val) + 4 * padding;
			for(TreeNode t: root.childs)
				ret = Math.max(ret, getBWidth(t, g));
			return ret;
		}
	}
	
	public BufferedImage generatePicture(TreeNode root) {
		int[] result = getTreeWidthAndHeight(root);
		int tHeight = result[1] - 1, tWidth = result[0];
		//System.out.println(tWidth + "," + tHeight);
		
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
		bWidth = getBWidth(root, g);
		bHeight = g.getFontMetrics().getHeight() + 4 * padding;
		
		bi = new BufferedImage(tWidth * bWidth, tHeight * bHeight, BufferedImage.TYPE_INT_RGB);
		g = bi.getGraphics();
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
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
			g.drawString(root.val, left + bWidth / 2 - strWidth / 2, height + bHeight / 2 + strHeight / 2 - strDescent);
			g.drawRect(left + bWidth / 2 - strWidth / 2 - padding, height + bHeight / 2 - strHeight / 2 - padding, strWidth + 2 * padding, strHeight + 2 * padding);
			if(height > 0)
				g.drawLine(left + bWidth / 2, height + padding, left + bWidth / 2, height);
			left += bWidth;
			return left - bWidth / 2;
		}
		else {
			int fc = drawNodes(root.childs.get(0), g, height + bHeight), lc = fc;
			for(int i = 1; i < root.childs.size(); i++)
				lc = drawNodes(root.childs.get(i), g, height + bHeight);
			int ret = (fc + lc) / 2;
			g.drawString(root.val, ret - strWidth / 2, height + bHeight / 2 + strHeight / 2 - strDescent);
			if(height > 0)
				g.drawLine(ret, height + padding, ret, height);
			g.drawRect(ret - strWidth / 2 - padding, height + bHeight / 2 - strHeight / 2 - padding, strWidth + 2 * padding, strHeight + 2 * padding);
			g.drawLine(fc, height + bHeight, lc, height + bHeight);
			g.drawLine(ret, height + bHeight - padding, ret, height + bHeight);
			return ret;
		}
	}
	
	private int[] getTreeWidthAndHeight(TreeNode root) {
		//System.out.println("root.val = " + root.val);
		int[] result = new int [2];
		if(root.childs.isEmpty())
			result[0] = result[1] = 1;
		else {
			result[0] = 0; result[1] = 1;
			for(TreeNode t: root.childs) {
				int[] res = getTreeWidthAndHeight(t);
				result[0] += res[0];
				result[1] = Math.max(result[1], 1 + res[1]);
				//System.out.println(result[0]);
			}
		}
		return result;
	}
}
