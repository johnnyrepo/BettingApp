package com.oddsring.model;

public enum BetType {

	WIN("WIN"), DRAW("DRAW"), LOSE("LOSE");
	
	private String name;
	
	private BetType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
