package appli.jeu;

import java.util.ArrayList;
import java.util.Collections;

public class Joueur {
    // Nom du joueur
    private final String nom;

    // Les piles du joueur
    private final Pile pileAscendante;
    private final Pile pileDescendante;

    // Liste des cartes en main
    private final ArrayList<Integer> cartesEnMain;

    // La pioche du joueur
    private final Pioche pioche;

    // Constructeur par défaut
    public Joueur(String nom)
    {
        this.nom = nom;
        cartesEnMain = new ArrayList<>();

        pileAscendante = new Pile(Constantes.CARTE_INITIALE_PILE_ASCENDANTE, TypePile.ASCENDANTE);
        pileDescendante = new Pile(Constantes.CARTE_INITIALE_PILE_DESCENDANTE, TypePile.DESCENDANTE);

        pioche = new Pioche(Constantes.CARTE_INITIALE_PILE_ASCENDANTE +1, Constantes.CARTE_INITIALE_PILE_DESCENDANTE -1);
        piocher(Constantes.NB_CARTES_MAIN_INITIALES);
    }

    /**
     * Récupère une des piles du joueur
     * @param typePile le type de la pile à récupérer
     * @return la pile du joueur désirée
     */
    public Pile getPile(TypePile typePile){
        return typePile == TypePile.ASCENDANTE? pileAscendante : pileDescendante;
    }

    /**
     * Récupère les cartes en main du joueur
     * @return La liste des cartes en main
     */
    public ArrayList<Integer> getCartesEnMain() {
        return cartesEnMain;
    }

    /**
     * Permet au joueur de piocher des cartes
     * @param nombreCartes le nombre de cartes que l'on souhaite piocher
     * @return le nombre de cartes que le joueur a pioché au final
     * (n'est pas forcément égal à nombreCartes car la pioche peut s'épuiser avant que toutes les cartes aient été piochées)
     */
    public int piocher(int nombreCartes)
    {
        /*
            S'il y a plus de cartes à piocher que de cartes dans la pioche, alors on pioche seulement toutes les cartes
            Restantes dans la pioche
        */
        int nombreCartesPiochees = Math.min(nombreCartes, pioche.getNombreCartes());

        for(int i = 0; i < nombreCartesPiochees; ++i)
            cartesEnMain.add(pioche.piocher());

        //On trie la main du joueur
        Collections.sort(cartesEnMain);
        return nombreCartesPiochees;
    }

    /**
     * Retourne une chaine de caractères présentant l'état du plateau et de la main du joueur
     * @return la chaine de caractères mentionnée ci-dessus
     */
    public String informationsPlateau()
    {
        StringBuilder affiche = new StringBuilder();

        //On utilise le format %O2d afin d'ajouter un zéro à gauche si le nombre est un chiffre
        affiche.append(nom)
                .append(" ^[")
                .append(String.format("%02d", pileAscendante.getCarteSommet()))
                .append("] v[")
                .append(String.format("%02d", pileDescendante.getCarteSommet()))
                .append("] (m")
                .append(cartesEnMain.size())
                .append("p")
                .append(pioche.getNombreCartes())
                .append(")");

        return affiche.toString();
    }

    /**
     * Retourne une chaîne de caractères listant les cartes de la main du joueur
     * @return la chaine de caractères mentionnée ci-dessus
     */
    public String informationsCartesMain() {
        StringBuilder affiche = new StringBuilder();

        affiche.append("cartes ")
                .append(nom)
                .append(" { ");

        for(int i : cartesEnMain)
            affiche.append(String.format("%02d", i))
                    .append(" ");

        affiche.append("}");
        return affiche.toString();
    }

    /**
     * Retourne le nom du joueur
     * @return le nom du joueur
     */
    public String toString() {
        return nom;
    }

    /**
     * Retourne le nombre de cartes dans la pioche du joueur
     * @return le nombre de cartes dans la pioche du joueur
     */
    public int getTaillePioche() {
        return pioche.getNombreCartes();
    }
}
