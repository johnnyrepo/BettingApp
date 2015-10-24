package com.oddsring.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oddsring.engine.MatchCoefficientRandomizer;
import com.oddsring.model.Match;
import com.oddsring.repository.BetRepository;
import com.oddsring.repository.MatchRepository;

@Service
@Transactional
public class MatchCoefficientService implements IMatchCoefficientService {

	private static final Logger LOGGER = Logger.getLogger(MatchCoefficientService.class);

	@Autowired
	private MatchCoefficientRandomizer randomizer;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private BetRepository betRepository;

	@Override
	public void cleanAllMatchesAndBets() {
		betRepository.removeAll();
		matchRepository.removeAll();

	}

	@Override
	public void initMatchesAndCoefficients() {
		matchRepository.save(new Match("Russia - France", "1x2", new BigDecimal("1.2"), new BigDecimal("2.4"),
				new BigDecimal("3.5")));
		matchRepository.save(new Match("Germany - Italy", "1x2", new BigDecimal("1.8"), new BigDecimal("3.2"),
				new BigDecimal("5.0")));
		matchRepository.save(new Match("Spain - Portugal", "1x2", new BigDecimal("2.0"), new BigDecimal("1.0"),
				new BigDecimal("4.0")));
		matchRepository.save(new Match("Belarus - England", "1x2", new BigDecimal("3.0"), new BigDecimal("2.0"),
				new BigDecimal("1.1")));
		matchRepository.save(new Match("Netherlands - Denmark", "1x2", new BigDecimal("5.0"), new BigDecimal("3.0"),
				new BigDecimal("1.05")));
	}

	@Override
	public void updateCoefficientsRandomly() {
		List<Match> matches = matchRepository.findAll();

		for (Match match : matches) {
			if (randomizer.nextBoolean()) {
				// update match coefficient values
				if (randomizer.nextBoolean()) {
					match.setWin(randomizer.nextCoefficient());
				}

				if (randomizer.nextBoolean()) {
					match.setDraw(randomizer.nextCoefficient());
				}

				if (randomizer.nextBoolean()) {
					match.setLose(randomizer.nextCoefficient());
				}

				// save updated coefficients to repository
				matchRepository.save(match);

				LOGGER.info("Updating coefficients of the match: " + match.toString());
			}
		}
	}

}
