import static org.junit.jupiter.api.Assertions.*;
import gameLaby.laby.LabyDessin;
import gameLaby.laby.LabyJeu;
import gameLaby.laby.Monstre;
import moteurJeu.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.FileReader;
import java.util.List;

public class TestIteration1 {



    @RepeatedTest(10)  // Test répété plusieurs fois à cause du hasard
    public void testDeplacerMonstre_DeplacementValide() {
        int width = 800;
        int height = 600;
        int fps = 60;

        // Création des objets jeu et dessin
        LabyJeu jeuLaby = new LabyJeu(false);
        LabyDessin dessinLaby = new LabyDessin();

        // Configuration du moteur
        MoteurJeu.setTaille(width, height);
        MoteurJeu.setFPS(fps);


        List<Monstre> monstresAvant = jeuLaby.getLabyrinthe().getMonstres();
        int xAvant = monstresAvant.get(0).getX();
        int yAvant = monstresAvant.get(0).getY();

        jeuLaby.getLabyrinthe().deplacerMonstre();

        int xApres = monstresAvant.get(0).getX();
        int yApres = monstresAvant.get(0).getY();

        // Le monstre doit soit être resté sur place, soit s'être déplacé d'une case adjacente
        boolean deplacementValide =
                (xApres == xAvant && yApres == yAvant) ||  // pas déplacé (mur ou hasard)
                        (xApres == xAvant + 1 && yApres == yAvant) ||
                        (xApres == xAvant - 1 && yApres == yAvant) ||
                        (xApres == xAvant && yApres == yAvant + 1) ||
                        (xApres == xAvant && yApres == yAvant - 1);

        assertTrue(deplacementValide, "Le monstre s'est déplacé sur une case invalide");

        // La case d'arrivée ne doit pas être un mur
        assertFalse(jeuLaby.getLabyrinthe().getMur(xApres, yApres), "Le monstre s'est déplacé sur un mur");
    }



    @Test
    public void testMonstrePositionInitiale() {
        LabyJeu jeu = new LabyJeu(false);
        List<Monstre> monstres = jeu.getLabyrinthe().getMonstres();

        assertFalse(monstres.isEmpty(), "Le monstre n'a pas de position initiale");
    }



    @Test
    public void testMonstreSurBonneCase() {
        LabyJeu jeu = new LabyJeu(false);
        List<Monstre> monstres = jeu.getLabyrinthe().getMonstres();

        Monstre m = monstres.get(0);

        assertEquals(5, m.getX(), "Le monstre n'est pas sur la colonne attendue");
        assertEquals(2, m.getY(), "Le monstre n'est pas sur la ligne attendue");
    }

    @Test
    public void testFichierLabyrintheContientM() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("labySimple/laby1.txt"));
        br.readLine();
        br.readLine();

        String ligne;
        boolean monstreTrouve = false;
        while ((ligne = br.readLine()) != null) {
            if(ligne.contains("M")) {
                monstreTrouve = true;
                break;
            }
        }

        br.close();
        assertTrue(monstreTrouve, "Le fichier Labyrinthe ne contient pas de monstre");
    }

    @Test
    public void testMonstrePasSurPersonnage() {
        LabyJeu jeu = new LabyJeu(false);
        List<Monstre> monstres = jeu.getLabyrinthe().getMonstres();
        int x = jeu.getLabyrinthe().pj.getX();
        int y = jeu.getLabyrinthe().pj.getY();
        boolean memeCase;

        for (Monstre m : monstres) {
            memeCase = (m.getX() == x && m.getY() == y);
            assertFalse(memeCase,"Le monstre est positionné sur la même case que le personnage");
        }
    }

}