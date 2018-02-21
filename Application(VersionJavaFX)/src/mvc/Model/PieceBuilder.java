package mvc.Model;

import javafx.scene.paint.Color;

public class PieceBuilder {

    public final static int XMAX = 5;
    public final static int YMAX = 5;

    public Piece getOTetris(int x,int y){
        Color couleur = Color.rgb(157,108,142);
        String nom = "OTetris";
        Case[][] cases = new Case[XMAX][8];
        for(int i=0;i<XMAX;i++)
            for(int j=0;j<YMAX;j++)
                cases[i][j] = null;
        cases[1][1] = new Case(x,y,couleur,-1);
        cases[1][2] = new Case(x,y+1,couleur,-1);
        cases[2][1] = new Case(x+1,y,couleur,-1);
        cases[2][2] = new Case(x+1,y+1,couleur,-1);
        return new Piece(nom,couleur,0,cases);
    }


    public Piece getITetris(int x,int y){
        Color couleur = Color.rgb(165,120,65);
        String nom = "ITetris";
        Case[][] cases = new Case[XMAX][YMAX];
        for(int i=0;i<XMAX;i++)
            for(int j=0;j<YMAX;j++)
                cases[i][j] = null;
        for(int k=0;k<4;k++)
            cases[0][k] = new Case(x+k,y, couleur,-1);
        return new Piece(nom,couleur,0,cases);
    }

    public Piece getOTetris(){
        return this.getOTetris(0,0);
    }

    public Piece getITetris(){
        return this.getITetris(0,0);
    }

}
