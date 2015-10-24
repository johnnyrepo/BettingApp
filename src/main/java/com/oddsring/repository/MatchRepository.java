package com.oddsring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.oddsring.exception.TechnicalException;
import com.oddsring.model.Match;

@Repository
public class MatchRepository implements IMatchRepository {

    @Autowired
    private MongoOperations mongoOperations;
	
    public List<Match> findAll() {
		List<Match> matches = mongoOperations.findAll(Match.class);

		return matches;
    }

	public void save(Match match) {
		mongoOperations.save(match);
	}

	public void remove(Match match) {
		mongoOperations.remove(match);
	}

	@Override
	public void removeAll() {
		mongoOperations.findAllAndRemove(new Query(), Match.class);
	}
	
	@Override
	public Match findById(String id) throws TechnicalException {
		Match result = null;
		
		Query query = new Query(Criteria.where("id").is(id));
		
		List<Match> matches = mongoOperations.find(query, Match.class);
		
		if (matches.size() > 1) {
			throw new TechnicalException("More than one Match found with query by id");
		} else if (matches.size() == 1) {
			result = matches.get(0);
		}
		
		return result;
	}

}
