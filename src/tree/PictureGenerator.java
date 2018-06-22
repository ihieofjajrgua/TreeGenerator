package tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureGenerator {
	private int left, bWidth, bHeight, padding = 10;
	
	private int getBWidth(TreeNode root, FontMetrics f) {
		if(root == null)
			return 0;
		else {
			int ret = Math.min(f.stringWidth("啊啊啊啊啊啊啊啊啊啊"), 
				f.stringWidth(root.val)) + 4 * padding;
			for(TreeNode t: root.childs)
				ret = Math.max(ret, getBWidth(t, f));
			return ret;
		}
	}
	
	private int getBHeight(TreeNode root, FontMetrics f) {
		if(root == null)
			return 0;
		else {
			int lineStart = 0, height = 0;
			for(int i = 0; i < root.val.length(); i++) {
				if(f.stringWidth(root.val.substring(lineStart, i)) > bWidth) {
					lineStart = i;
					height++;
				}
			}
			height++;
			int ret = height * f.getHeight() + 4 * padding;
			
			for(TreeNode t: root.childs)
				ret = Math.max(ret, getBHeight(t, f));
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
		bWidth = getBWidth(root, g.getFontMetrics());
		bHeight = getBHeight(root, g.getFontMetrics());
		System.out.println(bWidth + "," + bHeight);
		
		bi = new BufferedImage(tWidth * bWidth, tHeight * bHeight, BufferedImage.TYPE_INT_RGB);
		g = bi.getGraphics();
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		g.setColor(Color.BLACK);
		left = 0;
		drawSubTree(root, g, -bHeight);
		g.dispose();
		
		return bi;
	}
	
	private int drawNode(TreeNode node, Graphics g, int mid, int height) {
		int strWidth = g.getFontMetrics().stringWidth(node.val);
		int strHeight = g.getFontMetrics().getHeight();
		int strDescent = g.getFontMetrics().getDescent();
		
		g.drawString(node.val, mid - strWidth / 2, height + bHeight / 2 + strHeight / 2 - strDescent);
		g.drawRect(mid - strWidth / 2 - padding, height + bHeight / 2 - strHeight / 2 - padding, strWidth + 2 * padding, strHeight + 2 * padding);
		if(height > 0)
			g.drawLine(mid, height + bHeight / 2 - strHeight / 2 - padding, mid, height);
		
		return height + bHeight / 2 + strHeight / 2 + padding;
	}
	
	private int drawSubTree(TreeNode root, Graphics g, int height) {
		if(root.childs.isEmpty()) {
			drawNode(root, g, left + bWidth / 2, height);
			left += bWidth;
			return left - bWidth / 2;
		}
		else {
			int fc = drawSubTree(root.childs.get(0), g, height + bHeight), lc = fc;
			for(int i = 1; i < root.childs.size(); i++)
				lc = drawSubTree(root.childs.get(i), g, height + bHeight);
			int ret = (fc + lc) / 2;
			int bottom = drawNode(root, g, ret, height);
			
			g.drawLine(fc, height + bHeight, lc, height + bHeight);
			g.drawLine(ret, bottom, ret, height + bHeight);
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
