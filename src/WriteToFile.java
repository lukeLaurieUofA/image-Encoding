import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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