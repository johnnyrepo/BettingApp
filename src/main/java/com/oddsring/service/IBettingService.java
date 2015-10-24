package com.oddsring.service;

import java.util.List;

import com.oddsring.exception.BusinessException;
import com.oddsring.model.Match;
import com.oddsring.model.rest.BetSlip;

public interface IBettingService {

	/**
	 * Retrieves a list of all matches
	 * @return
	 */
	List<Match> retrieveAllMatches();

	/**
	 * Places a bet to selected match
	 * @param bet
	 * @param ip 
	 * @throws BusinessException 
	 */
	void placeBet(BetSlip betSlip, String ip) throws BusinessException;
	
}
