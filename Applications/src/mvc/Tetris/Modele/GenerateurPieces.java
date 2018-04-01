package mvc.Tetris.Modele;

import Model.Piece;
import Model.PieceBuilder;

import java.util.HashMap;

public class GenerateurPieces {
    PieceBuilder pieceBuilder = new PieceBuilder();
    
    public GenerateurPieces() {
    }

    /**
     * Création des pièces propres au Tetris
     * @return La liste des pièces sous forme de HashMap
     */
    public HashMap<String, Piece> createPieces(){

        pieceBuilder.addPiece("OTetris", null,new double[]{1,1, 1,2, 2,1, 2,2});
        pieceBuilder.addPiece("ITetris", null,new double[]{0,1, 1,1, 2,1, 3,1});
        pieceBuilder.addPiece("STetris", null,new double[]{1,1, 1,2, 2,0, 2,1});
        pieceBuilder.addPiece("ZTetris", null,new double[]{1,0, 1,1, 2,1, 2,2});
        pieceBuilder.addPiece("LTetris", null,new double[]{1,0, 2,0, 3,0, 3,1});
        pieceBuilder.addPiece("JTetris", null,new double[]{3,0, 1,1, 2,1, 3,1});
        pieceBuilder.addPiece("TTetris", null,new double[]{1,0, 1,1, 1,2, 2,1});
        return pieceBuilder.getListePiece();
    }
}
