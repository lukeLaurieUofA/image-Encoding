import java.util.Arrays;

public class ProgramTesting {
	public static void main(String args[]) {
		boolean MaskTestSuccess = true;
		boolean SameColorTest = true; 
		System.out.println("Test 01:  Ensuring  that the mask is able is accurate \n");

		// initilize bytes to test mask.
		for (int i = 0; i < 256; i++) {
			byte temp = (byte) i;
			if ((temp & 0xff) != i) {
				System.out.println("MASK FAILURE AT VALUE: " + i);
				MaskTestSuccess = false;
			}
		}

		if (MaskTestSuccess) {
			System.out.println("Mask values produces accuarte numbers in [0-255] \n");
		}

		System.out.println("Test 02: Testing the bit extraction \n");

		byte[] purplePixels = ImageBitExtraction.getBites("./src/purple.png");

		for (int i = 0; i < purplePixels.length - 1; i+=3) {
			if ((purplePixels[i] & 0xff) != 194) {
					System.out.println("Wrong  color value for red should be 194 is " +( purplePixels[i] & 0xff ));
			}
			if ((purplePixels[i + 1] & 0xff) != 176) {
					System.out.println("Wrong  color value for red should be 176 is " + (purplePixels[i+1] & 0xff) );
			}
			if ((purplePixels[i + 2] & 0xff )!=  224) {
					System.out.println("Wrong  color value for red should be 224 is " + (purplePixels[i+2] & 0xff ));
			}
			}
		}
	}
