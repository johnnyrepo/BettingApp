package com.oddsring.service;

import java.util.List;

import com.oddsring.exception.BusinessException;
import com.oddsring.model.Bet;
import com.oddsring.model.BetType;

public interface IReportsService {
	
	void removeBet(String timestamp) throws BusinessException;

	List<Bet> retrieveBetsByIp(String ip);
	
	List<Bet> retrieveBetsByType(BetType type);
	
	List<Bet> retrieveAllBets();

	List<Bet> retrieveBetsByTypeAndIp(BetType betType, String ip);

	List<Bet> retrieveBetsByTypeAndMatchName(BetType betType, String matchId);
	
}
