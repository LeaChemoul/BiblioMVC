package mvc.Model;


import com.sun.javafx.geom.Vec2d;

import java.util.LinkedList;

public class Piece {
    private String nom;
    private String couleur;
    private int vitesseChute;
    private int[][] cases = {{1,1,1,1},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

    public Piece(){

    }

    public Piece(String nom, String couleur, int vitesse){
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
    }

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
}
