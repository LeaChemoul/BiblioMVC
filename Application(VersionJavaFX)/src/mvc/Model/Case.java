package mvc.Model;

public class Case {
    private int x;
    private int y;
    private String couleur;
    private int index;

    public Case(int x, int y, String coul, int i){
        this.x = x;
        this.y = y;
        this.couleur = coul;
        this.index = i;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
