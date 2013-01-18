package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ScanRequest {

	private Collection<String /* symbol */> filteredResult;
	private Map<String /* symbol */, List<EndOfDayData>> initialGrupedRecord;

	public ScanRequest(Collection<String> filteredResult,
			Map<String, List<EndOfDayData>> initialGrupedRecord) {
		super();
		this.filteredResult = filteredResult;
		this.initialGrupedRecord = initialGrupedRecord;
	}

	public Collection<String> getFilteredResult() {
		return filteredResult;
	}

	public Map<String, List<EndOfDayData>> getInitialGrupedRecord() {
		return initialGrupedRecord;
	}

	public void setInitialGrupedRecord(
			Map<String, List<EndOfDayData>> initialGrupedRecord) {
		this.initialGrupedRecord = initialGrupedRecord;
	}

}
