package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanResult;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public List<ScanOutput> runScan(final ScanParameter param,
			final PrimaryPrincipal requester) {

		// get group filter
		ScanResult eodData = getEndOfDayDataRepository().findRecords(param);
		List<Condition> conditions = param.getConditions();

		if (conditions != null) {
			for (Condition condition : conditions) {
				setTechnicalIndicator(condition.getLhs());
				setTechnicalIndicator(condition.getRhs());
			}

			ScannerUtil.filter(conditions, eodData);
		}
		List<ScanOutput> retValue = new ArrayList<ScanOutput>(eodData
				.getFilteredResult().size());
		Iterator<String> itr = eodData.getFilteredResult().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			EndOfDayData eod = eodData.getInitialGrupedRecord().get(key).get(0);
			retValue.add(new ScanOutput(eod.getStockSymbol(), null, eod
					.getExchange().getName(), null, null, eod
					.getStockPriceOpen(), eod.getStockPriceHigh(), eod
					.getStockPriceLow(), eod.getStockPriceClose(), eod
					.getStockVolume()));
		}
		return retValue;

	}

	private void setTechnicalIndicator(Expression exp) {
		if (exp.getIndicator() == null)
			throw new UnsupportedOperationException(
					"constant value not supported");
		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(exp.getIndicator());
		exp.setTechnicalIndicator(ti);
	}

	protected abstract TechnicalIndicatorService getTechnicalIndicatorService();

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

}
