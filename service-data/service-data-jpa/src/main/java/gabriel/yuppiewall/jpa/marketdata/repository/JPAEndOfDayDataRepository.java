package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.market.domain.JPAInstrument;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("JPAEndOfDayDataRepository")
public class JPAEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	private JPAEODDataRepository jpaEODDataRepository;

	@Autowired
	@Qualifier("SystemDataRepository")
	private SystemDataRepository marketMetaRepository;

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		jpaEODDataRepository.saveAndFlush(new JPAEndOfDayData(endOfDayData));
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {

		List<JPAEndOfDayData> convertedList = new ArrayList<JPAEndOfDayData>(
				list.size());

		for (EndOfDayData endOfDayData_ : list) {
			convertedList.add(new JPAEndOfDayData(endOfDayData_));
			/*
			 * jpaEODDataRepository.save(new JPAEndOfDayData(endOfDayData_)); if
			 * (++counter == 20) { jpaEODDataRepository.flush(); counter = 0; }
			 */
		}
		/*
		 * if (counter != 0) jpaEODDataRepository.flush();
		 */
		jpaEODDataRepository.save(convertedList);
		// jpaEODDataRepository.flush();

	}

	@Override
	public List<EndOfDayData> findAllEndOfDayData(Instrument instrument,
			Filter<EndOfDayData> ignore) {
		List<JPAEndOfDayData> list = jpaEODDataRepository
				.findAllEndOfDayData(new JPAInstrument(instrument));
		List<EndOfDayData> returnValue = new ArrayList<>();
		for (JPAEndOfDayData jpaEndOfDayData : list) {
			returnValue.add(jpaEndOfDayData.getEndOfDayData());
		}

		return returnValue;
	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instruments, int offset, int start) {
		// TODO Auto-generated method stub
		return null;
	}

}
