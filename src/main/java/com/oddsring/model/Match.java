package com.oddsring.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Match.COLLECTION_NAME)
public class Match {
	
	public final static String COLLECTION_NAME = "matches";

	@Id
	private String id;
	
	private String name;
		
	private String type;
	
	private BigDecimal win;
	
	private BigDecimal draw;
	
	private BigDecimal lose;
	
//	private List<Bet> bets;

	public Match() {

	}
	
	public Match(String name, String type, BigDecimal win,
			BigDecimal draw, BigDecimal lose) {
		this.name = name;
		this.type = type;
		this.win = win;
		this.draw = draw;
		this.lose = lose;
//		this.bets = bets;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BigDecimal getWin() {
		return win;
	}

	public void setWin(BigDecimal win) {
		this.win = win;
	}

	public BigDecimal getDraw() {
		return draw;
	}

	public void setDraw(BigDecimal draw) {
		this.draw = draw;
	}

	public BigDecimal getLose() {
		return lose;
	}

	public void setLose(BigDecimal lose) {
		this.lose = lose;
	}

//	public List<Bet> getBets() {
//		return bets;
//	}
//
//	public void setBets(List<Bet> bets) {
//		this.bets = bets;
//	}
	
}
