package mvc.Tetris.Modele;

import mvc.Model.Plateau;

import java.util.Timer;
import java.util.TimerTask;

public class RepeterAction {
    private Timer t;
    private Plateau plateau;

    public RepeterAction(Plateau p) {
        this.plateau = p;
        t = new Timer();
        t.schedule(new Descente(), 0, 1 * 500);
    }

    class Descente extends TimerTask {
        int nbrRepetitions = plateau.getHauteur();


        public void run() {
            if (nbrRepetitions > 0) {
                //TODO Lorsqu'on descend bcp plus vite que le timer, on va au-delà de l'index du tableau : IndexOutOfBoundException
                boolean aEuLieu = plateau.versBas(plateau.getPieceCourante());
                if (!aEuLieu)
                    nbrRepetitions = 0;
                else
                    nbrRepetitions--;
            } else {
                //La pièce est arrivée à la fin
                plateau.newPiece();
                t.cancel();
                int ligne = 0;
                do{
                    System.out.println("Suppression de la ligne ");
                    ligne = plateau.ligneASupprimer();
                    plateau.effacerLigne(ligne);
                } while(ligne != -1);
                RepeterAction repeterAction = new RepeterAction(plateau);
            }
        }
    }
}
