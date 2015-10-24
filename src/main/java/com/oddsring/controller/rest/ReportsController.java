package com.oddsring.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oddsring.exception.BusinessException;
import com.oddsring.model.Bet;
import com.oddsring.model.BetType;
import com.oddsring.service.IReportsService;

@RestController
@RequestMapping("/reports/")
public class ReportsController {

	@Autowired
	private IReportsService reportsService;
	
	@RequestMapping(value = "/bets", method = RequestMethod.GET)
	public List<Bet> bets() {
		return reportsService.retrieveAllBets();
	}
	
	@RequestMapping(value = "bets/ip/{ip:.+}", method = RequestMethod.GET)
	public List<Bet> betsByIp(@PathVariable("ip") String ip) {
		List<Bet> bets = reportsService.retrieveBetsByIp(ip);
		return bets ;
	}
	
	@RequestMapping(value = "bets/type/{type}", method = RequestMethod.GET)
	public List<Bet> betsByType(@PathVariable("type") String type) throws BusinessException {
		BetType betType = null;
		try {
			betType = BetType.valueOf(type);
		} catch(Exception e) {
			throw new BusinessException("No such BetType: " + type);
		}
		
		return reportsService.retrieveBetsByType(betType);
	}
	
	@RequestMapping(value = "bets/type/{type}/ip/{ip:.+}", method = RequestMethod.GET)
	public List<Bet> betsByTypeAndIp(@PathVariable("type") String type, @PathVariable("ip") String ip) throws BusinessException {
		BetType betType = convertToBetType(type);
		
		return reportsService.retrieveBetsByTypeAndIp(betType, ip);
	}
	
	@RequestMapping(value = "bets/type/{type}/match/{name}", method = RequestMethod.GET)
	public List<Bet> betsByTypeAndMatchName(@PathVariable("type") String type, @PathVariable("name") String name) throws BusinessException {
		BetType betType = convertToBetType(type);
		
		return reportsService.retrieveBetsByTypeAndMatchName(betType, name);
	}
	
	private BetType convertToBetType(String type) throws BusinessException {
		BetType betType = null;
		try {
			betType = BetType.valueOf(type);
		} catch(Exception e) {
			throw new BusinessException("No such BetType: " + type);
		}
		
		return betType;
	}
	
	@RequestMapping(value = "/bets/{id}", method = RequestMethod.DELETE)
	public void removeBet(@PathVariable("id") String id) throws BusinessException {
		reportsService.removeBet(id);
	}
	
//	@RequestMapping(value = "bets/", method = RequestMethod.GET)
//	public List<Bet> betsByParam(@RequestParam("type") String type, @RequestParam("match") String match) throws BusinessException {
//		
//	}
	
}
