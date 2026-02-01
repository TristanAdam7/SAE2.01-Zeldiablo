import moteurJeu.Clavier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import gameLaby.laby.*;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class TestIteration4Fonct64 {

    private LabyJeu jeu;
    private Labyrinthe laby;
    private Clavier clavier;

    @BeforeEach
    void setup() {
        jeu = new LabyJeu(false);
        laby = jeu.getLabyrinthe();
        clavier = new Clavier();

        // Positionne le joueur et la sortie au même endroit
        laby.pj.setX(1);
        laby.pj.setY(1);
        laby.sortie.setX(1);
        laby.sortie.setY(1);

        // Supprime tous les objets (y compris l’amulette)
        laby.getObjets().clear();
    }

    @Test
    void testVictoireQuandJoueurSurSortieEtAmuletteRecuperee() {
        LabyDessin dessin = new LabyDessin();
        Canvas canvas = new Canvas(100, 100);
        dessin.dessinerJeu(jeu, canvas);
        jeu.update(0.3, clavier);

        assertTrue(laby.etreFini, "Le jeu doit se finir si le joueur a l’amulette et est sur la sortie");
    }

    @Test
    void testPasDeVictoireSansAmulette() {
        laby.getObjets().add(new Amulette(2, 2)); // L’amulette est encore sur le plateau

        LabyDessin dessin = new LabyDessin();
        Canvas canvas = new Canvas(100, 100);
        dessin.dessinerJeu(jeu, canvas);

        assertFalse(laby.etreFini, "Le jeu ne doit pas finir si l’amulette est encore présente");
    }

    @Test
    void testPasDeVictoireSiPasSurLaSortie() {
        laby.sortie.setX(5);
        laby.sortie.setY(5); // La sortie est ailleurs

        LabyDessin dessin = new LabyDessin();
        Canvas canvas = new Canvas(100, 100);
        dessin.dessinerJeu(jeu, canvas);

        assertFalse(laby.etreFini, "Le jeu ne doit pas finir si le joueur n’est pas sur la sortie");
    }
}
