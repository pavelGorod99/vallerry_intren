package Section_8.src.practices.src.test.java.com.vallerry.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.vallerry.blackjack.Suit;

class SuitTest {

    @Test
    void printHeart() {
        Suit.HEARTS.toString();
        assertEquals("\u2665", Suit.HEARTS.toString() );
    }
}