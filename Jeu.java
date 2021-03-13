package appli.jeu;

import java.util.ArrayList;

public class Jeu
{
    // Liste des joueurs, joueurActif et joueurInactif changent à tour de rôle de valeur
    private final Joueur joueur1;
    private final Joueur joueur2;
    private Joueur joueurActif;
    private Joueur joueurInactif;
    private Joueur gagnant;

    // Contient les informations du dernier coup joué de la forme "x cartes posées, x cartes piochées
    private String informationsDernierCoupJoue;

    /* Constructeur **/
    public Jeu(String nomJoueurUn, String nomJoueurDeux) {
        joueur1 = new Joueur(nomJoueurUn);
        joueur2 = new Joueur(nomJoueurDeux);

        //Au début du jeu, le premier joueur est celui qui commence

        joueurActif = joueur1;
        joueurInactif = joueur2;
        gagnant = null; //Il n'y a pas de gagnant pour l'instant
    }

    /**
        Renvoie les informations du dernier coup joué
        @return la chaîne qui contient ces informations.
     */
    public String getInformationsDernierCoupJoue(){
        return informationsDernierCoupJoue;
    }

    /**
        Renvoie le premier joueur crée
        @return le premier joueur crée
     */
    public Joueur getPremierJoueur(){
        return joueur1;
    }

    /**
     * Renvoie le second joueur crée
     * @return le second joueur crée
     */
    public Joueur getSecondJoueur(){
        return joueur2;
    }

    /**
     * Renvoie le joueur actif
     * @return le joueur actif
     */
    public Joueur getJoueurActif(){
        return joueurActif;
    }

    /**
     * Renvoie le joueur inactif
     * @return le joueur inactif
     */
    public Joueur getJoueurInactif(){
        return joueurInactif;
    }

    /**
     * Renvoie le joueur qui a gagné la partie
     * @return le joueur qui a gagné la partie
     */
    public Joueur getGagnant() {
        return gagnant;
    }

