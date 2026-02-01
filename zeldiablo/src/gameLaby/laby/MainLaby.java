package gameLaby.laby;

import moteurJeu.MoteurJeu;

/**
 * Classe principale pour lancer le jeu du labyrinthe.
 */
public class MainLaby {
    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        int fps = 60;

        // Création des objets jeu et dessin
        LabyJeu jeuLaby = new LabyJeu(true);
        LabyDessin dessinLaby = new LabyDessin();

        // Configuration du moteur
        MoteurJeu.setTaille(width, height);
        MoteurJeu.setFPS(fps);

        // Lancement du jeu
        MoteurJeu.launch(jeuLaby, dessinLaby);
    }
}