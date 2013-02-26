package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.Tuple;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public List<ScanOutput> runScan(final ScanParameter param,
			final PrimaryPrincipal requester) {

		List<Condition> conditions = param.getConditions();
		if (conditions == null) {
			conditions = new ArrayList<>();
			param.setConditions(conditions);
		}
		//
		Condition avePriceConition = param.getGlobalFilter().getAvgPrice();

		if (avePriceConition != null) {
			conditions.add(avePriceConition);
		}
		Condition aveVolConition = param.getGlobalFilter().getAvgVolue();
		if (aveVolConition != null) {
			conditions.add(aveVolConition);
		}
		// get group filter
		validateGroupFilter(param.getGlobalFilter());
		validateCondition(param.getConditions());

		GlobalFilter gfilter = param.getGlobalFilter();
		Tuple<String, String> group = gfilter.getGroup();
		ScanRequest sr = new ScanRequest();
		sr.setConditions(param.getConditions());
		String key = group.getKey();
		Set<String> exchanges = new HashSet<>();
		if ("exchange".equals(key)) {
			exchanges.add(group.getValue());
		} else if ("country".equals(key)) {

			List<Exchange> exchangeList = getSystemDataRepository()
					.getExchangeByCountryCode(group.getValue());
			for (Exchange exchange : exchangeList) {
				exchanges.add(exchange.getSymbol());
			}

		}
		sr.setExchanges(exchanges);
		List<ScanOutput> result = getScanRunner().runScan(sr);
		SystemDataRepository sdr = getSystemDataRepository();
		for (ScanOutput scanOutput : result) {
			Instrument inst = scanOutput.getEod().getInstrument();
			inst = sdr.getInstrument(inst);
			scanOutput.setInstrument(inst);
		}
		return result;
	}

	private void validateCondition(List<Condition> conditions) {
		// TODO Auto-generated method stub

	}

	private void validateGroupFilter(GlobalFilter gfilter) {

		if (gfilter == null)
			throw new MissingRequiredFiledException("Missing Global Filter");
		Tuple<String, String> group = gfilter.getGroup();
		if (group == null)
			throw new MissingRequiredFiledException("Missing Global Filter");
		String key = group.getKey();
		if (!("country".equals(key) || "exchange".equals(key))) {

			throw new InvalidParameterValueException(key
					+ " Fileter Not supported");
		}

	}

	protected abstract ScanRunner getScanRunner();

	protected abstract SystemDataRepository getSystemDataRepository();

}
