import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class bitTests {
		@Test
		void creationTest() {
			byte arr[] = ImageBitExtraction.getBites("./src/us.png");
			if (arr == null) {
				fail();
			}
			System.out.print("Start: ");
			for(int i = 0; i < arr.length; i++) {
				System.out.print(i % 0xFF+ " ");
			}
		}
		@Test
		void bitCovversion() {
			byte a =(byte) 138;
			byte b =(byte) 122;
			byte c =(byte) 255;
			byte d = (byte) 0;
			byte e =(byte) 42;
			
			assertEquals(138,a & 255);
			assertEquals(122,b & 255);
			assertEquals(255,c & 255);
			assertEquals(0,d & 255);
			assertEquals(42,e & 255);

		}
	}

