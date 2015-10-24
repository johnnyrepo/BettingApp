package com.oddsring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.oddsring.model.Bet;
import com.oddsring.model.BetType;

@Repository
public class BetRepository implements IBetRepository {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void save(Bet bet) {
		mongoOperations.save(bet);
	}

	@Override
	public void remove(Bet bet) {
		mongoOperations.remove(bet);
	}

	@Override
	public void removeByTimestamp(String timestamp) {
		Query query = new Query(Criteria.where("timestamp").is(timestamp));

		mongoOperations.remove(query, Bet.class);
	}

	@Override
	public void removeAll() {
		mongoOperations.findAllAndRemove(new Query(), Bet.class);
	}

	@Override
	public List<Bet> findAll() {
		return mongoOperations.findAll(Bet.class);
	}

	@Override
	public List<Bet> findByIp(String ip) {
		Query query = new Query(Criteria.where("ip").is(ip));

		return mongoOperations.find(query, Bet.class);
	}

	@Override
	public Bet findByTimestamp(String timestamp) {
		Query query = new Query(Criteria.where("timestamp").is(timestamp));

		return mongoOperations.findOne(query, Bet.class);
	}

	public List<Bet> findByMatchId(String matchId) {
		Query query = new Query(Criteria.where("matchId").is(matchId));

		return mongoOperations.find(query, Bet.class);
	}

	@Override
	public List<Bet> findByType(BetType type) {
		Query query = new Query(Criteria.where("type").is(type.toString()));

		return mongoOperations.find(query, Bet.class);
	}

	@Override
	public List<Bet> findByTypeAndIp(BetType type, String ip) {
		Query query = new Query(Criteria.where("type").is(type.toString()).and("ip").is(ip));

		return mongoOperations.find(query, Bet.class);
	}

	@Override
	public List<Bet> findByByTypeAndMatchName(BetType betType, String name) {
		Query query = new Query(Criteria.where("type").is(betType.toString()).and("name").is(name));

		return mongoOperations.find(query, Bet.class);
	}

}
