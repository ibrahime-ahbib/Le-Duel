package appli.jeu.tests;

import appli.jeu.Pile;
import appli.jeu.TypePile;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPile {

    @Test
    public void testCreationPile(){
        Pile pileAscendante = new Pile(1, TypePile.ASCENDANTE);
        Pile pileDescendante = new Pile(60, TypePile.DESCENDANTE);

        assertEquals(pileAscendante.getCarteSommet(), 1);
        assertEquals(pileDescendante.getCarteSommet(), 60);
    }

    @Test
    public void modificationPile(){
        Pile pileAscendante = new Pile(1, TypePile.ASCENDANTE);
        Pile pileDescendante = new Pile(60, TypePile.DESCENDANTE);

        pileAscendante.setCarteSommet(47);
        pileDescendante.setCarteSommet(15);
    }

    @Test
    public void testJouabiliteCarte(){
        Pile pileAscendante = new Pile(47, TypePile.ASCENDANTE);
        Pile pileDescendante = new Pile(15, TypePile.DESCENDANTE);

        assertTrue(pileAscendante.carteJouable(49, false)); //Jouable car 49>47
        assertTrue(pileAscendante.carteJouable(37, false)); //Jouable car 37 = 47-10
        assertFalse(pileAscendante.carteJouable(38, false)); //Injouable car 38 < 47 et 38 != 47-10

        assertTrue(pileDescendante.carteJouable(12, false)); //Jouable car 12<15
        assertTrue(pileDescendante.carteJouable(25, false)); //Jouable car 25 = 15+10
        assertFalse(pileDescendante.carteJouable(24, false)); //Injouable car 24>15 et 24 != 15+10
    }

    @Test
    public void testCopiePile(){
        Pile pileAscendante = new Pile(47, TypePile.ASCENDANTE);
        Pile pileDescendante = new Pile(15, TypePile.DESCENDANTE);

        Pile copieAscendante = new Pile(pileAscendante);
        Pile copieDescendante = new Pile(pileDescendante);

        assertEquals(pileAscendante.getCarteSommet(), copieAscendante.getCarteSommet());

        assertEquals(pileDescendante.getCarteSommet(), copieDescendante.getCarteSommet());

        pileAscendante.setCarteSommet(23);
        pileDescendante.setCarteSommet(56);
        copieAscendante.set(pileAscendante);
        copieDescendante.set(pileDescendante);

        assertEquals(pileAscendante.getCarteSommet(), copieAscendante.getCarteSommet());
        assertEquals(pileDescendante.getCarteSommet(), copieDescendante.getCarteSommet());
    }
}
