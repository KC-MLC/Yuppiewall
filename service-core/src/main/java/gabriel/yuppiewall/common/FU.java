package gabriel.yuppiewall.common;

import java.math.BigDecimal;

public abstract class FU {
	public final static int ROUND = BigDecimal.ROUND_HALF_UP;
	public final static int SCALE = 2;
	public final static BigDecimal U0 = (BigDecimal.valueOf(0.00))
			.setScale(SCALE);
	public final static BigDecimal U1 = (BigDecimal.valueOf(1.00))
			.setScale(SCALE);
	public final static BigDecimal H100 = (BigDecimal.valueOf(100.00))
			.setScale(SCALE);
}