    /**
     * Vérifie qu'une série de coups entrés par un utilisateur est valide syntaxiquement
     * @param coup
     * @return true si le coup est valide, false sinon
     */
    public boolean formatCoupValide(String coup) {
        /*
            Un coup est valide syntaxiquement si:
            - les deux premiers caractères sont des chiffres
            - le troisième est 'v' ou '^'
            - s'il y a un quatrième caractère, il doit être '
         */

        if(((coup.length() == 4 && coup.charAt(3) != '\'')
                || (coup.length() != 3 && coup.length() != 4)
                || !Character.isDigit(coup.charAt(0))
                || !Character.isDigit(coup.charAt(1))
                || (coup.charAt(2) != '^' && coup.charAt(2) != 'v')))
            return false;
        return true;
    }
    /**
     * Execute un coup rentré par le joueur actif
     * @param coupEntre le coup entré par le joueur actif
     * @return true si le coup a pu être joué, false sinon
     */
    public boolean jouerCartes(String coupEntre)
    {
        //Le principe de cette vérification est de simuler un coup avec des plateaux de copie
        //Si on arrive à la fin et que tout s'est bien passé, on n'a plus qu'à modifier les vrais plateaux grâce aux copies qui contiennent les nouvelles valeurs.

        Pile pileJoueurAscendanteTemporaire = new Pile(joueurActif.getPile(TypePile.ASCENDANTE));
        Pile pileJoueurDescendanteTemporaire = new Pile(joueurActif.getPile(TypePile.DESCENDANTE));

        Pile pileJoueurAdverseAscendanteTemporaire = new Pile(joueurInactif.getPile(TypePile.ASCENDANTE));
        Pile pileJoueurAdverseDescendanteTemporaire = new Pile(joueurInactif.getPile(TypePile.DESCENDANTE));

        String[] coupsAJouer = coupEntre.split("\\s+"); //On utilise cette expression régulière pour supprimer tous les séparateurs
        ArrayList<Integer> cartesJouees = new ArrayList<>();
        /*
            On garde des compteurs pour le nombre de cartes joués sur la pile du joueur en question et celle du joueur adverse
            Afin de vérifier que l'on respecte les règles de pose (au moins deux cartes jouées, une carte maximum chez l'adversaire)
        */
        int nbCartesJoueesPile = 0;
        int nbCartesJoueesPileAdverse = 0;

        Pile pile;

        for(String coup : coupsAJouer) {
            if(!formatCoupValide(coup))
                return false;

            //On peut appeller parseInt sans vérifier les exceptions puisqu'on sait à ce point que les deux premiers caractères sont des chiffres
            int carteActuelle = Integer.parseInt(coup.substring(0, 2));

            //Si la carte est déjà jouée, le coup est invalide car on ne peut pas jouer deux fois la même carte
            if(!joueurActif.getCartesEnMain().contains(carteActuelle) || cartesJouees.contains(carteActuelle))
                return false;

            //La syntaxe du coup est valide, on peut maintenant essayer de le jouer sur la pile de copie pour voir s'il n'y a pas d'erreur.

            boolean pileAdverse = coup.length() == 4;
            TypePile pileVisee = coup.charAt(2) == 'v'? TypePile.DESCENDANTE : TypePile.ASCENDANTE;

            if(pileAdverse)
                pile = pileVisee == TypePile.ASCENDANTE? pileJoueurAdverseAscendanteTemporaire : pileJoueurAdverseDescendanteTemporaire;
            else
                pile = pileVisee == TypePile.ASCENDANTE? pileJoueurAscendanteTemporaire : pileJoueurDescendanteTemporaire;

            //Si le coup n'est pas jouable sur la pile, le coup est invalide, on arrête le traitement.
            if(!pile.carteJouable(carteActuelle, pileAdverse))
                return false;

            pile.setCarteSommet(carteActuelle);

            if(pileAdverse)
                nbCartesJoueesPileAdverse++;
            else
                nbCartesJoueesPile++;

            cartesJouees.add(carteActuelle);
        }

        /*
            Un coup est invalide si:
            le joueur joue plus d'une carte sur le plateau de l'adversaire
            le joueur joue moins de deux cartes en tout
         */
        if((nbCartesJoueesPile + nbCartesJoueesPileAdverse < Constantes.NB_CARTES_MINIMUM_REQUISES_COUP)
                || nbCartesJoueesPileAdverse > Constantes.NB_CARTES_MAXIMUM_JOUABLE_ADVERSE)
            return false;

        /*
           Si on arrive à ce point, c'est que le coup proposé est jouable et les piles de copie qui ont servi
           pour les tests contiennent les nouvelles (et bonnes) valeurs. On a plus qu'à mettre à jour les vraies piles en copiant
           les valeurs des piles copiées dedans.
        */

        joueurActif.getPile(TypePile.ASCENDANTE).set(pileJoueurAscendanteTemporaire);
        joueurActif.getPile(TypePile.DESCENDANTE).set(pileJoueurDescendanteTemporaire);
        joueurInactif.getPile(TypePile.ASCENDANTE).set(pileJoueurAdverseAscendanteTemporaire);
        joueurInactif.getPile(TypePile.DESCENDANTE).set(pileJoueurAdverseDescendanteTemporaire);

        /* Puis on retire les cartes posées par le joueur de sa main */

        for(Integer i : cartesJouees)
            joueurActif.getCartesEnMain().remove(i);

        /*
            On détermine le nombre de cartes que le joueur doit piocher en fonction de s'il a joué sur la pile adverse
            ou non.
         */

        int nombreCartesJouees = nbCartesJoueesPile + nbCartesJoueesPileAdverse;
        int nombreCartesPiochees = joueurActif.piocher(nbCartesJoueesPileAdverse == 1 ?
                Constantes.NB_CARTES_MAIN_INITIALES - joueurActif.getCartesEnMain().size() :
                2);
        informationsDernierCoupJoue = nombreCartesJouees + " cartes posées, " +  nombreCartesPiochees + " cartes piochées";

        /* A la fin du tour, le joueur actif devient inactif et inversement */

        Joueur temp = joueurInactif;
        joueurInactif = joueurActif;
        joueurActif = temp;

        return true;
    }

