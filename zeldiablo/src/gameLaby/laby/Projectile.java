package gameLaby.laby;

/**
 * Classe représentant un projectile
 */
public class Projectile extends Element {
    /**
     * Attributs
     */
    private String direction = Labyrinthe.DROITE;

    /**
     * constructeur
     *
     * @param x position selon x
     * @param y position selon y
     * @param direction la direction du tir
     */
    public Projectile(int x, int y, String direction) {
        super(x, y);
        this.direction = direction;
    }

    /**
     * Retourne la direction du projectile
     * @return la direction
     */
    public String getDirection() {
        return direction;
    }
}
