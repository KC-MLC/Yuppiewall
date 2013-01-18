package gabriel.yuppiewall.indicator.service;

import gabriel.yuppiewall.indicator.Constant;
import gabriel.yuppiewall.indicator.ConstantEval;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.momentum.RSI;
import gabriel.yuppiewall.indicator.momentum.StochasticOscillator;
import gabriel.yuppiewall.indicator.trend.ExponentialMovingAverage;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.indicator.volume.AccumulationDistributionLine;

import java.util.HashMap;
import java.util.Map;

public class SimpleTechnicalIndicatorService implements
		TechnicalIndicatorService {

	private static Map<String, TechnicalIndicator> indicators = new HashMap<>();

	static {
		indicators.put("RSI", new RSI());
		indicators.put("SMA", new SimpleMovingAverage());
		indicators.put("EMA", new ExponentialMovingAverage());
		indicators.put("ADL", new AccumulationDistributionLine());
		indicators.put("OSC", new StochasticOscillator());
		indicators.put("High", new ConstantEval());
		indicators.put("Low", new ConstantEval());
		indicators.put("Close", new ConstantEval());
		indicators.put("Volume", new ConstantEval());
		indicators.put("Open", new ConstantEval());
		indicators.put("Constant", new Constant());
	}

	@Override
	public TechnicalIndicator getTechnicalIndicator(String indicator) {
		TechnicalIndicator ti = indicators.get(indicator);
		if (ti == null)
			throw new UnsupportedOperationException("No indicator found"
					+ indicator);
		return ti;
	}

}
