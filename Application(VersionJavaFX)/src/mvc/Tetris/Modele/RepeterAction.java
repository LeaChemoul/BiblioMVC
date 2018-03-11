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
        t.schedule(new Descente(), 0, 1*500);
    }

    class Descente extends TimerTask {
        int nbrRepetitions = plateau.getHauteur();

        public void run() {
            if (nbrRepetitions > 0) {
                plateau.versBas(plateau.getPieceCourante());
                nbrRepetitions--;
            } else {
                plateau.setPieceCourante(null);
                t.cancel();
            }
        }
    }
}
