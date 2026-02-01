import gameLaby.laby.*;
import moteurJeu.Clavier;
import moteurJeu.MoteurJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestIteration4Fonct63 {
    private LabyJeu jeu;
    private Labyrinthe laby;
    private Joueur joueur;
    private Amulette amulette;
    private Clavier clavier;

    @BeforeEach
    void setup() {
        // Initialiser un jeu sans fichier avec un labyrinthe factice
        jeu = new LabyJeu(false); // On suppose un LabyJeu modifiable pour tests
        laby = jeu.getLabyrinthe();
        joueur = laby.pj;
        clavier = new Clavier();

        // Placer joueur et amulette au même endroit
        joueur.setX(2);
        joueur.setY(2);
        amulette = new Amulette(2, 2);

        laby.objets.add(amulette);
    }

    @Test
    void testRamasserAmuletteQuandToucheEAppuyee() {
        clavier.prendre = true;
        jeu.update(0.3, clavier); // cooldown < 0

        assertTrue(joueur.getInventaire().contains(amulette), "L’amulette doit être dans l’inventaire du joueur");
        assertFalse(laby.getObjets().contains(amulette), "L’amulette ne doit plus être sur le plateau");
    }

    @Test
    void testNeRamassePasAmuletteSiToucheENonAppuyee() {
        clavier.prendre = false;
        jeu.update(0.3, clavier);

        assertFalse(joueur.getInventaire().contains(amulette), "Le joueur ne doit pas avoir l’amulette si E n’est pas pressée");
        assertTrue(laby.getObjets().contains(amulette), "L’amulette doit rester sur le plateau si E n’est pas pressée");
    }

    @Test
    void testNeRamassePasSiPasSurAmulette() {
        joueur.setX(1);
        joueur.setY(1);
        clavier.prendre = true;

        jeu.update(0.3, clavier);

        assertFalse(joueur.getInventaire().contains(amulette), "Le joueur ne doit pas ramasser si pas sur l’amulette");
        assertTrue(laby.getObjets().contains(amulette), "L’amulette doit rester sur le plateau");
    }
}
