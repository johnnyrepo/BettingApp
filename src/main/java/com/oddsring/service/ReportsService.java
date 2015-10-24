package com.oddsring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oddsring.exception.BusinessException;
import com.oddsring.model.Bet;
import com.oddsring.model.BetType;
import com.oddsring.repository.IBetRepository;

@Service
@Transactional
public class ReportsService implements IReportsService {

	@Autowired
	private IBetRepository betRepository;

	@Override
	public void removeBet(String timestamp) throws BusinessException {
		Bet bet = betRepository.findByTimestamp(timestamp);
		if (bet == null) {
			throw new BusinessException("No such bet with timestamp = " + timestamp);
		}
		
		betRepository.removeByTimestamp(timestamp);
//		betRepository.remove(bet);
	}
	
	@Override
	public List<Bet> retrieveAllBets() {
		return betRepository.findAll();
	}
	
	@Override
	public List<Bet> retrieveBetsByIp(String ip) {
		return betRepository.findByIp(ip);
	}

	@Override
	public List<Bet> retrieveBetsByType(BetType type) {
		return betRepository.findByType(type);
	}

	@Override
	public List<Bet> retrieveBetsByTypeAndIp(BetType betType, String ip) {
		return betRepository.findByTypeAndIp(betType, ip);
	}

	@Override
	public List<Bet> retrieveBetsByTypeAndMatchName(BetType betType, String name) {
		return betRepository.findByByTypeAndMatchName(betType, name);
	}

}
