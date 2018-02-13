package mvc.Model;

public class Plateau {
    private int largeur;
    private int hauteur;
    private int[][] plateau;

    public Plateau(int l, int h){
        this.largeur = l;
        this.hauteur = h;
        plateau = new int[l][h];

        for(int i=0;i<this.largeur;i++)
            for(int j=0;j<this.hauteur;j++)
                plateau[i][j]=0;
    }
}
