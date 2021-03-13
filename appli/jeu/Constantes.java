package appli.jeu;

public class Constantes {
    //La quantité de cartes qu'a un joueur dans sa main au début de la partie
    public static int NB_CARTES_MAIN_INITIALES = 6;

    public static int CARTE_INITIALE_PILE_ASCENDANTE = 1;
    public static int CARTE_INITIALE_PILE_DESCENDANTE = 60;

    //La différence entre deux cartes pour qu'une carte soit jouable sur une pile appartenant au joueur qui la joue
    public static int DIFFERENCE_CARTE_JOUABLE = 10;

    //Le nombre de cartes jouées minimum pour que le coup soit valide
    public static int NB_CARTES_MINIMUM_REQUISES_COUP = 2;

    //Le nombre maximum de cartes jouables sur les piles de l'adversaire
    public static int NB_CARTES_MAXIMUM_JOUABLE_ADVERSE = 1;
}
