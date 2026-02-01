import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestIteration5 {
     @Test
     public void testGeneration() throws IOException {
         Labyrinthe l1 = new Labyrinthe("labySimple/laby0.txt", true);
         Labyrinthe l2 = new Labyrinthe("labySimple/laby0.txt", true);
         System.out.println(l1);
         System.out.println(l2);
         assertNotEquals(l1.toString(), l2.toString());
     }

}
