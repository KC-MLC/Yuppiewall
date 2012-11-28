package gabriel.yuppiewall.jpa.market.domain;

import java.util.Date;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;

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
	public void testCreateDomainExchange() {
		Assert.assertNotNull(entityManager);
		entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
		entityManager.flush();
		try {
			entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
			entityManager.flush();
			Assert.fail();
		} catch (EntityExistsException e) {

		}

	}

	@Test
	public void testCreateDomainTradeDay() {
		Assert.assertNotNull(entityManager);
		Exchange_ nyse = new Exchange_("NYSE");
		entityManager.persist(new JPAExchange(nyse));
		entityManager.flush();
		entityManager.persist(new JPATradeDay(
				new TradeDay_(nyse, new Date(), 1)));
		entityManager.flush();
		try {
			entityManager.persist(new JPATradeDay(new TradeDay_(nyse,
					new Date(), 2)));
			entityManager.flush();
			Assert.fail();
		} catch (EntityExistsException e) {

		}

		/*
		 * entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
		 * entityManager.persist(new JPAExchange(new Exchange_("NYSE")));
		 * entityManager.flush();
		 */
	}
}
