package mvc.Tetris.Modele;


import java.util.Timer;
import java.util.TimerTask;

public class Chronometre {
    Timer t;
    private int chrono;
    private Partie partie;

    public Chronometre(Partie p) {
        t = new Timer();
        this.partie = p;
        t.schedule(new Demarrer(), 0, 1*1000);
    }

    public int getChrono() {
        return chrono;
    }

    public String getTemps(){
        return timeToHMS(this.getChrono());
    }

    public static String timeToHMS(long tempsS) {

        // IN : (long) temps en secondes
        // OUT : (String) temps au format texte : "1 h 26 min 3 s"

        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String r="";

        if(h>0) {r+=h+" h ";}
        if(m>0) {r+=m+" min ";}
        if(s>0) {r+=s+" s";}
        if(h<=0 && m<=0 && s<=0) {r="0 s";}

        return r;
    }

    class Demarrer extends TimerTask {

        public void run() {
            if (!partie.isEstFinie()) {
                chrono++;
            } else {
                t.cancel();
            }
        }
    }
}
