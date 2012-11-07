package gabriel.yuppiewall.server.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<String> {

	private BufferedReader bufferedReader;
	private String currentLine;
	private boolean finished;

	public LineIterator(InputStream is) throws FileNotFoundException {

		bufferedReader = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public boolean hasNext() {
		if (currentLine != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			try {
				return ((currentLine = bufferedReader.readLine()) != null) ? true
						: !(finished = true);

			} catch (IOException ioe) {
				close();
				throw new IllegalStateException(ioe.toString());
			}
		}
	}

	@Override
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException("No more lines");
		String temp = currentLine.trim();
		currentLine = null;
		return temp;
	}

	public void close() {
		finished = true;
		try {
			bufferedReader.close();
		} catch (IOException ignore) {
		}
		currentLine = null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

}
