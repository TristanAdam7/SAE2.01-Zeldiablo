package gameLaby.laby;

/**
 * Classe représentant un élément dans le jeu
 */
public abstract class Element {
    /**
     * Attributs
     */
    private int x, y;

    /**
     * Constructeur
     *
     * @param dx abscisse
     * @param dy ordonnée
     */
    public Element(int dx, int dy) {
        this.x = dx;
        this.y = dy;
    }

    /**
     * permet de savoir si le perso est en x,y
     *
     * @param dx position testee
     * @param dy position testee
     * @return true si le perso est bien en (dx,dy)
     */
    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    /**
     * @return position x du perso
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return position y du perso
     */
    public int getY() {
        return this.y;
    }

    /**
     * met a jour la variable x
     *
     * @param x l'abscisse de l'élément
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * met a jour la variable y
     *
     * @param y l'ordonnée de l'élément
     */
    public void setY(int y) {
        this.y = y;
    }
}
