package appli.jeu.tests;

import appli.jeu.Jeu;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestJeu {

    @Test
    public void testCreationJeu() {
        Jeu jeu = new Jeu("NORD", "SUD");
        assertEquals(jeu.getJoueurActif(),jeu.getPremierJoueur());
        assertEquals(jeu.getJoueurInactif(), jeu.getSecondJoueur());
        assertNull(jeu.getGagnant());
    }

    @Test
    public void testFormatCoupValide() {
        Jeu jeu = new Jeu("NORD", "SUD");

        //On testes des formats valides
        assertTrue(jeu.formatCoupValide("15^'"));
        assertTrue(jeu.formatCoupValide("12v'"));
        assertTrue(jeu.formatCoupValide("37v"));
        assertTrue(jeu.formatCoupValide("16^"));

        //On teste des formats invalides
        assertFalse(jeu.formatCoupValide("6'v"));
        assertFalse(jeu.formatCoupValide("azeez 30v 25 !!!"));
        assertFalse(jeu.formatCoupValide("^25 21'^ 36"));
        assertFalse(jeu.formatCoupValide("^005 1^'2"));
    }
}
