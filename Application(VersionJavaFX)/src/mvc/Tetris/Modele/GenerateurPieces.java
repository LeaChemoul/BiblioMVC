package mvc.Tetris.Modele;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import mvc.Model.Piece;
import mvc.Model.PieceBuilder;

import java.util.HashMap;

public class GenerateurPieces {
    PieceBuilder pieceBuilder = new PieceBuilder();
    
    public GenerateurPieces() {
    }

    public Vec2d[] OTetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(1,1);
        listeVect[1] = new Vec2d(1,2);
        listeVect[2] = new Vec2d(2,1);
        listeVect[3] = new Vec2d(2,2);
        return listeVect;
    }

    public Vec2d[] ITetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(0,1);
        listeVect[1] = new Vec2d(1,1);
        listeVect[2] = new Vec2d(2,1);
        listeVect[3] = new Vec2d(3,1);
        return listeVect;
    }

    public Vec2d[] STetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(1,1);
        listeVect[1] = new Vec2d(1,2);
        listeVect[2] = new Vec2d(2,0);
        listeVect[3] = new Vec2d(2,1);
        return listeVect;
    }

    public Vec2d[] ZTetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(1,0);
        listeVect[1] = new Vec2d(1,1);
        listeVect[2] = new Vec2d(2,1);
        listeVect[3] = new Vec2d(2,2);
        return listeVect;
    }

    public Vec2d[] LTetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(1,0);
        listeVect[1] = new Vec2d(2,0);
        listeVect[2] = new Vec2d(3,0);
        listeVect[3] = new Vec2d(3,1);
        return listeVect;
    }

    public Vec2d[] JTetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(3,0);
        listeVect[1] = new Vec2d(1,1);
        listeVect[2] = new Vec2d(2,1);
        listeVect[3] = new Vec2d(3,1);
        return listeVect;
    }

    public Vec2d[] TTetris(){
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(1,0);
        listeVect[1] = new Vec2d(1,1);
        listeVect[2] = new Vec2d(1,2);
        listeVect[3] = new Vec2d(2,1);
        return listeVect;
    }

    public HashMap<String, Piece> createPieces(){

        pieceBuilder.addPiece("OTetris",4,4, Color.rgb(120,150,1),2,OTetris());
        pieceBuilder.addPiece("ITetris",4,4, Color.rgb(29,150,145),2,ITetris());
        pieceBuilder.addPiece("STetris",4,4, Color.rgb(150,72,73),2,STetris());
        pieceBuilder.addPiece("ZTetris",4,4, Color.rgb(150,0,40),2,ZTetris());
        pieceBuilder.addPiece("LTetris",4,4, Color.rgb(0,91,150),2,LTetris());
        pieceBuilder.addPiece("JTetris",4,4, Color.rgb(214,213,31),2,JTetris());
        pieceBuilder.addPiece("TTetris",4,4, Color.rgb(109,67,150),2,TTetris());
        return pieceBuilder.getListePiece();
    }
}
