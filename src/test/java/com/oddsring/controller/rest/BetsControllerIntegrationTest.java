package com.oddsring.controller.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.oddsring.BettingAppApplication;
import com.oddsring.MatchesInitializer;
import com.oddsring.model.Bet;
import com.oddsring.model.Match;
import com.oddsring.model.rest.BetSlip;
import com.oddsring.repository.BetRepository;
import com.oddsring.repository.MatchRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BettingAppApplication.class)
@WebIntegrationTest({ "server.port:0" })
public class BetsControllerIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Value("${local.server.port}")
	private int port;

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	BetRepository betRepository;

	@Autowired
	MatchRepository matchRepository;

	@Before
	public void setUp() {
		MatchesInitializer bean = applicationContext.getBean(MatchesInitializer.class);
		bean.stopCoefficientUpdate();
		
		betRepository.removeAll();
		matchRepository.removeAll();

		Match match = new Match("match1", "type1", new BigDecimal("1.05"), new BigDecimal("2.05"),
				new BigDecimal("3.05"));
		Match match2 = new Match("match2", "type2", new BigDecimal("2.45"), new BigDecimal("4.44"),
				new BigDecimal("3.95"));

		matchRepository.save(match);
		matchRepository.save(match2);
	}

	@After
	public void tearDown() {
		betRepository.removeAll();
		matchRepository.removeAll();
	}

	@Test
	public void testListAllMatches() {
		String matches = restTemplate.getForObject("http://localhost:" + port + "/roster", String.class);

		// contains match1
		assertTrue(matches.contains("match1"));
		assertTrue(matches.contains("type1"));
		assertTrue(matches.contains("1.05"));
		assertTrue(matches.contains("2.05"));
		assertTrue(matches.contains("3.05"));
		// contains match 2
		assertTrue(matches.contains("match2"));
		assertTrue(matches.contains("type2"));
		assertTrue(matches.contains("2.45"));
		assertTrue(matches.contains("4.44"));
		assertTrue(matches.contains("3.95"));
	}

	@Test
	public void testBet() {
		List<Match> matches = matchRepository.findAll();

		BetSlip betSlip = new BetSlip();
		betSlip.setAmount(new BigDecimal(100));
		betSlip.setMatchId(matches.get(0).getId());
		betSlip.setName(matches.get(0).getName());
		betSlip.setOdd(matches.get(0).getWin());
		betSlip.setType("WIN");

		restTemplate.postForObject("http://localhost:" + port + "/placeBet", betSlip, String.class);

		List<Bet> bets = betRepository.findAll();

		assertEquals(1, bets.size());
		assertEquals(betSlip.getAmount(), bets.get(0).getAmount());
		assertEquals(betSlip.getMatchId(), bets.get(0).getMatchId());
		assertEquals(betSlip.getName(), bets.get(0).getName());
		assertEquals(betSlip.getOdd(), bets.get(0).getOdd());
		assertEquals(betSlip.getType(), bets.get(0).getType().toString());
		assertNotNull(bets.get(0).getIp());
		assertNotNull(bets.get(0).getTimestamp());
	}

}
