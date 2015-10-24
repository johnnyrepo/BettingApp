package com.oddsring.repository;

import java.util.List;

import com.oddsring.model.Bet;
import com.oddsring.model.BetType;

public interface IBetRepository {

	void save(Bet bet);

	void remove(Bet bet);

	void removeByTimestamp(String timestamp);

	void removeAll();

	List<Bet> findAll();

	List<Bet> findByIp(String ip);

	Bet findByTimestamp(String timestamp);

	List<Bet> findByMatchId(String id);

	List<Bet> findByType(BetType type);

	List<Bet> findByTypeAndIp(BetType betType, String ip);

	List<Bet> findByByTypeAndMatchName(BetType betType, String matchId);

}
