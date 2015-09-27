package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import domain.EMediumRemote;

public class ThumbSnail {
	
	
	
	public static String thumbIt(EMediumRemote item) { 
		try {
			BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
			img.createGraphics().drawImage(ImageIO.read(new File(item.getPath())).getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);
			ImageIO.write(img, "jpg", new File("/Users/nunoalexandre/Desktop/THUMB.jpg"));
			return "/Users/nunoalexandre/Desktop/THUMB.jpg";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
