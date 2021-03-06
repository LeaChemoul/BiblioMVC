package mvc.Tetris.ModeleTetris;

import Model.Piece;
import Model.Plateau;

import java.util.HashMap;
import java.util.Random;

public class Partie implements Runnable{

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;
    private boolean estFinie;
    private int score = 0;
    private Chronometre chronometre;


    public Partie(Plateau p){
        this.plateau = p;
        HashMap<String, Piece> poolDePieces = generateurPieces.createPieces();
        plateau.setPoolDePiece(poolDePieces.values().toArray(new Piece[0]));
        pieceSuivante();
    }

    /**
     * Met à jour la pièce suivante de la partie.
     */
    private void pieceSuivante(){
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
        this.chronometre = new Chronometre(this);
    }

    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Methode appellée au lancement du thread.
     */
    @Override
    public void run() {
        int nbrRepetitions = 0;
        while (nbrRepetitions != -1) {
            if (nbrRepetitions > 0) {
                try {
                    Thread.sleep(400);
                    boolean aEuLieu = plateau.versBas(plateau.getPieceCourante());
                    if (!aEuLieu)
                        nbrRepetitions = 0; //Si la descente n'a pas eu lieu s'est qu'elle était impossible
                    else
                        nbrRepetitions--;

                    //On incremente le score
                    this.incrementerScore();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {

                boolean piecePosee = plateau.newPiece();
                this.pieceSuivante(); //Création de la pièce suivante

                //Suppression des lignes
                int ligne = 0;
                do {
                    ligne = plateau.ligneASupprimer();
                    if (ligne != -1) {
                        score += 40;
                        plateau.supprimerLigne(ligne);
                    }
                } while (ligne != -1);

                //Test de fin de partie
                if (piecePosee) {
                    nbrRepetitions = this.plateau.getHauteur();
                }else{
                    this.estFinie = true;
                    break;
                }
            }
        }

    }

    public boolean isEstFinie() {
        return estFinie;
    }

    public int getScore() {
        return score;
    }

    public void incrementerScore(){
        this.score += 2;
    }

    public Chronometre getChronometre() {
        return chronometre;
    }
}