    /**
     * Vérifie que le joueur actif est en capacité de jouer un coup, peu importe lequel
     * @return true si le joueur actif peut jouer, false sinon
     */
    private boolean joueurActifPeutJouer()
    {
        //Si le joueur a moins de deux cartes dans sa main on est sûr qu'il ne puisse pas jouer, donc on quitte directement
        if(joueurActif.getCartesEnMain().size() < 2) return false;

        ArrayList<Integer> cartesJouables = new ArrayList<>();

        Pile pileJoueurAscendanteTemporaire = new Pile(joueurActif.getPile(TypePile.ASCENDANTE));
        Pile pileJoueurDescendanteTemporaire = new Pile(joueurActif.getPile(TypePile.DESCENDANTE));

        Pile pileJoueurAdverseAscendanteTemporaire = new Pile(joueurInactif.getPile(TypePile.ASCENDANTE));
        Pile pileJoueurAdverseDescendanteTemporaire = new Pile(joueurInactif.getPile(TypePile.DESCENDANTE));

        /*
            Vérification de la pile ascendante: on cherche la plus longue combinaison de -10 possible
            Par exemple, si la carte sur la pile est 40 et qu'on a en main 10,20,30 alors on peut poser 30^ 20^ 10^
            Pour trouver ces combinaisons on commence depuis la fin de la main du joueur
        */
        for(int i = joueurActif.getCartesEnMain().size() - 1; i >= 0 ; --i) {
            int carte = joueurActif.getCartesEnMain().get(i);
                if(carte == pileJoueurAscendanteTemporaire.getCarteSommet() - Constantes.DIFFERENCE_CARTE_JOUABLE) {
                    cartesJouables.add(carte);
                    pileJoueurAscendanteTemporaire.setCarteSommet(carte);
                }
        }
        //Maintenant qu'on a trouvé la plus grande combinaison (si elle existe), on cherche les cartes jouables par dessus
        //Dans l'exemple précédent, il s'agirait des cartes plus grandes que 10
        for(int i : joueurActif.getCartesEnMain())
            //On s'assure que la carte n'est pas déjà présente dans la liste des cartes jouables
            if(pileJoueurAscendanteTemporaire.carteJouable(i, false) && !cartesJouables.contains(i))
                cartesJouables.add(i);

        //S'il y a deux cartes ou plus jouables, on peut quitter tout de suite la fonction
        if(cartesJouables.size() >= Constantes.NB_CARTES_MINIMUM_REQUISES_COUP) return true;

        //S'il y a une seule carte jouable, on la sort du tableau afin qu'elle ne bloque pas une combinaison pour la pile descendante
        //On la stocke dans une variable temporaire qui prend -1 comme valeur initiale au cas où le tableau soit vide
        int uniqueCarteJoueePileAscendante = -1;
        if(cartesJouables.size() == 1) {
            uniqueCarteJoueePileAscendante = cartesJouables.get(0);
            cartesJouables.clear();
        }

        //Vérification de la pile descendante: on procède comme pour la pile ascendante mais cette fois-ci dans l'ordre inverse
        for(int i : joueurActif.getCartesEnMain()){
            if(i == pileJoueurDescendanteTemporaire.getCarteSommet() + Constantes.DIFFERENCE_CARTE_JOUABLE){
                cartesJouables.add(i);
                pileJoueurDescendanteTemporaire.setCarteSommet(i);
            }
        }

        for(int i = joueurActif.getCartesEnMain().size() - 1; i >= 0; --i)
        {
            int carte = joueurActif.getCartesEnMain().get(i);
            if(pileJoueurDescendanteTemporaire.carteJouable(carte, false) && !cartesJouables.contains(carte))
                cartesJouables.add(carte);
        }

        //Maintenant qu'on a terminé de traiter les cartes pour la pile descendante, on peut réintroduire la carte jouable de la pile ascendante
        //Si elle existe et si elle n'a pas été jouée entre temps dans la pile descendante
        if(!cartesJouables.contains(uniqueCarteJoueePileAscendante) && uniqueCarteJoueePileAscendante != -1)
            cartesJouables.add(uniqueCarteJoueePileAscendante);

        //S'il y a deux cartes ou plus jouables sur la pile ascendante et descendante on peut quitter la fonction
        if(cartesJouables.size() >= Constantes.NB_CARTES_MINIMUM_REQUISES_COUP) return true;

        //Sinon, on cherche si on peut jouer une carte sur une pile adverse qui n'ait pas encore été jouée
        for(int b : joueurActif.getCartesEnMain()){
            if((pileJoueurAdverseDescendanteTemporaire.carteJouable(b, true)
            || pileJoueurAdverseAscendanteTemporaire.carteJouable(b, true))
            && !cartesJouables.contains(b)){
                cartesJouables.add(b);
                break; //Dès qu'on en trouve une, on arrête la boucle car on ne peut jouer qu'une carte sur les piles adverses
            }
        }

        return cartesJouables.size() >= Constantes.NB_CARTES_MINIMUM_REQUISES_COUP;
    }

    /**
     * Vérifie que la partie peut continuer (donc qu'aucun joueur n'ait gagné ou perdu)
     * @return true si la partie peut continuer, false sinon
     */
    public boolean peutContinuer() {
        //Si le joueur actif ne peut pas jouer ou que le joueur non actif (celui qui a joué juste avant) n'a plus de cartes, alors la partie est finie.
        if (!joueurActifPeutJouer() || (joueurInactif.getTaillePioche() == 0 && joueurInactif.getCartesEnMain().isEmpty())) {
            gagnant = joueurInactif; //Le gagnant est forcément le joueur inactif dû à la manière dont nous testons les joueurs
            return false;
        }

        return true;
    }
}
