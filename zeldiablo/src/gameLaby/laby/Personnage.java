package gameLaby.laby;

/**
 * Classe représentant un personnage
 */
public abstract class Personnage extends Element {
    /**
     * position du perso
     */
    private int vie;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Personnage(int dx, int dy, int v) {
        super(dx, dy);

        if (v <= 0){
            this.vie = 1;
        } else {
            this.vie = v;
        }
    }

    /**
     * permet de faire subir des dégâts au personnage
     * @param degats nombre de dégâts
     */
    public void subirDegat(int degats){
        if (this.vie-degats < 0) {
            this.vie = 0;
        } else {
            this.vie -= degats;
        }
    }

    /**
     * retourne la vie du personnage
     * @return la vie du personnage
     */
    public int getVie(){
        return this.vie;
    }

    /**
     * Initialise la vie du personnage
     * @param i le nombre de points de vie
     */
    public void setVie(int i) {
        this.vie = i;
    }
}
