package com.oddsring.controller.rest;

import static org.junit.Assert.assertEquals;

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
import com.oddsring.model.BetType;
import com.oddsring.repository.BetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BettingAppApplication.class)
@WebIntegrationTest({ "server.port:0" })
public class ReportsControllerIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Value("${local.server.port}")
	private int port;

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	BetRepository betRepository;

	@Before
	public void setUp() {
		MatchesInitializer bean = applicationContext.getBean(MatchesInitializer.class);
		bean.stopCoefficientUpdate();
		
		betRepository.removeAll();
	}

	@After
	public void tearDown() {
		betRepository.removeAll();
	}

	@Test
	public void testNoBets() {
		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets", String.class);

		assertEquals("[]", bets);
	}

	@Test
	public void testOneBet() {

		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId");
		bet.setName("matchName");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets", String.class);

		assertEquals(
				"[{\"timestamp\":\"12345\",\"matchId\":\"matchId\",\"name\":\"matchName\",\"type\":\"DRAW\",\"odd\":3.3,\"amount\":100,\"ip\":\"127.0.0.1\"}]",
				bets);
	}

	@Test
	public void testBetsByIp() {
		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId1");
		bet.setName("matchName1");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/ip/127.0.0.1",
				String.class);

		assertEquals(
				"[{\"timestamp\":\"12345\",\"matchId\":\"matchId1\",\"name\":\"matchName1\",\"type\":\"DRAW\",\"odd\":3.3,\"amount\":100,\"ip\":\"127.0.0.1\"}]",
				bets);
	}

	@Test
	public void testBetsByType() {
		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId1");
		bet.setName("matchName1");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		Bet bet2 = new Bet();
		bet2.setAmount(new BigDecimal(200));
		bet2.setIp("127.0.0.2");
		bet2.setMatchId("matchId2");
		bet2.setName("matchName2");
		bet2.setOdd(new BigDecimal("4.4"));
		bet2.setType(BetType.WIN);
		bet2.setTimestamp("54321");

		betRepository.save(bet2);

		// DRAW
		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/type/DRAW", String.class);

		assertEquals(
				"[{\"timestamp\":\"12345\",\"matchId\":\"matchId1\",\"name\":\"matchName1\",\"type\":\"DRAW\",\"odd\":3.3,\"amount\":100,\"ip\":\"127.0.0.1\"}]",
				bets);

		// WIN
		bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/type/WIN", String.class);

		assertEquals(
				"[{\"timestamp\":\"54321\",\"matchId\":\"matchId2\",\"name\":\"matchName2\",\"type\":\"WIN\",\"odd\":4.4,\"amount\":200,\"ip\":\"127.0.0.2\"}]",
				bets);
	}

	@Test
	public void testBetsByTypeAndIp() {
		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId1");
		bet.setName("matchName1");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		Bet bet2 = new Bet();
		bet2.setAmount(new BigDecimal(200));
		bet2.setIp("127.0.0.2");
		bet2.setMatchId("matchId2");
		bet2.setName("matchName2");
		bet2.setOdd(new BigDecimal("4.4"));
		bet2.setType(BetType.WIN);
		bet2.setTimestamp("54321");

		betRepository.save(bet2);

		// 127.0.0.1
		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/type/DRAW/ip/127.0.0.1",
				String.class);

		assertEquals(
				"[{\"timestamp\":\"12345\",\"matchId\":\"matchId1\",\"name\":\"matchName1\",\"type\":\"DRAW\",\"odd\":3.3,\"amount\":100,\"ip\":\"127.0.0.1\"}]",
				bets);

		// 127.0.0.2
		bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/type/WIN/ip/127.0.0.2",
				String.class);

		assertEquals(
				"[{\"timestamp\":\"54321\",\"matchId\":\"matchId2\",\"name\":\"matchName2\",\"type\":\"WIN\",\"odd\":4.4,\"amount\":200,\"ip\":\"127.0.0.2\"}]",
				bets);
	}

	@Test
	public void testBetsByTypeAndMatchName() {
		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId1");
		bet.setName("matchName1");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		Bet bet2 = new Bet();
		bet2.setAmount(new BigDecimal(200));
		bet2.setIp("127.0.0.2");
		bet2.setMatchId("matchId2");
		bet2.setName("matchName2");
		bet2.setOdd(new BigDecimal("4.4"));
		bet2.setType(BetType.WIN);
		bet2.setTimestamp("54321");

		betRepository.save(bet2);

		String bets = restTemplate.getForObject("http://localhost:" + port + "/reports/bets/type/DRAW/match/matchName1",
				String.class);

		assertEquals(
				"[{\"timestamp\":\"12345\",\"matchId\":\"matchId1\",\"name\":\"matchName1\",\"type\":\"DRAW\",\"odd\":3.3,\"amount\":100,\"ip\":\"127.0.0.1\"}]",
				bets);
	}

	@Test
	public void testRemoveBet() {
		Bet bet = new Bet();
		bet.setAmount(new BigDecimal(100));
		bet.setIp("127.0.0.1");
		bet.setMatchId("matchId1");
		bet.setName("matchName1");
		bet.setOdd(new BigDecimal("3.3"));
		bet.setType(BetType.DRAW);
		bet.setTimestamp("12345");

		betRepository.save(bet);

		restTemplate.delete("http://localhost:" + port + "/reports/bets/" + 12345);

		List<Bet> bets = betRepository.findAll();

		assertEquals(0, bets.size());
	}

}
