package com.oddsring.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * A thread-safe helper class for getting different randomized 
 * parameters needed to update win coefficients of the matches 
 * 
 * @author evgenius
 */
@Component
public class MatchCoefficientRandomizer {

	public static final BigDecimal MIN_COEFFICIENT = new BigDecimal("1.00");
	
	public static final BigDecimal MAX_COEFFICIENT = new BigDecimal("5.00");
	
	public static final long MIN_SLEEP_INTERVAL = 2000L;
	
	public static final long MAX_SLEEP_INTERVAL = 5000L;
	
	private Random random = new Random(System.currentTimeMillis());
	
	/**
	 * Return next random match win coefficient within 
	 * range [{@link #MIN_COEFFICIENT}, {@link MatchCoefficientRandomizer#MAX_COEFFICIENT}]
	 * 
	 * @return value in BigDecimal with 2 decimal digits
	 */
	public synchronized BigDecimal nextCoefficient() {
		BigDecimal nextNumber = MAX_COEFFICIENT.subtract(MIN_COEFFICIENT).multiply(BigDecimal.valueOf(random.nextDouble())).add(MIN_COEFFICIENT);
		
		return nextNumber.setScale(2, RoundingMode.UP);
	}
	
	/**
	 * Return next random boolean value
	 * 
	 * @return boolean value
	 */
	public synchronized boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Returns randomized thread sleep interval 
	 * in range [{@link #MIN_SLEEP_INTERVAL}, {@link MatchCoefficientRandomizer#MAX_SLEEP_INTERVAL}]
	 * 
	 * @return value in milliseconds
	 */
	public synchronized long nextSleepInterval() {
		return (long)((MAX_SLEEP_INTERVAL - MIN_SLEEP_INTERVAL) * random.nextDouble()) + MIN_SLEEP_INTERVAL;
	}
	
}
