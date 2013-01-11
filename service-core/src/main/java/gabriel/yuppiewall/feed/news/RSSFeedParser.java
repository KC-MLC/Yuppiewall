package gabriel.yuppiewall.feed.news;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser {

	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String LINK = "link";
	private static final String ITEM = "item";
	private static final Proxy SYSTEM_PROXY;
	static {
		System.setProperty("java.net.useSystemProxies", "true");
		SYSTEM_PROXY = Proxy.NO_PROXY;
		/*SYSTEM_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"43.80.41.41", 8080));*/

	}

	public static List<Feed> readFeed(String feedUrl) throws IOException {
		URL url = new URL(feedUrl);
		List<Feed> feeds = new ArrayList<>();
		try {

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			InputStream in = url.openConnection(SYSTEM_PROXY).getInputStream();

			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			Feed feed = null;
			String description;

			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart() == (ITEM)) {
						feed = new Feed();
						feeds.add(feed);
						// event = eventReader.nextEvent();
						continue;
					}
					if (feed == null)
						continue;
					if (event.asStartElement().getName().getLocalPart() == (TITLE)) {
						event = eventReader.nextEvent();
						//String title = event.asCharacters().getData();
						String title =readHtml(eventReader, event);
						feed.setTitle(title);
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (DESCRIPTION)) {
						event = eventReader.nextEvent();
						// description = event.asCharacters().getData();
						description = readHtml(eventReader, event);/**/
						feed.setDescription(description);
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (LINK)) {
						event = eventReader.nextEvent();
						// readHtml

						String link = readHtml(eventReader, event);
						feed.setLink(link);
						continue;
					}

				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						feed = null;
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feeds;

	}

	private static String readHtml(XMLEventReader eventReader, XMLEvent event)
			throws XMLStreamException {
		StringBuilder content = new StringBuilder();
		do {
			if (event.isEndElement())
				break;
			content.append(event.asCharacters().getData());
			event = eventReader.nextEvent();
		} while (eventReader.hasNext());
		return content.toString();
	}

}
