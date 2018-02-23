package mvc.Model;


import javafx.scene.paint.Color;

public class Piece {
    private  String nom;
    private Color couleur;
    private int vitesseChute;
    private Case[][] cases;

    public Piece(String nom, Color couleur, int vitesse, Case[][] cases) {
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
    }

    public Case[][] getCases() {
        return cases;
    }

    public boolean mouvement(Plateau p, Direction direction){
        for(int i = PieceBuilder.XMAX-1;i>=0;i--)
            for(int j = PieceBuilder.YMAX - 1;j>=0;j--) {
                if(cases[i][j] != null){
                    Case caseTemp = cases[i][j];
                    int x = cases[i][j].getX();
                    int y = cases[i][j].getY();
                    //tester pour droite et gauche, ne marche que pour descente
                    if (y <p.getLargeur()-1 && x<p.getHauteur()-1 && x>0 && p.getTableauJeu()[i][j].getIndex() == -1){
                        p.getTableauJeu()[x][y] = new Case(i,j, Color.rgb(255,255,255),-1);;
                        caseTemp.setY(y + direction.getY());
                        p.modifierPlateau(x,y+direction.getY(), caseTemp);
                    }
                    else {
                        return false;
                    }
                }
            }
        return true;

    }
}