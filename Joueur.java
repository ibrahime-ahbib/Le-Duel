package appli;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Joueur {
    private final String nom;
    private final Pile pile;
    private final ArrayList<Byte> cartesEnMain;
    private final Pioche pioche;

    Joueur(String nom)
    {
        this.nom = nom;
        cartesEnMain = new ArrayList<>();
        pile = new Pile();
        pioche = new Pioche();
        piocher(6);
    }

    public Pile getPile(){
        return pile;
    }

    public ArrayList<Byte> getCarteEnMain() {
        return cartesEnMain;
    }

    public void piocher(int nbCartes)
    {
        byte cartePioche;
        for(int i = 0; i < nbCartes; ++i) {
            cartePioche = pioche.piocher();
            cartesEnMain.add(cartePioche);
        }
    }

    //fonction afficher carte en main (format : cartes NORD { 15 20 23 32 41 48 } )
    public String toString()
    {
        StringBuilder affiche = new StringBuilder();  //NORD ^[01] v[60] (m6p52) SUD ^[01] v[60] (m6p52)

            affiche.append(nom).append(" ^[").append(getPile().getCarteSommetAscendante()).append("] v[").append(getPile().
                    getCarteSommetDescendante()).append("] (m").append(cartesEnMain.size()).append("p").
                    append(pioche.NombreCartesPioche()).append(")");



        return affiche.toString();
    }

    public String cartesMain() {
        StringBuilder affiche = new StringBuilder();

        affiche.append("cartes ").append(nom).append(" { ");
        Collections.sort(cartesEnMain);
        for(byte s : cartesEnMain)
            affiche.append(s).append(" ");

        affiche.append("}");
        return affiche.toString();
    }

    public boolean PeutJouerPartie (){
        ArrayList<Byte> cartesJouablesAscendantes = new ArrayList<>();
        ArrayList<Byte> cartesJouablesDescendantes = new ArrayList<>();

        byte carteAscendante = pile.getCarteSommetAscendante(), carteDescendante = pile.getCarteSommetDescendante();

        for(Byte carte : cartesEnMain) {
            if ((carte > carteAscendante || carte + 10 == carteAscendante)) {
                cartesJouablesAscendantes.add(carte);
                carteAscendante = carte;
            }

        }

        for(int i = cartesEnMain.size() - 1; i >= 0; i--)
        {
            byte carte = cartesEnMain.get(i);
            if(carte < carteDescendante || carte - 10 == carteDescendante)
            {
                cartesJouablesDescendantes.add(carte);
                carteDescendante = carte;
            }
        }

        if(cartesJouablesDescendantes.size() >= 2 || cartesJouablesAscendantes.size() >= 2 ||
                             (cartesJouablesDescendantes.size() == 1 && cartesJouablesAscendantes.size() == 1
                                     && !cartesJouablesDescendantes.get(0).equals(cartesJouablesAscendantes.get(0))))
                         return true;

        // pile descendante 34
        // 35 44
        return false;
    }

    public boolean partieGagnee(Joueur joueur2){
        return pioche.getPileDeCartes().empty() && cartesEnMain.isEmpty() && joueur2.partiePerdue();
    }

    public boolean partiePerdue(){ return !PeutJouerPartie(); }
}
