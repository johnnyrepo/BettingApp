package com.oddsring.model.rest;

import java.math.BigDecimal;

public class BetSlip {
	
	private String matchId;
	
	private String name;
	
	private String type;
	
	private BigDecimal odd;
	
	private BigDecimal amount;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getOdd() {
		return odd;
	}

	public void setOdd(BigDecimal odd) {
		this.odd = odd;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
