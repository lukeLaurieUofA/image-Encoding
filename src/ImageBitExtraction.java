import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBitExtraction {

	/*
	 * Return an arrays of bytes in a photo. If the
	 * file does not exist it returns null.
	 */
	public static byte[] getBites(String path) {
		File imageFile = new File(path);

		try {

			BufferedImage image = ImageIO.read(imageFile);
			
			int height = image.getHeight();
			int width = image.getWidth();
			int arrayLength = height * width * 3;
			int i = 0; // used to place bytes into the array


			byte[] bytesArray = new byte[arrayLength];
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < width; k++) {
					
					// gets readable pixel
					Color pixel = new Color(image.getRGB(k, j));
					
					// type casts ints to bytes
					byte red = (byte) pixel.getRed();
					byte blue = (byte) pixel.getBlue();
					byte green = (byte) pixel.getGreen();
					
					//add to bytes. 
					bytesArray[i] = red;
					i++;
					bytesArray[i] = green;
					i++;
					bytesArray[i] = blue;
					i++; 

				}
			}
			return bytesArray;
		} catch (IOException e) {
			if(!imageFile.exists()) {
				System.out.println("file does not exist");
			}else if(!imageFile.canRead()) {
				System.out.println("Image file is unreadable");
			}
			return null;
		}		
	}
}
