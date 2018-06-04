package tree;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pathname = args[0];
		File txt = new File(pathname);
		if(!txt.exists()) {
			System.err.println("文件不存在！");
			return;
		}
		
		FileParser parser = new FileParser();
		TreeNode root = parser.parse(txt);
		PictureGenerator generator = new PictureGenerator();
		BufferedImage bi = generator.generatePicture(root);
		
		try {
			ImageIO.write(bi, "jpg", new File(pathname.substring(0, pathname.lastIndexOf(".")) + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
