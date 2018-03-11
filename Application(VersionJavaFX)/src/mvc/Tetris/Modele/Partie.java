package mvc.Tetris.Modele;

import mvc.Model.Plateau;

public class Partie {

    private Plateau plateau;
    private RepeterAction repeterAction;

    public Partie(Plateau p){
        this.plateau = p;
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
