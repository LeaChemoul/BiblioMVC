package mvc.Tetris.Modele;

import mvc.Model.Plateau;

public class Partie {

    Plateau plateau;

    public Partie(Plateau p){
        this.plateau = p;
    }

    public void deroulement(){
        this.plateau.newPiece();
        this.plateau.descente();

    }

    public Plateau getPlateau() {
        return plateau;
    }
}
