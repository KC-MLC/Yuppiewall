package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.vaadin.vaadinvisualizations.AnnotatedTimeLine;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLineEntry;

public class ATLView {
	public AnnotatedTimeLine drawChart(
			Map<String, List<EndOfDayData>> instrumentMap) {
		org.vaadin.vaadinvisualizations.AnnotatedTimeLine atl = new org.vaadin.vaadinvisualizations.AnnotatedTimeLine();
		atl.setOption("displayAnnotations", true);
		atl.setOption("wmode", "window");

		atl.addLineLabel("Sold Pencils");
		atl.addLineLabel("Sold Pens");
		// a time line can have multiple entries as above 'Sold Pencils' and
		// 'Sold Pens'
		// for each distinct entry you have to set a value for each of the above
		// entries

		ArrayList<AnnotatedTimeLineEntry> timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries.add(new AnnotatedTimeLineEntry(30000, "", "")); // Sold
																		// Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(40645, "", "")); // Sold
																		// Pens

		atl.add(new GregorianCalendar(2008, 0, 1), timeLineEntries);

		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries.add(new AnnotatedTimeLineEntry(14045, "", "")); // Sold
																		// Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(20374, "", "")); // Sold
																		// Pens

		atl.add(new GregorianCalendar(2008, 0, 2), timeLineEntries);
		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries.add(new AnnotatedTimeLineEntry(55022, "", "")); // Sold
																		// Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(50766, "", "")); // Sold
																		// Pens

		atl.add(new GregorianCalendar(2008, 0, 3), timeLineEntries);
		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries.add(new AnnotatedTimeLineEntry(75284, "", "")); // Sold
																		// Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(14334, "Out of Stock",
				"Ran out of stock at 4pm")); // Sold Pens

		atl.add(new GregorianCalendar(2008, 0, 4), timeLineEntries);
		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
		timeLineEntries.add(new AnnotatedTimeLineEntry(41476, "Bought Pens",
				"Bought 200k Pens")); // Sold Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(66467, "", "")); // Sold
																		// Pens

		atl.add(new GregorianCalendar(2008, 0, 5), timeLineEntries);
		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

		timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
		timeLineEntries.add(new AnnotatedTimeLineEntry(33322, "Closed Shop",
				"Had enough of pencils business")); // Sold Pencils
		timeLineEntries.add(new AnnotatedTimeLineEntry(39463, "Pens look good",
				"Swapping to pens wholesale")); // Sold Pens

		atl.add(new GregorianCalendar(2008, 0, 6), timeLineEntries);

		atl.setSizeFull();
		return atl;
	}

	public AnnotatedTimeLine drawChart1(
			Map<String, List<EndOfDayData>> instrumentMap) {

		AnnotatedTimeLine atl = new AnnotatedTimeLine();
		atl.setOption("displayAnnotations", true);

		// atl.setOption("wmode", "window");
		atl.setOption("wmode", "opaque");
		Iterator<String> instruments = instrumentMap.keySet().iterator();
		int max = 0;
		while (instruments.hasNext()) {
			String instrument = instruments.next();
			atl.addLineLabel(instrument);
			try {
				int size = instrumentMap.get(instrument).size();
				if (size > max)
					max = size;
			} catch (NullPointerException npe) {

			}
		}
		for (int i = 0; i < max; i++) {

			ArrayList<AnnotatedTimeLineEntry> timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
			EndOfDayData eod = null;
			instruments = instrumentMap.keySet().iterator();
			while (instruments.hasNext()) {
				String instrument = instruments.next();
				List<EndOfDayData> list = instrumentMap.get(instrument);
				if (i >= list.size())
					continue;
				eod = list.get(i);
				System.out.println(instrument + "\t" + eod.getDate() + "\t"
						+ eod.getStockPriceAdjClose().doubleValue());
				timeLineEntries.add(new AnnotatedTimeLineEntry(eod
						.getStockPriceAdjClose().doubleValue(), "", ""));

			}
			GregorianCalendar gc = new GregorianCalendar();
			gc.setGregorianChange(eod.getDate());
			atl.add(gc, timeLineEntries);
		}

		atl.setSizeFull();
		return atl;
		// contentPane.setSecondComponent(atl);

	}
}
