package gameLaby.laby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * classe labyrinthe. represente un labyrinthe avec
 * <ul> des murs </ul>
 * <ul> un personnage (x,y) </ul>
 */
public class Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char VIDE = '.';
    public static final char MONSTRE = 'M';
    public static final char AMULETTE = 'A';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";

    /**
     * attribut du personnage
     */
    public Joueur pj;
    public Sortie sortie;
    public ArrayList<Monstre> monstres = new ArrayList<>();
    public ArrayList<Amulette> objets = new ArrayList<>();
    public ArrayList<Projectile> listProjectile = new ArrayList<>();
    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;

    /**
     * fin du jeu
     */
    public boolean etreFini = false;

    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on augmente colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        int[] res = {x, y};
        return res;
    }

    /**
     * Constructeur pour les tests
     *
     * @param largeur largeur de la fenetre
     * @param hauteur hauteur de la fenetre
     */
    public Labyrinthe(int largeur, int hauteur) {
        this.murs = new boolean[largeur][hauteur];
        this.monstres = new ArrayList<>();
        this.objets = new ArrayList<>();
        this.listProjectile = new ArrayList<>();
        this.pj = null;
        this.sortie=null;
        this.etreFini = false;
    }


    /**
     * charge le labyrinthe
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom, boolean aleatoire) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        this.murs = new boolean[nbColonnes][nbLignes];
        this.pj = null;

        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        this.murs[colonne][numeroLigne] = true;
                        break;
                    case VIDE:
                        this.murs[colonne][numeroLigne] = false;
                        break;
                    case PJ:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute PJ
                        this.pj = new Joueur(colonne, numeroLigne, 5);
                        if (this.sortie==null){
                            this.sortie=new Sortie(colonne, numeroLigne);
                        }
                        break;
                    case MONSTRE:
                        this.murs[colonne][numeroLigne] = false;
                        if (this.pj == null || !this.pj.etrePresent(colonne,numeroLigne)) { // permet de s'assurer qu'un personnage n'est pas déjà présent sur la position que va occuper le monstre
                            this.monstres.add(new Monstre(colonne, numeroLigne, 2));
                        }
                        break;
                    case AMULETTE:
                        this.murs[colonne][numeroLigne] = false;
                        this.objets.add(new Amulette(colonne, numeroLigne));
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            if (aleatoire) {
                boolean[][] visite = new boolean[nbColonnes][nbLignes];

                // Initialisation : toutes les cases sont des murs
                for (int x = 0; x < nbColonnes; x++) {
                    for (int y = 0; y < nbLignes; y++) {
                        murs[x][y] = true;
                    }
                }

                // Lancement de la génération depuis une cellule impaire
                genererDFS(1, 1, visite, nbColonnes, nbLignes);
            }


            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        // ferme fichier
        bfRead.close();
    }

    private void genererDFS(int x, int y, boolean[][] visite, int nbColonnes, int nbLignes) {
        visite[x][y] = true;  // on marque cette case comme visitée
        murs[x][y] = false;   // on enlève le mur sur cette case pour la rendre accessible

        // Liste des directions possibles (déplacements de 2 cases)
        int[][] directions = {
                {2, 0}, {-2, 0}, {0, 2}, {0, -2}
        };

        List<int[]> dirs = Arrays.asList(directions);
        Collections.shuffle(dirs);  // on mélange les directions pour que le labyrinthe soit aléatoire

        // On explore les voisins dans un ordre aléatoire
        for (int[] dir : dirs) {
            int nx = x + dir[0];  // nouvelle position en x
            int ny = y + dir[1];  // nouvelle position en y

            // Vérifie que la nouvelle cellule est bien à l'intérieur du labyrinthe et non visitée
            if (nx > 0 && ny > 0 && nx < nbColonnes - 1 && ny < nbLignes - 1 && !visite[nx][ny]) {
                // On casse le mur entre la case actuelle et la nouvelle case
                int murX = x + dir[0] / 2;
                int murY = y + dir[1] / 2;
                murs[murX][murY] = false;

                //reccurence sur la nouvelle cellule
                genererDFS(nx, ny, visite,  nbColonnes, nbLignes);
            }
        }
    }


    /**
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {
        // case courante
        int[] courante = {this.pj.getX(), this.pj.getY()};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // si c'est pas un mur ou un monstre, on effectue le deplacement
        if (!this.murs[suivante[0]][suivante[1]] && (monstrePresent(suivante[0],suivante[1]) == null)) {
            // on met a jour personnage
            this.pj.setX(suivante[0]);
            this.pj.setY(suivante[1]);

            for (Monstre m : this.monstres) {
                if (m.estAdjacent(this.pj)) {
                    m.attaquer(this.pj);
                } else {
                    m.stopAttaque();
                }
            }
        }
    }

    /**
     * deplace le monstre
     */
    public void deplacerMonstre() {
        for (Monstre m : this.monstres) {
            int deplacementRandom = (int) Math.round(Math.random() * 2);
            if (deplacementRandom == 1) {
                if (m.estAdjacent(this.pj) && !m.isAttacking()) {
                    m.attaquer(this.pj);
                } else if (!m.isAttacking()) {
                    // case courante
                    int[] courante = {m.getX(), m.getY()};
                    String[] action = {HAUT, BAS, GAUCHE, DROITE};

                    // calcule case suivante
                    int[] suivante = getSuivant(courante[0], courante[1], action[(int) (Math.random() * 4)]);

                    // si ce n'est ni un mur ni un personnage, on effectue le deplacement
                    if (!this.murs[suivante[0]][suivante[1]] && !this.pj.etrePresent(suivante[0], suivante[1]) && monstrePresent(suivante[0], suivante[1]) == null) {
                        // on met a jour le monstre
                        m.setX(suivante[0]);
                        m.setY(suivante[1]);
                    }
                }
            }
        }
    }


    /**
     * permet de savoir si un des monstres est présent sur une case
     *
     * @param x La position x potentielle du monstre
     * @param y La position y potentielle du monstre
     * @return true si un des monstres est présent sur la case de coordonnées (x,y), false sinon
     */
    public Monstre monstrePresent(int x, int y) {
        for (Monstre m : this.monstres) {
            if (m.etrePresent(x, y)) {
                return m;
            }
        }
        return null;
    }

    /**
     * permet de savoir si un des objets est présent sur une case
     *
     * @param x La position x potentielle du objet
     * @param y La position y potentielle du objet
     * @return true si un des objets est présent sur la case de coordonnées (x,y), false sinon
     */
    public Amulette objetPresent(int x, int y) {
        for (Amulette o : this.objets) {
            if (o.etrePresent(x, y)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Créer un nouveau tir
     */
    public void tir(){
        Projectile p = new Projectile(pj.getX(), pj.getY(), pj.getDerniereDirection());
        listProjectile.add(p);
    }

    /**
     * Gère le déplacement et la collision des projectiles
     */
    public void gererTir(){
        ArrayList<Projectile> aEnlever = new ArrayList<>();
        for (Projectile p : listProjectile) {
            int[] suivante = getSuivant(p.getX(), p.getY(), p.getDirection());
            Monstre m;

            if (this.murs[suivante[0]][suivante[1]]){
                aEnlever.add(p);
            } else if ((m = monstrePresent(suivante[0], suivante[1])) != null){
                m.subirDegat(1);
                aEnlever.add(p);
            } else {
                p.setX(suivante[0]);
                p.setY(suivante[1]);
            }
        }
        for (Projectile p : aEnlever) {
            listProjectile.remove(p);
        }
    }

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * return mur en (i,j)
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        // utilise le tableau de boolean
        return this.murs[x][y];
    }

    /**
     * retourne la liste de monstres
     * @return liste de monstres
     */
    public ArrayList<Monstre> getMonstres() {
        return monstres;
    }

    /**
     * retourne la liste d'objets
     * @return liste d'objets
     */
    public ArrayList<Amulette> getObjets() {
        return objets;
    }

    public String toString() {
        String s = "";
        for (int y = 0; y < murs.length; y++) {
            // affiche la ligne
            for (int x = 0; x < murs[y].length; x++) {
                if (murs[y][x]) {
                    s+="X";
                }
                else {
                    s+=".";
                }
            }
            // saut de ligne
            s+="\n";
        }
        return s;
    }
}
