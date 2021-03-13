package appli.jeu;

public class Pile {
    /*
        En théorie une pile dans "The Game" est constituée de toutes les cartes qui ont déjà été jouées dessus, mais
        dans notre cas seule la carte du dessus suffit.
    */
    private int carteSommet;

    // Une pile est soit ascendante soit descendante.
    private TypePile typePile;

    // Constructeur par défaut
    public Pile(int valeurInitiale, TypePile typePile) {
        this.typePile = typePile;
        carteSommet = valeurInitiale;
    }

    // Constructeur de copie
    public Pile(Pile autre){
        set(autre);
    }

    public void set(Pile autre) {
        this.typePile = autre.typePile;
        carteSommet = autre.carteSommet;
    }

    /**
     * Renvoie la carte au sommet de la pile.
     * @return la carte au sommet de la pile.
     */
    public int getCarteSommet() {
        return carteSommet;
    }

    /**
     * Change la carte du sommet de la pile
     * @param nouvelleCarteSommet la carte qui va remplacer la carte actuelle sur le sommet.
     */
    public void setCarteSommet(int nouvelleCarteSommet) {
        this.carteSommet = nouvelleCarteSommet;
    }

    /**
     * Vérifie si une carte est jouable sur la pile, en prenant en compte la règle +10/-10 si besoin
     * @param carte la carte à vérifier
     * @param pileAdverse si ce booléen est vrai alors on doit appliquer la règle +10/-10 (en fonction du type de la pile)
     * @return true si la carte est jouable, false sinon
     */
    public boolean carteJouable(int carte, boolean pileAdverse){
        if(typePile == TypePile.ASCENDANTE) {
            if(pileAdverse)
                return carte < carteSommet;
            else
                return carte > carteSommet || carte + Constantes.DIFFERENCE_CARTE_JOUABLE == carteSommet;
        }
        else {
            if(pileAdverse)
                return carte > carteSommet;
            else
                return carte < carteSommet || carte - Constantes.DIFFERENCE_CARTE_JOUABLE == carteSommet;
        }
    }
}
