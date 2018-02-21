package mvc.Model;


import com.sun.javafx.geom.Vec2d;

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

    public boolean descendre(Plateau p){
        for(int i = PieceBuilder.XMAX-1;i>=0;i--)
            for(int j = PieceBuilder.YMAX - 1;j>=0;j--) {
                int x = cases[i][j].getX();
                if (x > 0)
                    cases[i][j].setX(x - 1);
                else {
                    return false;
                }
            }
        return true;
    }
}










/*









    //retourne la largeur et la hauteur de la pièce





















    public Vec2d getLargeurHateur(){
        int cmptL = 0;
        int cmptH = 0;
        int maxL = 0;
        int maxH = 0;
        for(int i = 0;i<4;i++){
            cmptL = 0;
            for(int j = 0;j<4;j++){
                if(cases[i][j]==1)
                    cmptL++;
                if(cases[j][i]==1)
                    cmptH++;
            }
            if(cmptL > maxL)
                maxL = cmptL;
            if(cmptH > maxH)
                maxH = cmptH;
        }
        return new Vec2d(cmptL,cmptH);
    }

*/