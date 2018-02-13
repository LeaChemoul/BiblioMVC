package mvc.VueControleur;

import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Case extends Parent{
        Rectangle fond = new Rectangle();


        public String lettre;//lettre de la touche, c'est une variable public pour qu'elle puisse être lue depuis les autres classes
        private int positionX = 0;//abscisse
        private int positionY = 0;//ordonnée de la touche

        Rectangle fond_touche;

        public Case(int posX, int posY){
            positionX = posX;
            positionY = posY;

            fond = new Rectangle(30,30,Color.WHITE);
            fond.setArcHeight(10);
            fond.setArcWidth(10);
            this.getChildren().add(fond);//ajout du rectangle de fond de la touche

            this.setOnMouseEntered(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    fond.setFill(Color.LIGHTGREY);
                }
            });
            this.setOnMouseExited(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    fond.setFill(Color.WHITE);
                }
            });
        }
}
