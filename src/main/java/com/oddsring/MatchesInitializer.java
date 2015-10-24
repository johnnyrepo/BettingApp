package com.oddsring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oddsring.engine.MatchCoefficientRandomizer;
import com.oddsring.service.IMatchCoefficientService;

@Component
public class MatchesInitializer implements InitializingBean {

	@Autowired
	private IMatchCoefficientService coefficientService;
	
	@Autowired
	private MatchCoefficientRandomizer randomizer;

	private boolean stop = false;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// clean all matches
		coefficientService.cleanAllMatchesAndBets();
		
		// init match coefficients
		coefficientService.initMatchesAndCoefficients();
		
		// start match win coefficients update process
		new MatchCoefficientsUpdateThread().start();
	}


	public void stopCoefficientUpdate() {
		this.stop = true;
	}
	
	private class MatchCoefficientsUpdateThread extends Thread {
		
		@Override
		public void run() {
			while (!stop) {
				coefficientService.updateCoefficientsRandomly();

				// sleep for random period of time until next round of coefficients update
				try {
					Thread.sleep(randomizer.nextSleepInterval());
				} catch (InterruptedException e) {
					// Do nothing and let continue with next iteration of the loop
					e.printStackTrace();
				}
			}
			
		}
		
	}

	
}
