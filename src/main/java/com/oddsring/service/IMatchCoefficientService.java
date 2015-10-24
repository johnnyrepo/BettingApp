package com.oddsring.service;

public interface IMatchCoefficientService {
	
	/**
	 * Removes all the matches from repository
	 */
	void cleanAllMatchesAndBets();
	
	/**
	 * Initializes win coefficients of all the matches
	 */
	void initMatchesAndCoefficients();
	
	/**
	 * Start infinite loop of updating of the coefficients of the matches present in database in random order
	 */
	void updateCoefficientsRandomly();
	
}
