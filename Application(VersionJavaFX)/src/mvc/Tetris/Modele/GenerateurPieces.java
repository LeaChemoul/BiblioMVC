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

        pieceBuilder.addPiece("OTetris", Color.rgb(120,150,1),OTetris());
        pieceBuilder.addPiece("ITetris", Color.rgb(29,150,145),ITetris());
        pieceBuilder.addPiece("STetris", Color.rgb(150,72,73),STetris());
        pieceBuilder.addPiece("ZTetris", Color.rgb(150,0,40),ZTetris());
        pieceBuilder.addPiece("LTetris", Color.rgb(0,91,150),LTetris());
        pieceBuilder.addPiece("JTetris", Color.rgb(214,213,31),JTetris());
        pieceBuilder.addPiece("TTetris", Color.rgb(109,67,150),TTetris());
        return pieceBuilder.getListePiece();
    }
}
