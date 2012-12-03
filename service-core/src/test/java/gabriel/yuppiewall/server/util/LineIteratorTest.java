package gabriel.yuppiewall.server.util;

import gabriel.yuppiewall.common.LineIterator;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;

public class LineIteratorTest {

	private InputStream file;

	@Before
	public void setUp() throws Exception {
		LineIteratorTest.class.getResourceAsStream("A.txt");
		file = LineIteratorTest.class.getResourceAsStream("A.txt");
	}

	@org.junit.Test
	public void test() throws FileNotFoundException {
		LineIterator iterator = new LineIterator(file);
		String actual[] = new String[5];
		int index = 0;
		while (iterator.hasNext()) {
			actual[index++] = iterator.next();
		}
		Assert.assertArrayEquals(new String[] { "line1", "line2", "line3",
				"line4", "line5" }, actual);
		iterator.close();

	}

	@org.junit.Test
	public void test1() throws FileNotFoundException {
		LineIterator iterator = new LineIterator(file);
		String actual[] = new String[5];
		int index = 0;

		actual[index++] = iterator.next();
		actual[index++] = iterator.next();
		actual[index++] = iterator.next();
		actual[index++] = iterator.next();
		actual[index++] = iterator.next();

		Assert.assertArrayEquals(new String[] { "line1", "line2", "line3",
				"line4", "line5" }, actual);
		iterator.close();

	}
}
