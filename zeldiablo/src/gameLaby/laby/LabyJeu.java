package gameLaby.laby;

import moteurJeu.Jeu;
import moteurJeu.Clavier;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Classe représentant le jeu du labyrinthe
 */
public class LabyJeu implements Jeu {

    /**
     * Attributs
     */
    private Labyrinthe labyrinthe;

    private double cooldown = 0;
    private final double DELAI = 0.075;

    /**
     * Constructeur de LabyJeu qui charge le labyrinthe depuis un fichier
     */
    public LabyJeu(boolean aleatoire) {
        try {
            if (aleatoire) {
                this.labyrinthe = new Labyrinthe("labySimple/laby0.txt", true);
            } else {
                this.labyrinthe = new Labyrinthe("labySimple/laby2.txt", false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur de chargement du labyrinthe");
        }
    }

    /**
     * methode mise a jour du jeu
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */
    @Override
    public void update(double secondes, Clavier clavier) {
        if (!labyrinthe.etreFini) {
            cooldown -= secondes;
            if (cooldown <= 0) {
                if (clavier.haut) {
                    labyrinthe.deplacerPerso(Labyrinthe.HAUT);
                    labyrinthe.pj.setDerniereDirection(Labyrinthe.HAUT);
                }
                if (clavier.bas) {
                    labyrinthe.deplacerPerso(Labyrinthe.BAS);
                    labyrinthe.pj.setDerniereDirection(Labyrinthe.BAS);
                }
                if (clavier.gauche) {
                    labyrinthe.deplacerPerso(Labyrinthe.GAUCHE);
                    labyrinthe.pj.setDerniereDirection(Labyrinthe.GAUCHE);
                }
                if (clavier.droite) {
                    labyrinthe.deplacerPerso(Labyrinthe.DROITE);
                    labyrinthe.pj.setDerniereDirection(Labyrinthe.DROITE);
                }
                if (clavier.tir) {
                    labyrinthe.tir();
                }
                if (clavier.prendre) {
                    Amulette o;
                    if ((o = labyrinthe.objetPresent(labyrinthe.pj.getX(), labyrinthe.pj.getY())) != null){
                        labyrinthe.pj.getInventaire().add(o);
                        labyrinthe.objets.remove(o);
                    }
                }

                labyrinthe.gererTir();
                labyrinthe.deplacerMonstre();

                ArrayList<Monstre> aElim = new ArrayList<>();
                for(Monstre m : labyrinthe.getMonstres()){
                    if(m.getVie() <= 0){
                        aElim.add(m);
                    }
                }
                for(Monstre m : aElim){
                    labyrinthe.getMonstres().remove(m);
                }

                if(labyrinthe.pj.getVie() <= 0 || (labyrinthe.sortie.etrePresent(labyrinthe.pj.getX(), labyrinthe.pj.getY()) && labyrinthe.objets.isEmpty())){
                    labyrinthe.etreFini = true;
                }

                cooldown = DELAI;
            }
        }
    }

    /**
     * initialisation du jeu
     */
    @Override
    public void init() {
        System.out.println("Initialisation du jeu labyrinthe");
    }

    /**
     * verifie si le jeu est fini
     * @return booleen true si le jeu est fini
     */
    @Override
    public boolean etreFini() {
        return labyrinthe.etreFini;
    }

    /**
     * Retourne le labyrinthe
     * @return le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return labyrinthe;
    }
}