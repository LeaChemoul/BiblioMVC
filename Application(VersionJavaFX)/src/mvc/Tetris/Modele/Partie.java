package mvc.Tetris.Modele;

import mvc.Model.Piece;
import mvc.Model.Plateau;

import java.util.HashMap;

public class Partie {

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;
    private RepeterAction repeterAction;

    public Partie(Plateau p){
        this.plateau = p;
        HashMap<String, Piece> poolDePieces = generateurPieces.createPieces();
        plateau.setPoolDePiece(poolDePieces.values().toArray(new Piece[0]));
    }

    public void deroulement(){
        if(this.plateau.getPieceCourante() == null){
            this.plateau.newPiece();
            this.repeterAction = new RepeterAction(this.plateau); //on repete l'action de descente
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
