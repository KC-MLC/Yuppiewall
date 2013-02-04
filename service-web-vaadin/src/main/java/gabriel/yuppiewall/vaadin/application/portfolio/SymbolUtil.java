package gabriel.yuppiewall.vaadin.application.portfolio;

import java.util.Iterator;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

public class SymbolUtil {

	public static final Object PROPERTY_NAME = "name";

	public static IndexedContainer getSymbolContainer() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty(PROPERTY_NAME, String.class, null);

		SystemDataRepository sdr = YuppiewallUI.getInstance().getService(
				"SystemDataRepository");
		Iterator<Instrument> itr = sdr.getInstruments().iterator();
		while (itr.hasNext()) {
			Instrument inst = itr.next();
			String symbol = inst.getSymbol();
			String name = inst.getName();
			Item item = container.addItem(symbol);
			item.getItemProperty(PROPERTY_NAME).setValue(symbol + "\t" + name);

		}

		return container;
	}

}
