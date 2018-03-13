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
        t.schedule(new Descente(), 0, 1 * 550);
    }

    class Descente extends TimerTask {
        int nbrRepetitions = plateau.getHauteur();


        public void run() {
            if (nbrRepetitions > 0) {
                //TODO Lorsqu'on descend bcp plus vite que le timer, on va au-delà de l'index du tableau : IndexOutOfBoundException
                try {
                    Thread.sleep(500);
                    boolean aEuLieu = plateau.versBas(plateau.getPieceCourante());
                    if (!aEuLieu)
                        nbrRepetitions = 0;
                    else
                        nbrRepetitions--;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //La pièce est arrivée à la fin
                boolean piecePosee = plateau.newPiece();
                t.cancel();
                int ligne = 0;
                do{
                    System.out.println("Suppression de la ligne ");
                    ligne = plateau.ligneASupprimer();
                    if(ligne != -1)
                        plateau.effacerLigne(ligne);
                } while(ligne != -1);
                if(piecePosee){
                    RepeterAction repeterAction = new RepeterAction(plateau);
                }
            }
        }
    }
}
