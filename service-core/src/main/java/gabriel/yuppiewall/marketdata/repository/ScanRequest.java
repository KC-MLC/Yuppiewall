package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ScanRequest implements Serializable {

	private Collection<Instrument> filteredResult;
	private Map<Instrument /* symbol */, List<EndOfDayData>> initialGrupedRecord;

	public ScanRequest() {
	}

	public ScanRequest(Collection<Instrument> filteredResult,
			Map<Instrument, List<EndOfDayData>> initialGrupedRecord) {
		super();
		this.filteredResult = filteredResult;
		this.initialGrupedRecord = initialGrupedRecord;
	}

	public Collection<Instrument> getFilteredResult() {
		return filteredResult;
	}

	public Map<Instrument, List<EndOfDayData>> getInitialGrupedRecord() {
		return initialGrupedRecord;
	}

	public void setInitialGrupedRecord(
			Map<Instrument, List<EndOfDayData>> initialGrupedRecord) {
		this.initialGrupedRecord = initialGrupedRecord;
	}

}
