package gabriel.yuppiewall.jpa.marketdata.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:gabriel/yuppiewall/jpa/IntegrationTests-context.xml")
@Transactional
public class DomainIntegrationTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateDomainTradeDay() throws ParseException {
		Assert.assertNotNull(entityManager);
		Exchange nyse = new Exchange("NYSE");
		entityManager.persist(new JPAExchange(nyse));
		entityManager.flush();
		;
		entityManager.persist(new JPAEndOfDayData(new EndOfDayData(
				new Instrument("GOOG", new Exchange("nyse")),
				new SimpleDateFormat("dd-mm-yyyy").parse("01-01-2013"),
				new BigDecimal(2), new BigDecimal(3), new BigDecimal(4),
				new BigDecimal(6), new BigDecimal("3"), new BigDecimal(5))));
		entityManager.flush();
		try {
			entityManager
					.persist(new JPAEndOfDayData(new EndOfDayData(
							new Instrument("GOOG", new Exchange("nyse")),
							new SimpleDateFormat("dd-mm-yyyy")
									.parse("01-01-2013"), new BigDecimal(1),
							new BigDecimal(1), new BigDecimal(1),
							new BigDecimal(1), new BigDecimal("1"),
							new BigDecimal(1))));
			entityManager.flush();
			Assert.fail();
		} catch (EntityExistsException e) {
			entityManager.clear();

		}

		/*
		 * entityManager.persist(new JPAEndOfDayData(new EndOfDayData_(nyse,
		 * "GOOG", new SimpleDateFormat("dd-mm-yyyy").parse("02-01-2013"), new
		 * BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new
		 * BigDecimal(1), new BigInteger("1"), new BigDecimal(1))));
		 * entityManager.flush();
		 */

		/*
		 * entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
		 * entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
		 * entityManager.flush();
		 */
	}
}
