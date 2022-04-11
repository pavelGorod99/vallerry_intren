package com.vallerry.blackjack;

public enum Suit {
    CLUBS('\u2663'),
    DIAMONDS('\u2666'),
    HEARTS('\u2665'),
    SPADES('\u2660');

    private char symbol;
    private String strSymbol;

    Suit(char symbol) {
        this.symbol = symbol;
    }
    Suit(String symbol) {this.strSymbol = symbol;}

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}
