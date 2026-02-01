import gameLaby.laby.*;
import moteurJeu.Clavier;
import moteurJeu.MoteurJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestIteration3 {
    private Labyrinthe laby;
    private Joueur pj;

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
        this.laby = jeuLaby.getLabyrinthe();
        this.pj = laby.pj;
    }

    @Test
    public void testAmuletteEstPlaceeSurCaseVide() {
        laby.murs[2][2] = false;
        Amulette amulette = new Amulette(2, 2);
        laby.objets.add(amulette);

        // On marque la case comme vide

        assertFalse(laby.getMur(2, 2), "La case doit être vide.");
        assertTrue(laby.objets.contains(amulette), "L'amulette doit être présente dans les objets.");
    }

    @Test
    public void testAmulettePeutPartagerCaseAvecPJ() {
        int x = laby.pj.getX();
        int y = laby.pj.getY();
        laby.objets.add(new Amulette(x, y));

        assertNotNull(laby.objetPresent(x, y), "L'amulette doit être sur la même case que le PJ.");
    }

    @Test
    public void testAmulettePeutPartagerCaseAvecMonstre() {
        Monstre monstre = new Monstre(3, 3, 100);
        laby.monstres.add(monstre);
        laby.objets.add(new Amulette(3, 3));

        assertEquals(monstre, laby.monstrePresent(3, 3), "Le monstre doit être sur cette position");
        assertNotNull(laby.objetPresent(3, 3), "L'amulette doit être sur la même case que le monstre.");
    }

    @Test
    public void testTirInitialiseDansLaBonneDirection() {
        laby.pj.setDerniereDirection(Labyrinthe.DROITE);
        laby.tir();

        assertEquals(1, laby.listProjectile.size());
        Projectile p = laby.listProjectile.get(0);
        assertEquals(17, p.getX());
        assertEquals(12, p.getY());
        assertEquals(Labyrinthe.DROITE, p.getDirection());
    }

    @Test
    public void testProjectileAvanceCorrectement() {
        laby.pj.setDerniereDirection(Labyrinthe.GAUCHE);
        laby.tir();
        laby.gererTir();

        Projectile p = laby.listProjectile.get(0);
        assertEquals(16, p.getX());
        assertEquals(12, p.getY());
    }

    @Test
    public void testProjectileDisparaitQuandMur() {
        laby.murs[6][5] = true;
        laby.pj.setDerniereDirection(Labyrinthe.DROITE);
        laby.tir();

        laby.gererTir();
        assertTrue(laby.listProjectile.isEmpty(), "Le projectile devrait disparaître en touchant un mur.");
    }

    @Test
    public void testProjectileDisparaitEtBlesseMonstre() {
        Monstre monstre = new Monstre(6, 5, 3);
        laby.monstres.add(monstre);

        laby.pj.setX(5);
        laby.pj.setY(5);

        laby.pj.setDerniereDirection(Labyrinthe.DROITE);
        laby.tir();
        laby.gererTir();

        assertTrue(laby.listProjectile.isEmpty(), "Le projectile devrait disparaître après avoir touché le monstre.");
        assertEquals(2, monstre.getVie(), "Le monstre devrait perdre 1 point de vie.");
    }

    @Test
    public void testProjectileContinueTantQuePasObstacle() {
        laby.pj.setDerniereDirection(Labyrinthe.GAUCHE);
        laby.tir();

        // avance 3 fois
        laby.gererTir();
        laby.gererTir();
        laby.gererTir();

        Projectile p = laby.listProjectile.get(0);
        assertEquals(14, p.getX());
        assertEquals(12, p.getY());
    }

    @Test
    public void testTirDansMurImmediateDisparait() {
        laby.pj.setX(1);
        laby.pj.setY(2);
        laby.pj.setDerniereDirection(Labyrinthe.GAUCHE);
        laby.tir();
        laby.gererTir();
        assertTrue(laby.listProjectile.isEmpty(), "Le projectile devrait disparaître sans avancer.");
    }

}
