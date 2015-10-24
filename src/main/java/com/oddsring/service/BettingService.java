package com.oddsring.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oddsring.exception.BusinessException;
import com.oddsring.exception.TechnicalException;
import com.oddsring.model.Bet;
import com.oddsring.model.BetType;
import com.oddsring.model.Match;
import com.oddsring.model.rest.BetSlip;
import com.oddsring.repository.BetRepository;
import com.oddsring.repository.IMatchRepository;

@Service
@Transactional
public class BettingService implements IBettingService {

	@Autowired
	private IMatchRepository matchRepository;
	
	@Autowired
	private BetRepository betRepository;
	
	@Override
	public List<Match> retrieveAllMatches() {
		return matchRepository.findAll();
	}

	@Override
	public void placeBet(BetSlip betSlip, String ip) throws BusinessException {
		try {
			Match match = matchRepository.findById(betSlip.getMatchId());
			
			validateMatch(match);
			
			validateBetSlip(betSlip, match);
			
			Bet bet = new Bet();
			bet.setMatchId(match.getId());
			bet.setAmount(betSlip.getAmount());
			bet.setName(betSlip.getName());
			bet.setOdd(betSlip.getOdd());
			bet.setType(BetType.valueOf(betSlip.getType()));
			
			// set id
			bet.setTimestamp(String.valueOf(System.currentTimeMillis()));
			// set ip
			bet.setIp(ip);
			
			betRepository.save(bet);			
		} catch (TechnicalException e) {
			throw new BusinessException("Technical Exception has occured.");
		}
	}

	private void validateMatch(Match match) throws BusinessException {
		if (match == null) {
			throw new BusinessException("No such Match!");
		}		
	}

	private void validateBetSlip(BetSlip betSlip, Match match) throws BusinessException {	
		BigDecimal matchOdd = null;
		if (betSlip.getType().equals(BetType.DRAW.toString())) {
			matchOdd = match.getDraw();
		} else if(betSlip.getType().equals(BetType.WIN.toString())) {
			matchOdd = match.getWin();
		} else if (betSlip.getType().equals(BetType.LOSE.toString())) {
			matchOdd = match.getLose();
		}
		
		if (!betSlip.getOdd().equals(matchOdd)) {
			throw new BusinessException("Match odd has changed. Place bet again!");
		}
		
		if (betSlip.getAmount().doubleValue() < 0) {
			throw new BusinessException("Negative bet value is not allowed!");
		}
	}
	
}
