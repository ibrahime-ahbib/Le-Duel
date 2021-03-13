package appli.jeu;

import java.util.Collections;
import java.util.LinkedList;

public class Pioche {
    // La pioche du joueur
    private final LinkedList<Integer> pioche;

    /**
     * Constructeur
     * @param carteMinimale la carte la plus petite qui sera dans la pile
     * @param carteMaximale la carte la plus grande qui sera dans la pile
     */
    public Pioche(int carteMinimale, int carteMaximale){
        pioche  = new LinkedList<>();
        //Comme les cartes 1 et 60 sont déjà placées sur les piles, la pioche ne contient que les cartes de 2 à 59
        for(int i = carteMinimale; i <= carteMaximale; ++i)
            pioche.push(i);

        Collections.shuffle(pioche);
    }

    /**
     * Récupère le nombre de cartes dans la pioche du joueur
     * @return la taille de la pioche
     */
    public int getNombreCartes() {
        return pioche.size();
    }

    /**
     * Permet au joueur de piocher une carte dans la pioche
     * @return la carte venant d'être piochée
     */
    public int piocher(){
        return pioche.pop();
    }
}
