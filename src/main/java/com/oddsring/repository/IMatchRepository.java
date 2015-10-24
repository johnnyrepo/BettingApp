package com.oddsring.repository;

import java.util.List;

import com.oddsring.exception.TechnicalException;
import com.oddsring.model.Match;

public interface IMatchRepository {

	/**
	 * Returns a list of all betting rosters for specific event
	 * @return list of the matches in repo
	 */
    List<Match> findAll();
    
    /**
     * Updates match values
     * @param match a match to update
     */
	void save(Match match);
	
	/**
	 * Deletes the match
	 * @param match a match to delete
	 */
	void remove(Match match);
	
	void removeAll();

	Match findById(String name) throws TechnicalException;
	
}
