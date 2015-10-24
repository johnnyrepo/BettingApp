package com.oddsring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.oddsring.BettingAppApplication;
import com.oddsring.MatchesInitializer;
import com.oddsring.exception.BusinessException;
import com.oddsring.model.Bet;
import com.oddsring.model.BetType;
import com.oddsring.model.Match;
import com.oddsring.model.rest.BetSlip;
import com.oddsring.repository.BetRepository;
import com.oddsring.repository.MatchRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BettingAppApplication.class)
@Transactional
public class BettingServiceIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private IBettingService bettingService;
	
	@Autowired
	private BetRepository betRepository;

	@Autowired
	private MatchRepository matchRepository;
	
	@Before
	public void setUp() {
		MatchesInitializer bean = applicationContext.getBean(MatchesInitializer.class);
		bean.stopCoefficientUpdate();
		
		betRepository.removeAll();
		matchRepository.removeAll();

		Match match = new Match("Russia - France", "1x2", new BigDecimal("1.2"), new BigDecimal("2.4"),
				new BigDecimal("3.5"));
		Match match2 = new Match("Germany - Italy", "1x2", new BigDecimal("1.8"), new BigDecimal("3.2"),
				new BigDecimal("5.0"));

		matchRepository.save(match);
		matchRepository.save(match2);
	}
	
	@After
	public void tearDown() {
		betRepository.removeAll();
		matchRepository.removeAll();
	}
	
	@Test
	public void testRetrieveAllMatches() {
		List<Match> matches = bettingService.retrieveAllMatches();
		
		assertEquals(2, matches.size());

		assertMatch(matches.get(0), "Russia - France", "1x2", new BigDecimal("1.2"), new BigDecimal("2.4"), new BigDecimal("3.5"));
		assertMatch(matches.get(1), "Germany - Italy", "1x2", new BigDecimal("1.8"), new BigDecimal("3.2"), new BigDecimal("5.0"));
	
	}
	
	@Test
	public void testPlaceBet() throws BusinessException {
		List<Match> matches = bettingService.retrieveAllMatches();
		
		String matchId = matches.get(0).getId();
		BigDecimal odd = matches.get(0).getDraw();
		String name = matches.get(0).getName();
		
		BetSlip bet = new BetSlip();
		bet.setAmount(new BigDecimal(100));
		bet.setMatchId(matchId);
		bet.setName(name);
		bet.setOdd(odd);
		bet.setType("DRAW");
		
		String ip = "192.168.0.1";
		
		bettingService.placeBet(bet , ip);
		
		List<Bet> bets = betRepository.findByMatchId(matchId);
		assertEquals(1, bets.size());
		assertNotNull(bets.get(0).getTimestamp());
		assertEquals(new BigDecimal(100), bets.get(0).getAmount());
		assertEquals(ip, bets.get(0).getIp());
		assertEquals(matchId, bets.get(0).getMatchId());
		assertEquals(name, bets.get(0).getName());
		assertEquals(odd, bets.get(0).getOdd());
		assertEquals(BetType.DRAW, bets.get(0).getType());
	}
	
	private void assertMatch(Match match, String name, String type, BigDecimal win, BigDecimal draw, BigDecimal lose) {
		assertEquals(name, match.getName());
		assertEquals(type, match.getType());
		assertEquals(win, match.getWin());
		assertEquals(draw, match.getDraw());
		assertEquals(lose, match.getLose());
	}
	
}
