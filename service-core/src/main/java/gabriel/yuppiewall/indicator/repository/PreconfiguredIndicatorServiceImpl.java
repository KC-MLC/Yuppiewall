package gabriel.yuppiewall.indicator.repository;

import gabriel.yuppiewall.indicator.PreconfiguredIndicator;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;

import java.util.ArrayList;
import java.util.List;

public abstract class PreconfiguredIndicatorServiceImpl implements
		PreconfiguredIndicatorService {

	protected final List<PreconfiguredIndicator> preconfigList = new ArrayList<>();

	{
		{
			PreconfiguredIndicator config = new PreconfiguredIndicator(
					"New 52-week High");
			preconfigList.add(config);

			Expression lhs = new Expression("HIGH", "High", "1", 0,
					PERIOD.DAYS, SCAN_ON.HIGH);
			Expression rhs = new Expression("HIGH", "High", "260", 1,
					PERIOD.DAYS, SCAN_ON.HIGH);

			Condition condition = new Condition(lhs, OPERAND.GT, rhs);
			config.addCondition(condition);

		}
		{
			PreconfiguredIndicator config = new PreconfiguredIndicator(
					"New 52-week Low");
			preconfigList.add(config);

			Expression lhs = new Expression("LOW", "Low", "1", 0, PERIOD.DAYS,
					SCAN_ON.LOW);
			Expression rhs = new Expression("LOW", "Low", "260", 1,
					PERIOD.DAYS, SCAN_ON.LOW);

			Condition condition = new Condition(lhs, OPERAND.LT, rhs);
			config.addCondition(condition);

		}
		{
			PreconfiguredIndicator config = new PreconfiguredIndicator(
					"Bullish 50/200-day MA Cross");
			preconfigList.add(config);
			{
				Expression lhs = new Expression("SMA.C", "SMA", "50", 0,
						PERIOD.DAYS, SCAN_ON.CLOSING);
				Expression rhs = new Expression("SMA.C", "SMA", "200", 0,
						PERIOD.DAYS, SCAN_ON.CLOSING);

				Condition condition = new Condition(lhs, OPERAND.GT, rhs);
				config.addCondition(condition);
			}
			{
				Expression lhs = new Expression("SMA.C", "SMA", "50", 1,
						PERIOD.DAYS, SCAN_ON.CLOSING);
				Expression rhs = new Expression("SMA.C", "SMA", "200", 1,
						PERIOD.DAYS, SCAN_ON.CLOSING);

				Condition condition = new Condition(lhs, OPERAND.LT, rhs);
				config.addCondition(condition);
			}

		}

		{
			PreconfiguredIndicator config = new PreconfiguredIndicator(
					"Bearish 50/200-day MA Cross");
			preconfigList.add(config);
			{
				Expression lhs = new Expression("SMA.C", "SMA", "50", 0,
						PERIOD.DAYS, SCAN_ON.CLOSING);
				Expression rhs = new Expression("SMA.C", "SMA", "200", 0,
						PERIOD.DAYS, SCAN_ON.CLOSING);

				Condition condition = new Condition(lhs, OPERAND.LT, rhs);
				config.addCondition(condition);
			}
			{
				Expression lhs = new Expression("SMA.C", "SMA", "50", 1,
						PERIOD.DAYS, SCAN_ON.CLOSING);
				Expression rhs = new Expression("SMA.C", "SMA", "200", 1,
						PERIOD.DAYS, SCAN_ON.CLOSING);

				Condition condition = new Condition(lhs, OPERAND.GT, rhs);
				config.addCondition(condition);
			}

		}
	}

}
