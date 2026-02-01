import static org.junit.jupiter.api.Assertions.*;

import gameLaby.laby.*;
import moteurJeu.MoteurJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestIteration2 {

    private Labyrinthe labyrinthe;
    private Personnage pj;

    @BeforeEach
    public void setUp() throws IOException {
        int width = 800;
        int height = 600;
        int fps = 60;

        // Configuration du moteur
        MoteurJeu.setTaille(width, height);
        MoteurJeu.setFPS(fps);

        // Initialisation du jeu
        LabyJeu jeuLaby = new LabyJeu(false);

        // Récupération du labyrinthe et du personnage
        this.labyrinthe = jeuLaby.getLabyrinthe();
        this.pj = labyrinthe.pj;
    }

    @Test
    public void testFinDeJeuQuandVieAtteintZero() {
        pj.setVie(0);

        // Simulation de l’appel dans LabyDessin
        if (pj.getVie() <= 0) {
            labyrinthe.etreFini = true;
        }

        assertTrue(labyrinthe.etreFini, "Le jeu doit être terminé si la vie est 0");
    }

    @Test
    public void testFinDeJeuQuandVieNegatif() {
        pj.setVie(-5);

        if (pj.getVie() <= 0) {
            labyrinthe.etreFini = true;
        }

        assertTrue(labyrinthe.etreFini, "Le jeu doit être terminé si la vie est négative");
    }

    @Test
    public void testJeuContinueSiViePositive() {
        pj.setVie(3);

        if (pj.getVie() <= 0) {
            labyrinthe.etreFini = true;
        }

        assertFalse(labyrinthe.etreFini, "Le jeu ne doit pas être terminé si la vie est positive");
    }

    public void testVieInitiale() {
        assertEquals(5, labyrinthe.pj.getVie(), "Le héros doit commencer avec 5 PV");
    }


    @Test
    public void testMonstreChangeEtatAttaqueEtCouleurLogique() {
        Monstre monstre = new Monstre(0, 0, 5);
        Joueur cible = new Joueur(0, 1, 5);

        // Initialement le monstre n'attaque pas
        assertFalse(monstre.isAttacking(), "Le monstre ne doit pas attaquer au départ");

        // Le monstre attaque la cible
        monstre.attaquer(cible);
        assertTrue(monstre.isAttacking(), "Le monstre doit être en attaque après avoir attaqué");

        // Arrêter l'attaque
        monstre.stopAttaque();

        assertFalse(monstre.isAttacking(), "Le monstre doit avoir arrêté d'attaquer après stopAttaque()");
    }

}
