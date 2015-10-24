package com.oddsring.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oddsring.exception.BusinessException;
import com.oddsring.model.Match;
import com.oddsring.model.rest.BetSlip;
import com.oddsring.service.IBettingService;

@RestController
public class BetsController {

	@Autowired
	private IBettingService bettingService;

	@RequestMapping(value = "/roster", method = RequestMethod.GET)
	public List<Match> listAllMatches() {
		return bettingService.retrieveAllMatches();
	}
	
	@RequestMapping(value = "/placeBet", method = RequestMethod.POST)
	public void placeBet(@RequestBody BetSlip betSlip, HttpServletRequest request) throws BusinessException {				
		bettingService.placeBet(betSlip, request.getRemoteAddr());
	}
	
}
