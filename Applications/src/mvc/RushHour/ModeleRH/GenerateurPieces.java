package mvc.RushHour.ModeleRH;

import Model.PieceBuilder;
import Model.Piece;

import java.util.HashMap;

public class GenerateurPieces {
    private PieceBuilder pieceBuilder = new PieceBuilder();

    /**
     * Création des pièces propres au Rush Hour.
     * @return Liste des pièces sous forme de HashMap dont la clé est le nom de la pièce du Rush Hour.
     */
    public HashMap<String, Piece> createPieces(){

        pieceBuilder.addPiece("4RushHourV", null,new double[]{0,0, 0,1, 0,2, 0,3});
        pieceBuilder.addPiece("4RushHourH", null,new double[]{0,0, 1,0, 2,0, 3,0});

        pieceBuilder.addPiece("3RushHourV", null,new double[]{0,0, 0,1, 0,2});
        pieceBuilder.addPiece("3RushHourH", null,new double[]{0,0, 1,0, 2,0});

        pieceBuilder.addPiece("2RushHourV", null,new double[]{0,0, 0,1});
        pieceBuilder.addPiece("2RushHourH", null,new double[]{0,0, 1,0});
        return pieceBuilder.getListePiece();
    }
}
