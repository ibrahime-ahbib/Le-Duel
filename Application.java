package appli;

public class Application {

    public static void main(String[] args) {
	    Joueur joueur1 = new Joueur("NORD");
        Joueur joueur2 = new Joueur("SUD");
        Jeu jeu = new Jeu(joueur1, joueur2);
        jeu.lancer();
    }
}
