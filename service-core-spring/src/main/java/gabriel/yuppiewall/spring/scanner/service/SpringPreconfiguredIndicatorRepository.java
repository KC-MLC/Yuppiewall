package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.indicator.PreconfiguredIndicator;
import gabriel.yuppiewall.indicator.repository.PreconfiguredIndicatorServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("preconfiguredIndicatorService")
public class SpringPreconfiguredIndicatorRepository extends
		PreconfiguredIndicatorServiceImpl {

	@Override
	public List<PreconfiguredIndicator> getPreconfiguredIndicator() {
		return preconfigList;
	}

}
