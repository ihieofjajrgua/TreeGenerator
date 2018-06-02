package tree;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pathname = args[0];
		FileParser parser = new FileParser();
		TreeNode root = parser.parse(new File(pathname));
		PictureGenerator generator = new PictureGenerator();
		BufferedImage bi = generator.generatePicture(root);
		try {
			ImageIO.write(bi, "jpg", new File("C:/Users/zhaotong/Desktop/test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
