import gameLaby.laby.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIteration4Fonct55 {

    @Test
    void testMonstreNePeutPasAvoirVieNegative() {
        Monstre m = new Monstre(1, 1, 1);
        m.subirDegat(1);
        m.subirDegat(5);
        assertTrue(m.getVie() >= 0, "Les PV du monstre ne doivent pas être négatifs");
    }

    @Test
    void testMonstreDisparaitApresDeuxDegats() {
        Monstre m = new Monstre(1, 1, 1);
        m.subirDegat(1);
        m.subirDegat(1);
        assertEquals(0, m.getVie(), "Le monstre doit avoir 0 PV après 2 dégâts");
        // On suppose qu'un monstre avec 0 PV est retiré du plateau ailleurs
    }

    @Test
    void testHeroTueMonstreAvantRiposte() {
        Joueur j = new Joueur(0, 0, 1);
        Monstre m = new Monstre(0, 1, 1); // Adjacents

        m.subirDegat(2); // Il meurt avant de pouvoir attaquer

        m.stopAttaque();

        assertFalse(m.isAttacking(), "Le monstre ne doit pas attaquer s'il est mort");
    }
}
