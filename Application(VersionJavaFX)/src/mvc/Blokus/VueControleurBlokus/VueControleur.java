package mvc.Blokus.VueControleurBlokus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvc.Tetris.Modele.Partie;


public class VueControleur extends Application {

    private Partie partie;

    @Override
    public void start(Stage primaryStage){

        //TOP

        primaryStage.setTitle("Jeu Plateau");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}