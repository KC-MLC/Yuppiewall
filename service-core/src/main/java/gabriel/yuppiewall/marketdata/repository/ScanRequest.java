package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Condition;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ScanRequest implements Serializable {

	private List<Condition> conditions;
	private Collection<Instrument> filteredResult;
	private Map<Instrument /* symbol */, List<EndOfDayData>> initialGrupedRecord;

	public ScanRequest() {
	}

	public ScanRequest(Collection<Instrument> filteredResult,
			Map<Instrument, List<EndOfDayData>> initialGrupedRecord) {
		this.filteredResult = filteredResult;
		this.initialGrupedRecord = initialGrupedRecord;
	}

	public ScanRequest(Collection<Instrument> filteredResult) {
		this.filteredResult = filteredResult;
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

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

}
