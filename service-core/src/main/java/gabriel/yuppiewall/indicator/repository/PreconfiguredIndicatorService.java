package gabriel.yuppiewall.indicator.repository;

import gabriel.yuppiewall.indicator.PreconfiguredIndicator;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.util.List;

public interface PreconfiguredIndicatorService {

	List<PreconfiguredIndicator> getPreconfiguredIndicator();

}
