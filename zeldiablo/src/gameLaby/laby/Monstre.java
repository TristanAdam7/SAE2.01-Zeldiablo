package gameLaby.laby;

import javafx.scene.paint.Color;

/**
 * Classe représentant un monstre
 */
public class Monstre extends Personnage{

    /**
     * Attributs
     */
    private boolean attaque;
    private Color couleur = Color.BLACK;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Monstre(int dx, int dy, int v) {
        super(dx, dy, v);
    }

    /**
     * Méthode pour attaquer
     *
     * @param cible cible de l'attaque
     */
    public void attaquer(Personnage cible) {
        cible.subirDegat(1);
        this.attaque = true;
        couleur = Color.RED;
    }

    /**
     * Méthode qui test si le personnage envoyé en paramètre se trouve à côté du monstre
     *
     * @param p le personnage
     * @return true si le monstre est à côté du personnage, false sinon
     */
    public boolean estAdjacent(Personnage p) {
        if ((Math.abs(super.getX() - p.getX()) <= 1 && Math.abs(super.getY() - p.getY()) <= 1) && (super.getX() == p.getX() || super.getY() == p.getY())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Arrête l'attaque
     */
    public void stopAttaque(){
        this.attaque = false;
        couleur = Color.RED;
    }

    /**
     * Test si le monstre est entrain d'attaquer
     * @return true si le monstre attaque, false sinon
     */
    public boolean isAttacking() {
        return this.attaque;
    }

    public Color getCouleur() {
        return couleur;
    }
}