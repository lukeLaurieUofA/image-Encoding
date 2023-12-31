import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * write method will write an encoding to a target file
 */
public class WriteToFile {

	/**
	 * @param encoding: String[] huffman encoding
	 * @param path: path to target file
	 */
	public static void write(byte[] encoding , String path) {
		try {
		    FileOutputStream outputStream = new FileOutputStream(path);
		    for (int i = 0; i < encoding.length; i++) {
			    outputStream.write(encoding[i]);
		    }
		    outputStream.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File could not be found");
		}
		catch (IOException e) {
			System.out.println("File could not wrote to found");
		}
	}
}