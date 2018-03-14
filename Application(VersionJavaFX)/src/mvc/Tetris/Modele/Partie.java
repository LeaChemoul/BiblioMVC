package mvc.Tetris.Modele;

import mvc.Model.Piece;
import mvc.Model.Plateau;

import java.util.HashMap;

public class Partie implements Runnable{

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;

    public Partie(Plateau p){
        this.plateau = p;
        HashMap<String, Piece> poolDePieces = generateurPieces.createPieces();
        plateau.setPoolDePiece(poolDePieces.values().toArray(new Piece[0]));
    }

    public void deroulement(){
        new Thread(this).start();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    @Override
    public void run() {
        int nbrRepetitions = 0;
        while (nbrRepetitions != -1) {
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

                boolean piecePosee = plateau.newPiece();
                int ligne = 0;
                do {
                    ligne = plateau.ligneASupprimer();
                    if (ligne != -1)
                        plateau.effacerLigne(ligne);
                } while (ligne != -1);
                if (piecePosee) {
                    nbrRepetitions = this.plateau.getHauteur();
                }
            }
        }
    }
}
