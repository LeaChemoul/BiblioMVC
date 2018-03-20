package mvc.RushHour.Modele;

import javafx.scene.paint.Color;
import mvc.Model.Piece;
import mvc.Model.PieceBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GenerateurPieces {
    PieceBuilder pieceBuilder = new PieceBuilder();
    
    public GenerateurPieces() {
    }

    public HashMap<String, Piece> createPieces(){

        pieceBuilder.addPiece("4RushHourV", Color.rgb(248,137,99),new double[]{0,0, 0,1, 0,2, 0,3});
        pieceBuilder.addPiece("4RushHourH", Color.rgb(248,137,99),new double[]{0,0, 1,0, 2,0, 3,0});

        pieceBuilder.addPiece("3RushHourV", Color.rgb(248,137,99),new double[]{0,0, 0,1, 0,2});
        pieceBuilder.addPiece("3RushHourH", Color.rgb(248,137,99),new double[]{0,0, 1,0, 2,0});

        pieceBuilder.addPiece("2RushHourV", Color.rgb(248,137,99),new double[]{0,0, 0,1});
        pieceBuilder.addPiece("2RushHourH", Color.rgb(248,137,99),new double[]{0,0, 1,0});
        return pieceBuilder.getListePiece();
    }
}
