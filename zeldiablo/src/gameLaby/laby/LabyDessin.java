package gameLaby.laby;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe pour dessiner le jeu du labyrinthe.
 */
public class LabyDessin implements DessinJeu {

    /**
     * affiche l'etat du jeu dans le canvas passe en parametre
     *
     * @param jeu jeu a afficher
     * @param canvas canvas dans lequel dessiner l'etat du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        Labyrinthe laby = ((LabyJeu) jeu).getLabyrinthe();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Définition de la taille d'une case
        int tailleCase = (int) Math.min(canvas.getWidth() / laby.getLength(), canvas.getHeight() / laby.getLengthY());

        // Efface l'écran
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Dessin du labyrinthe
        for (int y = 0; y < laby.getLengthY(); y++) {
            for (int x = 0; x < laby.getLength(); x++) {
                if (laby.getMur(x, y)) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * tailleCase, y * tailleCase, tailleCase, tailleCase);
                }
            }
        }

        // Dessin du personnage
        Joueur pj = laby.pj;
        gc.setFill(Color.BLUE);
        gc.fillOval(pj.getX() * tailleCase + tailleCase / 4, pj.getY() * tailleCase + tailleCase / 4, tailleCase / 2, tailleCase / 2);

        Sortie s = laby.sortie;
        gc.setFill(new Color(0, 1, 0, 0.5));
        gc.fillRect(s.getX() * tailleCase, s.getY() * tailleCase, tailleCase, tailleCase);

        // Dessin des monstres
        for (Monstre monstre : laby.getMonstres()) {
            gc.setFill(monstre.getCouleur());
            gc.fillOval(monstre.getX() * tailleCase + tailleCase / 4, monstre.getY() * tailleCase + tailleCase / 4, tailleCase / 2, tailleCase / 2);
        }

        //Dessin des projectiles
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        for (Projectile p : laby.listProjectile){
            gc.fillOval(p.getX() * tailleCase + tailleCase / 4, p.getY() * tailleCase + tailleCase / 4, tailleCase / 4, tailleCase / 4);
        }

        // dessin objets
        for (Amulette objet : laby.getObjets()) {
            Color couleur = new  Color(1, 1, 0, 0.5);
            gc.setFill(couleur);
            gc.fillOval(objet.getX() * tailleCase + tailleCase / 4, objet.getY() * tailleCase + tailleCase / 4, tailleCase / 2, tailleCase / 2);
        }

        //Affichage fin du jeu
        if(laby.etreFini){
            // Dessin du message de fin de jeu
            gc.setFill(new Color(1, 1, 1, 0.7));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.GOLD);
            String message = "Fin du jeu: ";
            if (laby.pj.getVie() <= 0) {
                message += "Vous avez perdu...";
            }
            else {
                message += "Vous avez gagné !!!";
            }
            gc.fillText(message, canvas.getWidth()/5, canvas.getHeight()/2);
            gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        }
    }
}