package Section_8.src.practices.src.test.java.com.vallerry.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.vallerry.blackjack.Rank;

class RankTest {

    @Test
    void getValueOfKing() {
        assertEquals(10, Rank.KING.getValue());
    }
}