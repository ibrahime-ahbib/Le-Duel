package appli.jeu.tests;

import appli.jeu.Joueur;
import appli.jeu.TypePile;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestJoueur {

    public static boolean mainTriee(ArrayList<Integer> mainJoueur){
        for(int i = 1; i < mainJoueur.size(); ++i){
            if(mainJoueur.get(i) < mainJoueur.get(i-1))
                return false;
        }
        return true;
    }

    @Test
    public void testCreationJoueur() {
        Joueur joueur = new Joueur("NORD");

        assertTrue(joueur.toString().equals("NORD"));
        assertEquals(6, joueur.getCartesEnMain().size()); //Un joueur a 6 cartes dans sa main au début
        assertEquals(joueur.getPile(TypePile.ASCENDANTE).getCarteSommet(), 1);
        assertEquals(joueur.getPile(TypePile.DESCENDANTE).getCarteSommet(), 60);

        assertTrue(mainTriee(joueur.getCartesEnMain())); //La main du joueur doit être triée après une pioche
    }
    
    @Test
    public void testPiocheJoueur() {
        Joueur joueur = new Joueur("NORD");
        Random random = new Random();
        int nbCartesAPiocher = random.nextInt(20);

        joueur.piocher(nbCartesAPiocher);
        assertEquals(6 + nbCartesAPiocher, joueur.getCartesEnMain().size());
        assertTrue(mainTriee(joueur.getCartesEnMain()));
    }

    @Test
    public void testSurpiocheJoueur(){
        Joueur joueur = new Joueur("NORD");
        joueur.piocher(50);
        int cartesPiochees = joueur.piocher(20);
        assertEquals(2, cartesPiochees); //On essaye de piocher plus de cartes qu'il n'y en a dans la pioche: on pioche donc normalement
        //le nombre de cartes restantes dans la pioche (2)

        cartesPiochees = joueur.piocher(10); //Puis on essaye de piocher alors qu'il n'y a plus de cartes du tout dans la pioche
        assertEquals(0, cartesPiochees); //On est donc sensé avoir pioché 0 carte.
    }

}
