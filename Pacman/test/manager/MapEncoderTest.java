package manager;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapEncoderTest {

	@Test
	public void testEncodeEmptyString() {
		assertEquals("", MapEncoder.RunLengthEncodeLine(""));
	}
	
	@Test
	public void testEncode1() {
		assertEquals("3a4b2c", MapEncoder.RunLengthEncodeLine("aaabbbbcc"));
	}
	
	@Test
	public void testEncode2() {
		assertEquals("1:15 1#15 1#15 1:", MapEncoder.RunLengthEncodeLine(":               #               #               :"));
	}
	
	@Test
	public void testEncode3() {
		assertEquals("49#", MapEncoder.RunLengthEncodeLine("#################################################"));
	}
	
	@Test
	public void testEncode4() {
		// rare case where there are no adjacent chars which are the same
		assertEquals("1#1 1*1 1#1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1*1 1#1 1*1 1#", MapEncoder.RunLengthEncodeLine("# * # * * * * * * * * * * * * * * * * * * * # * #"));
	}
	
	@Test
	public void testIsEncodedEmptyString() {
		assertEquals(true, MapEncoder.isEncodedLine(""));
	}
	
	@Test
	public void testIsEncoded1() {
		assertEquals(true, MapEncoder.isEncodedLine("3a11b9c"));
	}
	
	@Test
	public void testIsEncoded2() {
		assertEquals(false, MapEncoder.isEncodedLine("# p * * * * # * * * * * * * * * * * # * * * * * #"));
	}
	
	@Test
	public void testIsEncoded3() {
		assertEquals(false, MapEncoder.isEncodedLine("#################################################"));
	}
	
	@Test
	public void testIsEncoded4() {
		assertEquals(true, MapEncoder.isEncodedLine(""));
	}
	
	@Test
	public void testIsEncoded5() {
		assertEquals(true, MapEncoder.isEncodedLine("1:15 1#15 1#15 1:"));
	}
}
