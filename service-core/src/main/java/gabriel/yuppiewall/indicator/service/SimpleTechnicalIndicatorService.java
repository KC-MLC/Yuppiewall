package gabriel.yuppiewall.indicator.service;

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
	}

	@Override
	public TechnicalIndicator getTechnicalIndicator(String indicator) {
		return indicators.get(indicator);
	}

}
