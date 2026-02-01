package gameLaby.laby;


import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un joueur
 */
public class Joueur extends Personnage{

    /**
     * Attributs
     */
    private List<Amulette> inventaire = new ArrayList<>();
    private String derniereDirection;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Joueur(int dx, int dy, int v) {
        super(dx, dy, v);
    }

    /**
     * Enregistre la dernière action passé en paramètre
     * @param derniereDirection la dernière action
     */
    public void setDerniereDirection(String derniereDirection) {
        this.derniereDirection = derniereDirection;
    }

    /**
     * Retourne la dernière direction dans laquelle le personnage s'est déplacé
     * @return la dernière direction
     */
    public String getDerniereDirection() {
        return derniereDirection;
    }

    /**
     * retourne l'inventaire du personnage
     * @return l'inventaire
     */
    public List<Amulette> getInventaire() {
        return inventaire;
    }
}