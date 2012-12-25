package gabriel.yuppiewall.indicator.service;

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
		indicators.put("rsi", new RSI());
		indicators.put("sma", new SimpleMovingAverage());
		indicators.put("ema", new ExponentialMovingAverage());
		indicators.put("adl", new AccumulationDistributionLine());
		indicators.put("osc", new StochasticOscillator());
	}

	@Override
	public TechnicalIndicator getTechnicalIndicator(String indicator) {
		return indicators.get(indicator);
	}

}
