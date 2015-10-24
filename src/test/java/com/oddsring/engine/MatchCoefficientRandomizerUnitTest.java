package com.oddsring.engine;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class MatchCoefficientRandomizerUnitTest {

	MatchCoefficientRandomizer randomizer;

	@Before
	public void setUp() {
		randomizer = new MatchCoefficientRandomizer();
	}

	@Test
	public void testNextCoefficientInRange() {
		for (int i = 0; i < 10; i++) {
			BigDecimal coeff = randomizer.nextCoefficient();
			assertTrue("Coefficient has to be in range [" + MatchCoefficientRandomizer.MIN_COEFFICIENT + ", " + MatchCoefficientRandomizer.MAX_COEFFICIENT + "], but was = " + coeff,
					coeff.compareTo(MatchCoefficientRandomizer.MIN_COEFFICIENT) >= 0 && coeff.compareTo(MatchCoefficientRandomizer.MAX_COEFFICIENT) < 1);
		}
	}
	
	@Test
	public void testNextCoefficientFormat() {
		String coeffAsString = randomizer.nextCoefficient().toString();
		assertTrue("Format of coefficient value is X.XX, but actual was = " + coeffAsString, coeffAsString.substring(coeffAsString.indexOf(".")).length() == 3);
	}
	
	@Test
	public void testNextSleepIntervalInRange() {
		for (int i = 0; i < 10; i++) {
			long interval = randomizer.nextSleepInterval();
			System.out.println(interval);
			assertTrue("Slep interval has to be in range [" + MatchCoefficientRandomizer.MIN_SLEEP_INTERVAL + ", " + MatchCoefficientRandomizer.MAX_SLEEP_INTERVAL + "], but was = " + interval,
					interval >= MatchCoefficientRandomizer.MIN_SLEEP_INTERVAL && interval <= MatchCoefficientRandomizer.MAX_SLEEP_INTERVAL);
		}
	}
	
	@Test
	public void testNextBoolean() {
		randomizer.nextBoolean();
	}

}
