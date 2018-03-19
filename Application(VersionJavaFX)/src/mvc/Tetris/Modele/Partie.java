package mvc.Tetris.Modele;

import javafx.scene.control.Alert;
import mvc.Model.Piece;
import mvc.Model.Plateau;

import java.util.HashMap;
import java.util.Random;

public class Partie implements Runnable{

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;
    private boolean estFinie;

    public Partie(Plateau p){
        this.plateau = p;
        HashMap<String, Piece> poolDePieces = generateurPieces.createPieces();
        plateau.setPoolDePiece(poolDePieces.values().toArray(new Piece[0]));
        pieceSuivante();
    }

    public void pieceSuivante(){
        //Le nombre de pièces suivantes connues n'excedera jamais 1 pièce
        if(plateau.getPoolDePiece() != null){
            Random random = new Random();
            Piece pieceSuiv = new Piece(plateau.getPoolDePiece()[random.nextInt(plateau.getPoolDePiece().length)], true);
            plateau.getPiecesSuivantes().clear();
            plateau.getPiecesSuivantes().add(pieceSuiv);
        }
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
                try {
                    Thread.sleep(400);
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
                this.pieceSuivante();
                int ligne = 0;
                do {
                    ligne = plateau.ligneASupprimer();
                    if (ligne != -1)
                        plateau.supprimerLigne(ligne);
                } while (ligne != -1);
                if (piecePosee) {
                    nbrRepetitions = this.plateau.getHauteur();
                }else{
                    this.estFinie = true;
                    break;
                }
            }
        }

    }
}
