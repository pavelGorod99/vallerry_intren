package Section_8.src.practices.src.main.java.com.vallerry.blackjack;

import com.vallerry.blackjack.Suit;
import com.vallerry.blackjack.Rank;

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public static void main(String[] args) {
        Card c1 = new Card(Suit.CLUBS, Rank.KING);
        Card c2 = new Card(Suit.DIAMONDS, Rank.TEN);

        System.out.println(c1);
        System.out.println(c2);
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.toString() + " " + suit.toString();
    }
}
