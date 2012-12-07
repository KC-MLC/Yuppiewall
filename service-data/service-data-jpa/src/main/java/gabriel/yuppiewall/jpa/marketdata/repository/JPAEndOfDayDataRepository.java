package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.jpa.market.repository.TradeDayRepository;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("JPAEndOfDayDataRepository")
public class JPAEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	private JPAEODDataRepository jpaEODDataRepository;

	@Autowired
	private TradeDayRepository tradeDayRepository;

	@Override
	public void createEndOfDayData(EndOfDayData_ endOfDayData) {
		jpaEODDataRepository.saveAndFlush(new JPAEndOfDayData(endOfDayData));
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData_> list) {

		List<JPAEndOfDayData> convertedList = new ArrayList<JPAEndOfDayData>(
				list.size());
		for (EndOfDayData_ endOfDayData_ : list) {
			convertedList.add(new JPAEndOfDayData(endOfDayData_));
		}

		jpaEODDataRepository.save(convertedList);

	}

}
